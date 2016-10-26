package ch.hsr.mge.learnit.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import ch.hsr.mge.learnit.Application;
import ch.hsr.mge.learnit.R;
import ch.hsr.mge.learnit.database.DBHelper;
import ch.hsr.mge.learnit.domain.CardSet;
import ch.hsr.mge.learnit.domain.CardSets;

public class MainActivity extends AppCompatActivity {
    private CardSets sets;
    private DBHelper helper;
    private Application app;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerViewCardSets = (RecyclerView) findViewById(R.id.cardSetView);
        app = (Application) getApplication();

        sets = app.getCardSets();
        CardSetAdapter adapter = new CardSetAdapter(this, sets);
        recyclerViewCardSets.setAdapter(adapter);
        recyclerViewCardSets.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    CardSet set =  new CardSet();
                    sets.addCardSet(set);
                    set.setTitle("Title");
                    Intent intent = new Intent(MainActivity.this, CardSetDetailActivity.class);
                    int position = sets.getPosition(set);
                    intent.putExtra("CARDSET_POSITION", position);
                    startActivity(intent);
            }
        });
    }

    @Override
    public void onPause(){
        super.onPause();
        app.saveData(sets);
    }


    @Override
    public void onResume(){
        super.onResume();
        sets = app.getCardSets();
        RecyclerView recyclerViewCardSets = (RecyclerView) findViewById(R.id.cardSetView);
        CardSetAdapter adapter = new CardSetAdapter(this, sets);
        recyclerViewCardSets.setAdapter(adapter);
        recyclerViewCardSets.setLayoutManager(new LinearLayoutManager(this));
    }


}
