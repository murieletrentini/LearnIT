package ch.hsr.mge.learnit.domain;


import java.util.ArrayList;
import java.util.List;

public class CardSets {
    private List<CardSet> sets = new ArrayList<>();

    public CardSets(){
        CardSet one = new CardSet("Englisch Voci");
        one.addCard(new Card("testfront", "testback"));
        sets.add(one);
    }

    public CardSet get(int position) {
        return sets.get(position);
    }

    public int getPosition(CardSet set) {
        for (int i = 0; i < sets.size(); i++) {
            if (set.equals(sets.get(i))) {
                return i;
            }
        }
        return -1;
    }

    public int getSize() {
        return sets.size();
    }

}
