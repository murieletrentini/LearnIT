package ch.hsr.mge.learnit.presentation;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.hsr.mge.learnit.Application;
import ch.hsr.mge.learnit.R;

public class CardSetListFragment extends Fragment {
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private CardSetSelectionListener cardSetSelectionCallback = null;
    private CardSetAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_card_set_list, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.cardSetView);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        Application app = (Application) getActivity().getApplication();

        adapter = new CardSetAdapter(app.getCardSets(), cardSetSelectionCallback);
        recyclerView.setAdapter(adapter);


        return rootView;
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

        if (!(activity instanceof CardSetSelectionListener)) {
            throw new IllegalStateException("Activity must implement ItemSelectionListener");
        }

        cardSetSelectionCallback = (CardSetSelectionListener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        cardSetSelectionCallback = null;
    }
}
