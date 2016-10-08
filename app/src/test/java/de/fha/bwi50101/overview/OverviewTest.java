package de.fha.bwi50101.overview;

import android.content.Intent;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import de.fha.bwi50101.BuildConfig;
import de.fha.bwi50101.R;
import de.fha.bwi50101.create.CreateEntryActivity;

import static org.robolectric.Shadows.shadowOf;

/**
 * Created by Florian on 08.10.2016.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22)

public class OverviewTest {
    @Test
    public void clickCreate_shouldExecutePresenterOnCreate() {
        OverviewPresenter mock = Mockito.mock(OverviewPresenter.class);
        OverviewActivity activity = Robolectric.setupActivity(OverviewActivity.class);
        activity.setPresenter(mock);
        activity.findViewById(R.id.fab_create).performClick();
        Mockito.verify(mock, Mockito.times(1)).onCreateClicked();
    }

    @Test
    public void clickCreate_shouldFireIntent() {
        OverviewActivity activity = Robolectric.setupActivity(OverviewActivity.class);
        activity.findViewById(R.id.fab_create).performClick();
        Intent expectedIntent = new Intent(activity, CreateEntryActivity.class);
        Assert.assertTrue(expectedIntent.filterEquals(shadowOf(activity).getNextStartedActivity()));
    }


}
