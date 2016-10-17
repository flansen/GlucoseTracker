package de.fha.bwi50101.create_edit.impl;

import de.fha.bwi50101.common.model.Entry;
import de.fha.bwi50101.create_edit.note.NoteFragmentPresenter;

/**
 * Created by Florian on 14.10.2016.
 */

public class NoteFragmentPresenterImpl implements NoteFragmentPresenter {
    private Entry entry;
    private View view;

    public NoteFragmentPresenterImpl(Entry entry) {
        this.entry = entry;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void resetClicked() {
        view.resetNote();
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void onNoteTextChanged(String text) {
        if (text.isEmpty())
            entry.setNote(null);
        else
            entry.setNote(text);
    }
}
