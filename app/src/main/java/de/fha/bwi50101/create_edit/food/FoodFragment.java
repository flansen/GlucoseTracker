package de.fha.bwi50101.create_edit.food;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import de.fha.bwi50101.create_edit.impl.FoodFragmentPresenterImpl;

/**
 * Created by Florian on 09.10.2016.
 */

public class FoodFragment extends Fragment {
    private FoodFragmentPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new FoodFragmentPresenterImpl();
    }
}
