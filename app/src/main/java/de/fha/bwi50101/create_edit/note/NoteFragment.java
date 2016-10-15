package de.fha.bwi50101.create_edit.note;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import de.fha.bwi50101.R;
import de.fha.bwi50101.create_edit.CreateEditActivity;
import de.fha.bwi50101.create_edit.impl.NoteFragmentPresenterImpl;

/**
 * Created by Florian on 09.10.2016.
 */

public class NoteFragment extends Fragment {
    private NoteFragmentPresenter presenter;

    public static NoteFragment newInstance() {
        return new NoteFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new NoteFragmentPresenterImpl(((CreateEditActivity) getActivity()).getEntry());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
