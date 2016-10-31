package de.fha.bwi50101.overview;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import de.fha.bwi50101.BuildConfig;
import de.fha.bwi50101.R;
import de.fha.bwi50101.overview.home.HomeFragment;
import de.fha.bwi50101.overview.home.HomeFragmentPresenter;

/**
 * Created by Florian on 31.10.2016.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22)
public class HomeFragmentTest {
    HomeFragment sut;
    HomeFragmentPresenter presenter;

    @Before
    public void setUp() {
        presenter = Mockito.mock(HomeFragmentPresenter.class);
        sut = HomeFragment.newInstance();
        SupportFragmentTestUtil.startFragment(sut, OverviewActivity.class);
        sut.setPresenter(presenter);
    }

    @Test
    public void shouldCall_TimerStateChanged() {
        sut.getView().findViewById(R.id.home_alarm_switch).performClick();
        Mockito.verify(presenter).handleTimerStateChanged(Mockito.anyBoolean());
    }

    @Test
    public void shouldCall_SetView() {
        Mockito.verify(presenter).setView(sut);
    }
}
