package de.fha.bwi50101.common.persistance;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.orm.SugarContext;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import de.fha.bwi50101.common.Constants;
import de.fha.bwi50101.common.impl.RecordConverterImpl;
import de.fha.bwi50101.common.model.DiabetesData;
import de.fha.bwi50101.common.model.DiabetesDataType;
import de.fha.bwi50101.common.model.Entry;
import de.fha.bwi50101.common.persistance.impl.RepositoryImpl;

/**
 * Created by Florian on 06.10.2016.
 */
@RunWith(AndroidJUnit4.class)
public class RepositoryTest {
    Repository sut;
    DiabetesData d1, d2;

    @Before
    public void setUp() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        SugarContext.init(appContext);
        sut = new RepositoryImpl(new RecordConverterImpl());
    }

    @Test
    public void testSaveSetsIDs() {
        Entry e = createEntry();
        e = sut.save(e);
        assureIDsWereSet(e);

        Entry fetched = sut.findById(e.getId());

        assureEntriesAreEqual(e, fetched);
        assureDiabetesDataListIsEqual(e.getDiabetesData(), fetched.getDiabetesData());
    }

    @Test
    public void testSaveDatabaseEntryIsCorrect() {
        Entry e = createEntry();
        e = sut.save(e);
        Entry fetched = sut.findById(e.getId());
        assureEntriesAreEqual(e, fetched);
        assureDiabetesDataListIsEqual(e.getDiabetesData(), fetched.getDiabetesData());
    }



    @Test
    public void testFindMostRecentGlucose() {
        Entry e = createEntryWithMostRecentGlucose();
        e = sut.save(e);
        Entry fetched = sut.findMostRecentWithGlucoseValue();
        assureEntriesAreEqual(e, fetched);
    }

    @Test
    public void testFindNewerThan() {
        Entry e = createEntryWithCurrentDate();
        e = sut.save(e);
        List<Entry> fetched = sut.findNewerThan(new Date(e.getDataCreatedAt().getTime() - 1L));
        Assert.assertTrue(fetched.size() == 1);
        assureEntriesAreEqual(e, fetched.get(0));
    }

    @Test
    public void testFindAll() {
        Assert.assertTrue(sut.findAll().size() > 0);
    }

    @Test
    public void testUpdate() {
        Entry e = createEntry();
        e = sut.save(e);
        Entry fetched = sut.findById(e.getId());
        Assert.assertEquals(2, fetched.getDiabetesData().size());
        e.setDiabetesDataAndUpdateDate(Arrays.asList(d1));
        e = sut.save(e);
        fetched = sut.findById(e.getId());
        Assert.assertEquals(1, fetched.getDiabetesData().size());
        assureDiabetesDataAreEqual(e.getDiabetesData().get(0), fetched.getDiabetesData().get(0));
    }

    private Entry createEntryWithCurrentDate() {
        Entry e = createEntry();
        Date date = new Date();
        e.setDataCreatedAt(date);
        return e;
    }

    private void assureDiabetesDataListIsEqual(List<DiabetesData> l1, List<DiabetesData> l2) {
        DiabetesData d11, d12, d21 = null, d22 = null;
        d11 = l1.get(0);
        d12 = l1.get(1);
        for (DiabetesData d : l2) {
            if (d.getId() == d11.getId())
                d21 = d;
            else
                d22 = d;
        }
        Assert.assertNotNull(d11);
        Assert.assertNotNull(d12);
        Assert.assertNotNull(d21);
        Assert.assertNotNull(d22);
        assureDiabetesDataAreEqual(d11, d21);
        assureDiabetesDataAreEqual(d12, d22);
    }

    private void assureDiabetesDataAreEqual(DiabetesData d1, DiabetesData d2) {
        Assert.assertEquals(d1.getDate(), d2.getDate());
        Assert.assertEquals(d1.getId(), d2.getId());
        Assert.assertEquals(d1.getType(), d2.getType());
        Assert.assertEquals(d1.getValue(), d2.getValue());
    }

    private void assureEntriesAreEqual(Entry e1, Entry e2) {
        Assert.assertEquals(e1.getId(), e2.getId());
        Assert.assertEquals(e1.getCreatedAt(), e2.getCreatedAt());
        Assert.assertEquals(e1.getDataCreatedAt(), e2.getDataCreatedAt());
        Assert.assertEquals(e1.getNote(), e2.getNote());
        Assert.assertEquals(e1.getDiabetesData().size(), e2.getDiabetesData().size());
    }

    private Entry createEntry() {
        Entry e = new Entry();
        Date createdAt = new Date(1500000L);
        Date glucoseDataCreatedAt = new Date(1500010L);
        Date foodDataCreatedAt = new Date(1400010L);
        e.setNote("Test Note");
        e.setCreatedAt(createdAt);
        d1 = new DiabetesData();
        d1.setValue(123f);
        d1.setDate(glucoseDataCreatedAt);
        d1.setType(DiabetesDataType.Glucose);

        d2 = new DiabetesData();
        d2.setValue(10f);
        d2.setDate(foodDataCreatedAt);
        d2.setType(DiabetesDataType.Food);
        e.setDiabetesDataAndUpdateDate(Arrays.asList(d1, d2));
        return e;
    }


    private Entry createEntryWithMostRecentGlucose() {
        Entry e = createEntry();
        DiabetesData d = new DiabetesData();
        d.setType(DiabetesDataType.Glucose);
        d.setValue(1000f);
        Date date = new Date();
        d.setDate(date);
        e.setDiabetesDataAndUpdateDate(Arrays.asList(d1, d2, d));
        return e;
    }

    private void assureIDsWereSet(Entry e) {
        Assert.assertNotSame(e.getId(), Constants.NO_ID);
        Assert.assertNotSame(e.getDiabetesData().get(0).getId(), Constants.NO_ID);
        Assert.assertNotSame(e.getDiabetesData().get(1).getId(), Constants.NO_ID);
    }
}
