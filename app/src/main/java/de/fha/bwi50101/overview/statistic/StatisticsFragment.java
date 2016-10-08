package de.fha.bwi50101.overview.statistic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import de.fha.bwi50101.R;
import de.fha.bwi50101.overview.statistic.impl.StatisticsFragmentPresenterImpl;

/**
 * Created by Florian on 08.10.2016.
 */

public class StatisticsFragment extends Fragment {
    private StatisticsFragmentPresenter presenter;

    public static StatisticsFragment newIntance() {
        return new StatisticsFragment();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_statistic, menu);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        presenter = new StatisticsFragmentPresenterImpl();
        presenter.resume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
