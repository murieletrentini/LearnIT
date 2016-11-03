package ch.hsr.mge.learnit.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ViewSwitcher;

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
    private ViewSwitcher switcher;
    private int index;
    Animation slide_in_left, slide_out_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_mode);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        index = extras.getInt("CARDSET_POSITION");
        Application app = (Application) getApplication();
        set = app.getCardSets().get(index);

        front = (TextView) findViewById(R.id.frontText);
        back = (TextView) findViewById(R.id.backText);
        currCard = set.get(counter);
        refreshCardText();

        switcher = (ViewSwitcher) findViewById(R.id.viewSwitcher);
        slide_in_left = AnimationUtils.loadAnimation(this,
                android.R.anim.slide_in_left);
        slide_out_right = AnimationUtils.loadAnimation(this,
                android.R.anim.slide_out_right);

        switcher.setInAnimation(slide_in_left);
        switcher.setOutAnimation(slide_out_right);

        switcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switcher.getDisplayedChild() == 0) {
                    switcher.showNext();
                } else {
                    switcher.showPrevious();
                }

            }
        });

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

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, CardSetDetailActivity.class);
        intent.putExtra("CARDSET_POSITION", index);
        startActivity(intent);
    }

    private void finished() {
        Intent intent = new Intent(this, FinishedActivity.class);
        intent.putExtra("CARDSET_POSITION", index);
        startActivity(intent);
    }

    private void refreshCardText() {
        if (switcher == null || switcher.getDisplayedChild() == 0) {
            front.setText(currCard.getFront());
            back.setText(currCard.getBack());
        } else {
            front.setText(currCard.getBack());
            back.setText(currCard.getFront());
        }

    }
}
