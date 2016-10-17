package de.fha.bwi50101.create_edit.glucose;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import de.fha.bwi50101.R;
import de.fha.bwi50101.create_edit.EntryProvider;
import de.fha.bwi50101.create_edit.impl.GlucoseFragmentPresenterImpl;

/**
 * Created by Florian on 09.10.2016.
 */

public class GlucoseFragment extends Fragment {
    private GlucoseFragmentPresenter presenter;

    public static GlucoseFragment newInstance() {
        return new GlucoseFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new GlucoseFragmentPresenterImpl(((EntryProvider) getActivity()).getEntry());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_glucose, container, false);
        ButterKnife.bind(this, view);
        return view;

    }
}
