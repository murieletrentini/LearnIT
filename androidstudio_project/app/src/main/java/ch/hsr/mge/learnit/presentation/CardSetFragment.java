package ch.hsr.mge.learnit.presentation;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import ch.hsr.mge.learnit.R;
import ch.hsr.mge.learnit.domain.CardSet;
import ch.hsr.mge.learnit.Application;

public class CardSetFragment extends Fragment {
    public static final String ARG_ITEM = "cardSet_to_show";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_card_set, container, false);

        if (getArguments().containsKey(ARG_ITEM)) {
            Application app = (Application) getActivity().getApplication();
            CardSet set = app.getCardSets().get(getArguments().getInt(ARG_ITEM));
            initTitleText(rootView, set);
            initButtons(rootView, set);
        }

        return rootView;
    }

    private void initButtons(final View rootView, final CardSet set) {
        ImageButton play = (ImageButton) rootView.findViewById(R.id.playButton);
        ImageButton edit = (ImageButton) rootView.findViewById(R.id.editButton);
        ImageButton delete = (ImageButton) rootView.findViewById(R.id.deleteButton);
        play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Snackbar s = Snackbar.make(rootView, "Play clicked", Snackbar.LENGTH_SHORT);
                s.show();
            }
        });
    }

    private void initTitleText(View rootView, final CardSet set) {
        TextView titleText = (TextView) rootView.findViewById(R.id.titleText);
        titleText.setText(set.getTitle());
        titleText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                set.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
