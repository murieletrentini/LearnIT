package ch.hsr.mge.learnit;


import java.util.List;

import ch.hsr.mge.learnit.database.DBHelper;
import ch.hsr.mge.learnit.domain.CardSet;
import ch.hsr.mge.learnit.domain.CardSets;

public class Application extends android.app.Application {
    private DBHelper helper;
    private CardSets sets;

    @Override
    public void onCreate() {
        helper = new DBHelper(getApplicationContext());
        sets = helper.getAllCardSets();
    }

    public CardSets getCardSets() {
        return sets;
    }

    public void addSet(CardSet set) {
        sets.addCardSet(set);
    }

    public void saveData(CardSets sets){
        List<CardSet> setlist = sets.getCardSetList();
        for (CardSet set : setlist){
            helper.updateCardSet(set.getTitle());
        }

    }
}
