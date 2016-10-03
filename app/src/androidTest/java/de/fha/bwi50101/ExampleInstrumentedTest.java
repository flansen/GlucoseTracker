package de.fha.bwi50101;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.orm.SugarContext;

import org.junit.Test;
import org.junit.runner.RunWith;

import de.fha.bwi50101.common.model.Entry;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("de.fha.bwi50101", appContext.getPackageName());
        SugarContext.init(appContext);
        Entry e = new Entry();
        e.save();
        System.out.println(e.getId() + " -a-sdf-sad-gs-dgh-sdfh-d-h-");
    }
}
