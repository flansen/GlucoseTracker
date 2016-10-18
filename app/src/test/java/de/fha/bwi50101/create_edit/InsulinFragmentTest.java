package de.fha.bwi50101.create_edit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import de.fha.bwi50101.BuildConfig;
import de.fha.bwi50101.R;
import de.fha.bwi50101.create_edit.impl.InsulinFragmentPresenterImpl;
import de.fha.bwi50101.create_edit.insulin.InsulinFragment;
import de.fha.bwi50101.create_edit.insulin.InsulinFragmentPresenter;

/**
 * Created by Florian on 18.10.2016.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22)
public class InsulinFragmentTest {
    InsulinFragment sut;
    InsulinFragmentPresenter presenter;

    @Before
    public void setUp() {
        presenter = Mockito.mock(InsulinFragmentPresenterImpl.class);
        sut = InsulinFragment.newInstance();
        SupportFragmentTestUtil.startFragment(sut, CreateEditActivity.class);
        sut.setPresenter(presenter);
    }

    @Test
    public void shouldCall_Reset() {
        sut.getView().findViewById(R.id.insulin_reset).performClick();
        Mockito.verify(presenter, Mockito.times(1)).resetClicked();
    }
}
