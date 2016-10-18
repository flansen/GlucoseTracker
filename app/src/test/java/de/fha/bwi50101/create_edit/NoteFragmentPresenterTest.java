package de.fha.bwi50101.create_edit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import de.fha.bwi50101.common.model.Entry;
import de.fha.bwi50101.create_edit.impl.NoteFragmentPresenterImpl;
import de.fha.bwi50101.create_edit.note.NoteFragmentPresenter;

/**
 * Created by Florian on 18.10.2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class NoteFragmentPresenterTest {
    NoteFragmentPresenter sut;
    Entry mockEntry;
    NoteFragmentPresenter.View mockView;

    @Before
    public void setUp() {
        mockEntry = Mockito.mock(Entry.class);
        sut = new NoteFragmentPresenterImpl(mockEntry);
        mockView = Mockito.mock(NoteFragmentPresenter.View.class);
        sut.setView(mockView);
    }

    @Test
    public void shouldCall_ViewReset() {
        sut.resetClicked();
        Mockito.verify(mockView, Mockito.times(1)).resetNote();
    }

    @Test
    public void shouldSet_EntryNote() {
        String testText = "Testtext";
        sut.onNoteTextChanged(testText);
        Mockito.verify(mockEntry, Mockito.times(1)).setNote(testText);
    }
}
