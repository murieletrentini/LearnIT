package ch.hsr.mge.learnit.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import ch.hsr.mge.learnit.Application;
import ch.hsr.mge.learnit.R;
import ch.hsr.mge.learnit.database.DBHelper;
import ch.hsr.mge.learnit.dialogs.YesNoDialog;
import ch.hsr.mge.learnit.dialogs.YesNoDialog.DialogListener;
import ch.hsr.mge.learnit.domain.CardSet;
import ch.hsr.mge.learnit.domain.CardSets;

public class CardSetDetailActivity extends AppCompatActivity implements DialogListener {
    private CardSets sets;
    private CardSet set;
    private int index;
    private Intent intent;
    private DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_set_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        TextView title = (TextView) findViewById(R.id.titleText);
        Bundle extras = getIntent().getExtras();
        Application app = (Application) getApplication();
        sets = app.getCardSets();
        if (!sets.isEmpty() && extras != null) {
            index = extras.getInt("CARDSET_POSITION");
            set = sets.get(index);
            title.setText(set.getTitle());
        }

        RecyclerView recyclerViewCardSets = (RecyclerView) findViewById(R.id.cardView);

        CardAdapter adapter = new CardAdapter(this, set, index);
        recyclerViewCardSets.setAdapter(adapter);
        recyclerViewCardSets.setLayoutManager(new LinearLayoutManager(this));


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CardSetDetailActivity.this, PlayMode.class);
                intent.putExtra("CARDSET_POSITION", index);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cardsetdetail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_addCard:
                intent = new Intent(CardSetDetailActivity.this, AddCardActivity.class);
                intent.putExtra("CARDSET_POSITION", index);
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
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onFinishDialog(int resultCode) {
        if (resultCode == RESULT_OK){
            sets.removeCardSet(index);
            intent = new Intent(CardSetDetailActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}

