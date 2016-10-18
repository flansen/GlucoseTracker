package de.fha.bwi50101.create_edit.note;

import de.flhn.cleanboilerplate.presentation.presenters.base.BasePresenter;

/**
 * Created by Florian on 14.10.2016.
 */

public interface NoteFragmentPresenter extends BasePresenter {
    void resetClicked();

    void setView(View view);

    void onNoteTextChanged(String text);

    String getNote();
    interface View {
        void resetNote();

        void recreateStateForeEditing();
    }
}
