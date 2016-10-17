package ch.hsr.mge.learnit.presentation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ch.hsr.mge.learnit.R;
import ch.hsr.mge.learnit.domain.CardSet;
import ch.hsr.mge.learnit.domain.CardSets;

public class CardSetAdapter extends RecyclerView.Adapter<CardSetAdapter.ViewHolder> {
    private CardSets sets = null;
    private CardSetSelectionListener selectionListener;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(View itemRoot, TextView textView) {
            super(itemRoot);
            this.textView = textView;
        }
    }

    public CardSetAdapter(CardSets sets, CardSetSelectionListener selectionListener) {
        this.sets = sets;
        this.selectionListener = selectionListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.card_set_layout, parent, false);
        TextView textView = (TextView) v.findViewById(R.id.textView);
        return new ViewHolder(v, textView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CardSet set = sets.get(position);
        holder.textView.setText(set.getTitle());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectionListener.onItemSelected(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sets.getSize();
    }

}