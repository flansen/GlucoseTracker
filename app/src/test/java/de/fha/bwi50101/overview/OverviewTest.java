package de.fha.bwi50101.overview;

import android.content.Intent;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import de.fha.bwi50101.BuildConfig;
import de.fha.bwi50101.R;
import de.fha.bwi50101.create_edit.CreateEditActivity;

import static org.robolectric.Shadows.shadowOf;

/**
 * Created by Florian on 08.10.2016.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22)
public class OverviewTest {

    @Test
    public void clickCreate_shouldFireIntent() {
        OverviewActivity activity = Robolectric.setupActivity(OverviewActivity.class);
        activity.findViewById(R.id.home_add_entry).performClick();
        Intent expectedIntent = new Intent(activity, CreateEditActivity.class);
        Assert.assertTrue(expectedIntent.filterEquals(shadowOf(activity).getNextStartedActivity()));
    }


}
