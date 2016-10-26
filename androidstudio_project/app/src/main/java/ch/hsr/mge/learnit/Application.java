package ch.hsr.mge.learnit;


import java.util.List;

import ch.hsr.mge.learnit.database.DBHelper;
import ch.hsr.mge.learnit.domain.Card;
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
        //helper.dropAndRecreate();
        List<CardSet> setList = sets.getCardSetList();
        List<Card> cardList;
        for (CardSet set : setList){
            cardList = set.getCardList();
            if (!sets.getCardSetList().contains(set)) {
                helper.insertCardSet(set.getTitle());
                for (Card card : cardList) {
                    helper.insertCard(card.getFront(), card.getBack(), set.getTitle());
                }
            }

        }

    }

}
