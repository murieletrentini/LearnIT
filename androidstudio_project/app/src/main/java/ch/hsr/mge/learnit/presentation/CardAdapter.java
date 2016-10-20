package ch.hsr.mge.learnit.presentation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ch.hsr.mge.learnit.R;
import ch.hsr.mge.learnit.domain.Card;
import ch.hsr.mge.learnit.domain.CardSet;

public class CardAdapter extends
        RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private CardSet set = null;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView frontText;
        public TextView backText;
        private Context context;

        public ViewHolder(Context context, View itemRoot, TextView frontText, TextView backText) {
            super(itemRoot);
            this.frontText = frontText;
            this.backText = backText;
            this.context = context;
        }

    }

    public CardAdapter(Context context, CardSet set) {
        this.mContext = context;
        this.set = set;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.card_layout, parent, false);
        TextView frontText = (TextView) v.findViewById(R.id.frontText);
        TextView backText = (TextView) v.findViewById(R.id.backText);
        return new ViewHolder(context,v, frontText, backText);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Card card = set.get(position);
        holder.frontText.setText(card.getFront());
        holder.backText.setText(card.getBack());
    }

    @Override
    public int getItemCount() {
        if (set != null)
            return set.getSize();
        else
            return 0;
    }

    private Context getContext() {
        return mContext;
    }
}
