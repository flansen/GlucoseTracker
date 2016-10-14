package de.fha.bwi50101.create_edit.note;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import de.fha.bwi50101.create_edit.impl.NoteFragmentPresenterImpl;

/**
 * Created by Florian on 09.10.2016.
 */

public class NoteFragment extends Fragment {
    private NoteFragmentPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new NoteFragmentPresenterImpl();
    }
}
