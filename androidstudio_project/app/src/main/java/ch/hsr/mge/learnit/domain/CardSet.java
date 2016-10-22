package ch.hsr.mge.learnit.domain;


import java.util.ArrayList;
import java.util.List;

import ch.hsr.mge.learnit.database.CardSetHelper;
import ch.hsr.mge.learnit.database.DBHelper;

public class CardSet {

    private List<Card> cardSet = new ArrayList<>();
    private String title;

    public CardSet(String title) {
        this.title = title;

    }

    public CardSet() { title = "placeholder"; }

    public Card get(int position) {
        return cardSet.get(position);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addCard(Card card){
        cardSet.add(card);

    }

    public int getPosition(Card card) {
        for (int i = 0; i < cardSet.size(); i++) {
            if (card.equals(cardSet.get(i))) {
                return i;
            }
        }
        return -1;
    }

    public int getSize() {
        return cardSet.size();
    }

    public boolean isEmpty() { return cardSet.size() == 0; }

    public int amountCorrectCards(){
        int correct = 0;
        for (Card card : cardSet){
            if (card.isCorrect()){
                correct++;
            }
        }
        return correct;
    }
}
