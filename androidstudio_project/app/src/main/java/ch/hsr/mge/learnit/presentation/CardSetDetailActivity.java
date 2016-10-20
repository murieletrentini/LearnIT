package ch.hsr.mge.learnit.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ch.hsr.mge.learnit.Application;
import ch.hsr.mge.learnit.R;
import ch.hsr.mge.learnit.domain.CardSet;
import ch.hsr.mge.learnit.domain.CardSets;

public class CardSetDetailActivity extends AppCompatActivity {
    private CardSets sets;
    private CardSet set;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_set_details);
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

        CardAdapter adapter = new CardAdapter(this, set);
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
}

