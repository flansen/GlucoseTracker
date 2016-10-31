package de.fha.bwi50101.overview;

import android.content.Intent;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;

import de.fha.bwi50101.BuildConfig;
import de.fha.bwi50101.R;
import de.fha.bwi50101.create_edit.CreateEditActivity;
import de.fha.bwi50101.overview.home.HomeFragment;
import de.fha.bwi50101.overview.statistic.StatisticsFragment;
import de.fha.bwi50101.settings.SettingsActivity;

import static org.robolectric.Shadows.shadowOf;

/**
 * Created by Florian on 08.10.2016.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22)
public class OverviewTest {
    OverviewActivity activity;

    @Before
    public void setUp() {
        activity = Robolectric.setupActivity(OverviewActivity.class);
    }

    @Test
    public void clickCreate_shouldFireIntent() {
        activity.findViewById(R.id.home_add_entry).performClick();
        Intent expectedIntent = new Intent(activity, CreateEditActivity.class);
        Assert.assertTrue(expectedIntent.filterEquals(shadowOf(activity).getNextStartedActivity()));
    }

    @Test
    public void clickStatCreate_shouldFireIntent() {
        activity.findViewById(R.id.statistics_fab).performClick();
        Intent expectedIntent = new Intent(activity, CreateEditActivity.class);
        Assert.assertTrue(expectedIntent.filterEquals(shadowOf(activity).getNextStartedActivity()));
    }

    @Test
    public void clickSettings_shouldFireIntent() {
        ShadowActivity shadowActivity = shadowOf(activity);
        shadowActivity.clickMenuItem(R.id.action_settings);
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        String name = shadowIntent.getIntentClass().getName();
        junit.framework.Assert.assertEquals(name, SettingsActivity.class.getName());
    }

    @Test
    public void testFragmentCreation() {
        Assert.assertTrue(activity.getFragments() != null);
        Assert.assertEquals(2, activity.getFragments().size());
        Assert.assertTrue(activity.getFragments().containsKey(HomeFragment.class));
        Assert.assertTrue(activity.getFragments().containsKey(StatisticsFragment.class));
        Assert.assertTrue(activity.getFragments().get(HomeFragment.class) instanceof HomeFragment);
        Assert.assertTrue(activity.getFragments().get(StatisticsFragment.class) instanceof StatisticsFragment);
    }

}
