package de.fha.bwi50101.create_edit.food;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import de.fha.bwi50101.R;
import de.fha.bwi50101.create_edit.CreateEditActivity;
import de.fha.bwi50101.create_edit.impl.FoodFragmentPresenterImpl;

/**
 * Created by Florian on 09.10.2016.
 */

public class FoodFragment extends Fragment {
    private FoodFragmentPresenter presenter;

    public static FoodFragment newInstance() {
        return new FoodFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new FoodFragmentPresenterImpl(((CreateEditActivity) getActivity()).getEntry());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
