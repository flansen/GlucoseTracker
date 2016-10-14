package de.fha.bwi50101.create_edit.insulin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import de.fha.bwi50101.create_edit.impl.InsulinFragmentPresenterImpl;

/**
 * Created by Florian on 09.10.2016.
 */

public class InsulinFragment extends Fragment {
    private InsulinFragmentPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new InsulinFragmentPresenterImpl();
    }
}
