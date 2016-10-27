package ch.hsr.mge.learnit.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import ch.hsr.mge.learnit.Application;
import ch.hsr.mge.learnit.R;
import ch.hsr.mge.learnit.database.DBHelper;
import ch.hsr.mge.learnit.domain.Card;
import ch.hsr.mge.learnit.domain.CardSet;
import ch.hsr.mge.learnit.domain.CardSets;
import ch.hsr.mge.learnit.presentation.YesNoDialog.DialogListener;

public class AddCardActivity extends AppCompatActivity implements DialogListener {
    private int index;
    private int cardPosition;
    private CardSets sets;
    private CardSet set;
    private EditText front;
    private String frontString;
    private String backString;
    private EditText back;
    private Card card;
    private Intent intent;
    private Application app;
    private DBHelper helper;
    private String [] oldNames = new String[2];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Application app = (Application) getApplication();
        Bundle extras = getIntent().getExtras();
        index = extras.getInt("CARDSET_POSITION");
        cardPosition = extras.getInt("CARD_POSITION");
        sets= app.getCardSets();
        set = sets.get(index);
        card = set.get(cardPosition);

        front = (EditText) findViewById(R.id.frontSideText);
        back = (EditText) findViewById(R.id.backSideText);

        front.setText(card.getFront());
        back.setText(card.getBack());

        //oldNames[0] = front.getText().toString();
        //oldNames[1] = back.getText().toString();

       /* if (getIntent().hasExtra("CARD_POSITION")) {
            //card already exists -> edit mode

            card = set.get(extras.getInt("CARD_POSITION"));
            front.setText(card.getFront());
            back.setText(card.getBack());
            //TODO: not exactly sure on how to check if card is a new one or an old one,
            // code needs to be moved, maybe check on empty strings at start
            //helper = new DBHelper(getApplicationContext());
            //String newNames[] = new String[2];
            //newNames[0] = front.getText().toString();
            //newNames[1] = back.getText().toString();
            //helper.updateCard(oldNames, newNames);


        } else {
            card = new Card();
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_addcard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                frontString = front.getText().toString();
                backString = back.getText().toString();
                if(frontString.equals("") || backString.equals("")) {
                    DialogFragment alert = new YesNoDialog();
                    Bundle args = new Bundle();
                    args.putString("MESSAGE", "Your card has unfilled sides.");
                    alert.setArguments(args);
                    alert.show(getSupportFragmentManager(), "Alert");
                } else {
                    saveAndBackToParentActivity();
                }
                return true;
            case R.id.action_goHome:
                startActivity(new Intent(AddCardActivity.this, MainActivity.class));
                return true;
            case R.id.action_removeCard:
                set.removeCard(card);
                Intent intent = new Intent(AddCardActivity.this, CardSetDetailActivity.class);
                intent.putExtra("CARDSET_POSITION", index);
                startActivity(intent);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveAndBackToParentActivity(){
        card.setFront(frontString);
        card.setBack(backString);
        set.addCard(card);
        // app is buggy without these next lines
        helper = new DBHelper(getApplicationContext());
        helper.insertCardSet(set.getTitle());
        helper.insertCard(frontString, backString, set.getTitle());

        intent = new Intent(AddCardActivity.this, CardSetDetailActivity.class);
        intent.putExtra("CARDSET_POSITION", index);
        startActivity(intent);
    }

    @Override
    public void onFinishDialog(int resultCode) {
        if (resultCode == RESULT_OK){
            saveAndBackToParentActivity();
        }
    }
    @Override
    public void onPause() {
        super.onPause();
            if (helper == null)
                helper = new DBHelper(getApplicationContext());
            app = (Application) getApplication();
            app.saveData(sets);

    }

    @Override
    public void onResume(){
        super.onResume();
        sets = ((Application)getApplication()).getCardSets();
        set = sets.get(index);
        card = set.get(cardPosition);
        frontString = card.getFront();
        backString = card.getBack();
    }

}
