package ch.hsr.mge.learnit.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        ImageButton saveButton = (ImageButton) findViewById(R.id.checkButton);
        ImageButton cancleButton = (ImageButton) findViewById(R.id.cancleButton);
        Application app = (Application) getApplication();
        Bundle extras = getIntent().getExtras();
        index = extras.getInt("CARDSET_POSITION");
        set = app.getCardSets().get(index);

        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText front = (EditText) findViewById(R.id.frontSideText);
                EditText back = (EditText) findViewById(R.id.backSideText);
                set.addCard(new Card(front.getText().toString(), back.getText().toString()));
                Intent intent = new Intent(AddCardActivity.this, CardSetDetailActivity.class);
                startActivity(intent);
            }
        });

        cancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCardActivity.this, CardSetDetailActivity.class);
                startActivity(intent);
            }
        });

    }
}
