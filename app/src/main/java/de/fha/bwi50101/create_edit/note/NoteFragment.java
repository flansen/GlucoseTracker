package de.fha.bwi50101.create_edit.note;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.fha.bwi50101.R;
import de.fha.bwi50101.create_edit.EntryProvider;
import de.fha.bwi50101.create_edit.impl.NoteFragmentPresenterImpl;

/**
 * Created by Florian on 09.10.2016.
 */

public class NoteFragment extends Fragment implements NoteFragmentPresenter.View {
    @BindView(R.id.note_wrapper)
    TextInputLayout noteInputLayout;
    @BindView(R.id.note_reset)
    Button resetButton;
    private NoteFragmentPresenter presenter;

    public static NoteFragment newInstance() {
        return new NoteFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new NoteFragmentPresenterImpl(((EntryProvider) getActivity()).getEntry());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        ButterKnife.bind(this, view);
        presenter.setView(this);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.resetClicked();
            }
        });
        noteInputLayout.getEditText().addTextChangedListener(new NoteEditTextWatcher());
        return view;
    }

    public void setPresenter(NoteFragmentPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void resetNote() {
        noteInputLayout.getEditText().setText(null);
    }

    private class NoteEditTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            presenter.onNoteTextChanged(s.toString());
        }
    }
}
