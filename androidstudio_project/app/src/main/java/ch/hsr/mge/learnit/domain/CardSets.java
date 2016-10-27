package ch.hsr.mge.learnit.domain;


import java.util.ArrayList;
import java.util.List;

public class CardSets {
    private List<CardSet> sets = new ArrayList<>();

    public CardSets(){
      /*  CardSet set = (new CardSet("Test"));
        Card card = new Card("testfront", "testback");
        Card card2 = new Card("front", "back");
        set.addCard(card);
        set.addCard(card2);
        sets.add(set);
        CardSet set2 = new CardSet("Test2");
        set2.addCard(card);
        sets.add(set2);*/
    }

    public List<CardSet> getCardSetList (){
        return sets;
    }

    public CardSet get(int position) {
        return sets.get(position);
    }

    public void addCardSet(CardSet set) {
        sets.add(set);
    }

    public void removeCardSet(int position) {sets.remove(position);}

    public int getPosition(CardSet set) {
        for (int i = 0; i < sets.size(); i++) {
            if (set.equals(sets.get(i))) {
                return i;
            }
        }
        return -1;
    }

    public boolean isEmpty() {
        return sets.size() == 0;
    }

    public int getSize() {
        return sets.size();
    }

    public boolean hasDuplicate(CardSet set){
        for (CardSet currSet : sets){
            if (currSet.getTitle().equals(set.getTitle())){
                return !(currSet == set);
            }
        }
        return false;
    }

}
