package de.fha.bwi50101.create_edit;

import android.support.design.widget.TextInputLayout;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import de.fha.bwi50101.BuildConfig;
import de.fha.bwi50101.R;
import de.fha.bwi50101.create_edit.impl.NoteFragmentPresenterImpl;
import de.fha.bwi50101.create_edit.note.NoteFragment;
import de.fha.bwi50101.create_edit.note.NoteFragmentPresenter;

/**
 * Created by Florian on 18.10.2016.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22)
public class NoteFragmentTest {
    NoteFragment noteFragment;
    NoteFragmentPresenter presenterMock;

    @Before
    public void setUp() {
        noteFragment = NoteFragment.newInstance();
        SupportFragmentTestUtil.startFragment(noteFragment, CreateEditActivity.class);
        presenterMock = Mockito.mock(NoteFragmentPresenterImpl.class);
        noteFragment.setPresenter(presenterMock);
    }

    @Test
    public void shouldCall_OnNoteTextChanged() {
        String text = "Teststring";
        ((TextInputLayout) noteFragment.getView().findViewById(R.id.note_wrapper)).getEditText().setText(text);
        Mockito.verify(presenterMock, Mockito.times(1)).onNoteTextChanged(text);
    }

    @Test
    public void shouldCall_Reset() {
        noteFragment.getView().findViewById(R.id.note_reset).performClick();
        Mockito.verify(presenterMock, Mockito.times(1)).resetClicked();
    }

    @Test
    public void shouldClearInput() {
        String text = "Testtext";
        ((TextInputLayout) noteFragment.getView().findViewById(R.id.note_wrapper)).getEditText().setText(text);
        noteFragment.resetNote();
        String actualText = ((TextInputLayout) noteFragment.getView().findViewById(R.id.note_wrapper)).getEditText().getText().toString();
        Assert.assertTrue("".equals(actualText));
    }
}
