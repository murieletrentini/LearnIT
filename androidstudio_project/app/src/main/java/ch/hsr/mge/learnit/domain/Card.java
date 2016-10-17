package ch.hsr.mge.learnit.domain;

public class Card {
    private String front;
    private String back;
    private boolean isCorrect;

    public Card(String front, String back){
        this.front = front;
        this.back = back;
        isCorrect = false;
    }

    public String getFront() {
        return front;
    }

    public void setFront(String front) {
        this.front = front;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
