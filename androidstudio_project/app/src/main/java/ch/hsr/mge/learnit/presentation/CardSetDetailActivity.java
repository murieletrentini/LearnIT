package ch.hsr.mge.learnit.presentation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import ch.hsr.mge.learnit.Application;
import ch.hsr.mge.learnit.R;
import ch.hsr.mge.learnit.domain.CardSet;
import ch.hsr.mge.learnit.domain.CardSets;

public class CardSetDetailActivity extends AppCompatActivity {
    private CardSets sets;
    private CardSet set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_set_details);
        TextView title = (TextView) findViewById(R.id.titleText);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int position = extras.getInt("CARDSET_POSITION");
            Application app = (Application) getApplication();
            sets = app.getCardSets();
            set = sets.get(position);
            title.setText(set.getTitle());

            RecyclerView recyclerViewCardSets = (RecyclerView) findViewById(R.id.cardView);

            CardAdapter adapter = new CardAdapter(this, set);
            // Attach the adapter to the recyclerview to populate items
            recyclerViewCardSets.setAdapter(adapter);
            // Set layout manager to position the items
            recyclerViewCardSets.setLayoutManager(new LinearLayoutManager(this));
        }

    }
}
