package de.fha.bwi50101.create_edit.insulin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import de.fha.bwi50101.R;
import de.fha.bwi50101.create_edit.CreateEditActivity;
import de.fha.bwi50101.create_edit.impl.InsulinFragmentPresenterImpl;

/**
 * Created by Florian on 09.10.2016.
 */

public class InsulinFragment extends Fragment {
    private InsulinFragmentPresenter presenter;

    public static InsulinFragment newInstance() {
        return new InsulinFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new InsulinFragmentPresenterImpl(((CreateEditActivity) getActivity()).getEntry());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_insulin, container, false);
        ButterKnife.bind(this, view);
        return view;

    }
}
