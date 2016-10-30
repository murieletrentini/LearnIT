package ch.hsr.mge.learnit.presentation;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ch.hsr.mge.learnit.R;
import ch.hsr.mge.learnit.domain.CardSet;
import ch.hsr.mge.learnit.domain.CardSets;

public class CardSetAdapter extends
        RecyclerView.Adapter<CardSetAdapter.ViewHolder> {

    private CardSets sets = null;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView;
        private TextView amountOfCards;
        private Context context;
        private RelativeLayout cardSetParent;

        public ViewHolder(Context context, View itemRoot, TextView textView, TextView amountOfCards, RelativeLayout relativeLayout) {
            super(itemRoot);
            this.textView = textView;
            this.amountOfCards = amountOfCards;
            this.context = context;
            this.cardSetParent = relativeLayout;

           relativeLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition(); // gets item position
            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                // We can access the data within the views
                Intent intent = new Intent(context, CardSetDetailActivity.class);
                intent.putExtra("CARDSET_POSITION", position);
                context.startActivity(intent);
            }
        }
    }

    public CardSetAdapter(Context context, CardSets sets) {
        this.mContext = context;
        this.sets = sets;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.card_set_layout, parent, false);
        TextView textView = (TextView) v.findViewById(R.id.textView);
        TextView amountOfCards = (TextView) v.findViewById(R.id.cardAmount);
        RelativeLayout relativeLayout = (RelativeLayout) v.findViewById(R.id.cardSetParent);
        return new ViewHolder(context, v, textView, amountOfCards, relativeLayout);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (!sets.isEmpty()) {
            final CardSet set = sets.get(position);
            holder.textView.setText(set.getTitle());
            String card;
            card = set.getSize() == 1 ? "card" : "cards";
            holder.amountOfCards.setText("( " + set.getSize() + " " + card + " )");
        }
        else {
            String emptyString = "Click on the Plus to add a Set!";
            holder.textView.setText(emptyString);
            holder.amountOfCards.setText("");
        }

    }

    @Override
    public int getItemCount() {
        if (!sets.isEmpty())
            return sets.getSize();
        else
            return 1;
    }
}
