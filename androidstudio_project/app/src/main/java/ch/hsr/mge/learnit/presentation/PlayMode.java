package ch.hsr.mge.learnit.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import ch.hsr.mge.learnit.Application;
import ch.hsr.mge.learnit.R;
import ch.hsr.mge.learnit.domain.Card;
import ch.hsr.mge.learnit.domain.CardSet;

public class PlayMode extends AppCompatActivity {
    private CardSet set;
    private Card currCard;
    private int counter = 0;
    private TextView front;
    private TextView back;
    private int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_mode);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        index = extras.getInt("CARDSET_POSITION");
        Application app = (Application) getApplication();
        set = app.getCardSets().get(index);

        front = (TextView) findViewById(R.id.frontText);
        back = (TextView) findViewById(R.id.backText);
        currCard = set.get(counter);
        refreshCardText();

        ImageButton thumbsUp = (ImageButton) findViewById(R.id.thumbsUp);
        ImageButton thumbsDown = (ImageButton) findViewById(R.id.thumbsDown);
        thumbsUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currCard.setCorrect(true);
                if (counter == set.getSize() - 1) {
                    finished();
                } else {
                    currCard = set.get(++counter);
                    refreshCardText();
                }
            }
        });

        thumbsDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currCard.setCorrect(false);
                if (counter == set.getSize() - 1) {
                    finished();
                } else {
                    currCard = set.get(++counter);
                    refreshCardText();
                }
        }
    }

    );
}

    private void finished() {
        Intent intent = new Intent(this, WellDoneActivity.class);
        intent.putExtra("CARDSET_POSITION", index);
        startActivity(intent);
    }

    private void refreshCardText() {
        front.setText(currCard.getFront());
        back.setText(currCard.getBack());
    }
}
