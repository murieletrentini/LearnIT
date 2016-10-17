package ch.hsr.mge.learnit;


import ch.hsr.mge.learnit.domain.CardSets;

public class Application extends android.app.Application {
    private CardSets sets = new CardSets();

    public CardSets getCardSets() {
        return sets;
    }
}
