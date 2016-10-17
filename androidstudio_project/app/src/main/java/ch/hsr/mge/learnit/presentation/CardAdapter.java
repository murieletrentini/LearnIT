package ch.hsr.mge.learnit.presentation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ch.hsr.mge.learnit.R;
import ch.hsr.mge.learnit.domain.Card;
import ch.hsr.mge.learnit.domain.CardSet;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private CardSet set = null;
    private CardSelectionListener selectionListener;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView front;
        public TextView back;

        public ViewHolder(View itemRoot, TextView front, TextView back) {
            super(itemRoot);
            this.front = front;
            this.back = back;
        }
    }

    public CardAdapter(CardSet set, CardSelectionListener selectionListener) {
        this.set = set;
        this.selectionListener = selectionListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.card_layout, parent, false);
        TextView front = (TextView) v.findViewById(R.id.frontText);
        TextView back = (TextView) v.findViewById(R.id.backText);
        return new ViewHolder(v, front, back);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Card card = set.get(position);
        holder.front.setText(card.getFront());
        holder.back.setText(card.getBack());
    }

    @Override
    public int getItemCount() {
        return set.getSize();
    }

}