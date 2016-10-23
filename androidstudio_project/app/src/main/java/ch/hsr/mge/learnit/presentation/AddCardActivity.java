package ch.hsr.mge.learnit.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import ch.hsr.mge.learnit.Application;
import ch.hsr.mge.learnit.R;
import ch.hsr.mge.learnit.domain.Card;
import ch.hsr.mge.learnit.domain.CardSet;

public class AddCardActivity extends AppCompatActivity {
    private int index;
    private CardSet set;
    private EditText front;
    private EditText back;
    private Card card;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        Application app = (Application) getApplication();
        Bundle extras = getIntent().getExtras();
        index = extras.getInt("CARDSET_POSITION");
        set = app.getCardSets().get(index);

        front = (EditText) findViewById(R.id.frontSideText);
        back = (EditText) findViewById(R.id.backSideText);

        if (getIntent().hasExtra("CARD_POSITION")) {
            //card already exists -> edit mode
            card = set.get(extras.getInt("CARD_POSITION"));
            front.setText(card.getFront());
            back.setText(card.getBack());
        } else {
            card = new Card();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_addcard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;
            case R.id.action_save:
                card.setFront(front.getText().toString());
                card.setBack(back.getText().toString());
                set.addCard(card);
                intent = new Intent(AddCardActivity.this, CardSetDetailActivity.class);
                intent.putExtra("CARDSET_POSITION", index);
                startActivity(intent);
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}
