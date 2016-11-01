package ch.hsr.mge.learnit.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import ch.hsr.mge.learnit.Application;
import ch.hsr.mge.learnit.R;
import ch.hsr.mge.learnit.domain.Card;
import ch.hsr.mge.learnit.domain.CardSet;
import ch.hsr.mge.learnit.domain.CardSets;
import ch.hsr.mge.learnit.presentation.YesNoDialog.DialogListener;

public class CardSetDetailActivity extends AppCompatActivity implements DialogListener {
    private CardSets sets;
    private CardSet set;
    private int index;
    private Intent intent;
    private Application app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_set_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        app = (Application) getApplication();

        final EditText title = (EditText) findViewById(R.id.titleText);
        Bundle extras = getIntent().getExtras();
        Application app = (Application) getApplication();
        sets = app.getCardSets();
        if (!sets.isEmpty() && extras != null) {
            index = extras.getInt("CARDSET_POSITION");
            set = sets.get(index);
            title.setText(set.getTitle());
        }

        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (set != null)
                    set.setTitle(title.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        RecyclerView recyclerViewCardSets = (RecyclerView) findViewById(R.id.cardView);
        LinearLayout emptyLayout = (LinearLayout) findViewById(R.id.empty);

        if (set.isEmpty()){
            recyclerViewCardSets.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
        } else {
            recyclerViewCardSets.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);

            CardAdapter adapter = new CardAdapter(this, set, index);
            recyclerViewCardSets.setAdapter(adapter);
            recyclerViewCardSets.setLayoutManager(new LinearLayoutManager(this));
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!set.isEmpty()) {
                    Intent intent = new Intent(CardSetDetailActivity.this, PlayMode.class);
                    intent.putExtra("CARDSET_POSITION", index);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cardsetdetail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_goHome:
                intent = new Intent(CardSetDetailActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_addCard:
                intent = new Intent(CardSetDetailActivity.this, AddCardActivity.class);
                Card newCard = new Card();
                set.addCard(newCard);
                int indexCard = set.getPosition(newCard);
                intent.putExtra("CARDSET_POSITION", index);
                intent.putExtra("CARD_POSITION", indexCard);
                startActivity(intent);
                return true;
            case R.id.action_removeSet:
                DialogFragment alert = new YesNoDialog();
                Bundle args = new Bundle();
                args.putString("MESSAGE", "This set will be deleted.");
                alert.setArguments(args);
                alert.show(getSupportFragmentManager(), "Alert");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onFinishDialog(int resultCode) {
        if (resultCode == RESULT_OK) {
            sets.removeCardSet(index);
            intent = new Intent(CardSetDetailActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        app.saveData(sets);
    }

    @Override
    public void onResume() {
        super.onResume();
        sets = app.getCardSets();
        if (!sets.isEmpty()) {
            set = sets.get(index);
            RecyclerView recyclerViewCardSets = (RecyclerView) findViewById(R.id.cardView);
            CardAdapter adapter = new CardAdapter(this, set, index);
            recyclerViewCardSets.setAdapter(adapter);
            recyclerViewCardSets.setLayoutManager(new LinearLayoutManager(this));
        }
    }
}

