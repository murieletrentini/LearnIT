package ch.hsr.mge.learnit.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

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

        ImageButton saveButton = (ImageButton) findViewById(R.id.checkButton);
        ImageButton cancleButton = (ImageButton) findViewById(R.id.cancleButton);
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

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card.setFront(front.getText().toString());
                card.setBack(back.getText().toString());
                set.addCard(card);
                Intent intent = new Intent(AddCardActivity.this, CardSetDetailActivity.class);
                intent.putExtra("CARDSET_POSITION", index);
                startActivity(intent);
            }
        });

        cancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCardActivity.this, CardSetDetailActivity.class);
                intent.putExtra("CARDSET_POSITION", index);
                startActivity(intent);
            }
        });

    }
}
