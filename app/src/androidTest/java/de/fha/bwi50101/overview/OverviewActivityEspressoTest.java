package de.fha.bwi50101.overview;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;

import org.junit.Rule;
import org.junit.runner.RunWith;

import java.util.Collection;

/**
 * Created by Florian on 08.10.2016.
 */
@RunWith(AndroidJUnit4.class)
public class OverviewActivityEspressoTest {
    @Rule
    public ActivityTestRule<OverviewActivity> mActivityRule =
            new ActivityTestRule<>(OverviewActivity.class);

  /*  @Test
    public void testClickFAB() {
        Espresso.onView(ViewMatchers.withId(R.id.fab_create))
                .perform(ViewActions.click());
        Activity activity = getActivityInstance();
        boolean b = (activity instanceof CreateEditActivity);
        assertTrue(b);
    }*/


    //http://www.vogella.com/tutorials/AndroidTestingEspresso/article.html#espresso_usageintroduction_viewaction
    public Activity getActivityInstance() {
        final Activity[] activity = new Activity[1];
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            public void run() {
                Activity currentActivity = null;
                Collection resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
                if (resumedActivities.iterator().hasNext()) {
                    currentActivity = (Activity) resumedActivities.iterator().next();
                    activity[0] = currentActivity;
                }
            }
        });

        return activity[0];
    }
}
