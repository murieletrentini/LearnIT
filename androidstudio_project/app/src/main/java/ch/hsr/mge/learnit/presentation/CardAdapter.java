package ch.hsr.mge.learnit.presentation;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import ch.hsr.mge.learnit.R;
import ch.hsr.mge.learnit.domain.Card;
import ch.hsr.mge.learnit.domain.CardSet;

public class CardAdapter extends
        RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private CardSet set;
    private int index;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView frontText;
        private TextView backText;
        private LinearLayout cardParent;
        private Context context;

        public ViewHolder(Context context, View itemRoot, TextView frontText, TextView backText, LinearLayout cardParent) {
            super(itemRoot);
            this.frontText = frontText;
            this.backText = backText;
            this.cardParent = cardParent;
            this.context = context;

            cardParent.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Intent intent = new Intent(context, AddCardActivity.class);
                intent.putExtra("CARDSET_POSITION", index);
                intent.putExtra("CARD_POSITION", position);
                context.startActivity(intent);
            }
        }
    }

    public CardAdapter(Context context, CardSet set, int index) {
        this.mContext = context;
        this.set = set;
        this.index = index;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.card_layout, parent, false);
        TextView frontText = (TextView) v.findViewById(R.id.frontText);
        TextView backText = (TextView) v.findViewById(R.id.backText);
        LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.cardParent);
        return new ViewHolder(context,v, frontText, backText, linearLayout);
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
}
