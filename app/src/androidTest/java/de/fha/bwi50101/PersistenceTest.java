package de.fha.bwi50101;

import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import de.fha.bwi50101.common.model.Entry;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class PersistenceTest {


    @Test
    public void TestSaveEntry() throws Exception {

        Entry entry = new Entry();
        Assert.assertTrue(entry.getId() == 0L);
        Date createdAt = new Date(15000000L);
        entry.save();
        Assert.assertTrue(entry.getId() != 0L);
        System.out.println("Id is " + entry.getId());
    }
}
