package de.fha.bwi50101.common;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import de.fha.bwi50101.common.impl.DAOConverterImpl;
import de.fha.bwi50101.common.model.DiabetesData;
import de.fha.bwi50101.common.model.DiabetesDataType;
import de.fha.bwi50101.common.model.Entry;
import de.fha.bwi50101.common.persistance.DiabetesDataDAO;
import de.fha.bwi50101.common.persistance.EntryDAO;

/**
 * Created by Florian on 06.10.2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class DAOConverterTest {
    private static final String TEST_NOTE = "Test note";
    private static final Date TEST_DATE = new Date();
    private static final Date TEST_DIABETES_DATE = new Date(1234567L);
    private static final Long TEST_ID = 1234L;
    private static final Long TEST_ID_DATA1 = 1235L;
    private static final Long TEST_ID_DATA2 = 1236L;
    private static final float TEST_VALUE_1 = 23f;
    private static final float TEST_VALUE_2 = 203f;
    private DiabetesData diabetesData1;
    private DiabetesData diabetesData2;

    private DiabetesDataDAO diabetesDataDAO1;
    private DiabetesDataDAO diabetesDataDAO2;

    private DAOConverter sut;

    @Before
    public void setUp() {
        sut = new DAOConverterImpl();

        diabetesData1 = new DiabetesData();
        diabetesData1.setId(TEST_ID_DATA1);
        diabetesData1.setValue(TEST_VALUE_1);
        diabetesData1.setDate(TEST_DIABETES_DATE);
        diabetesData1.setType(DiabetesDataType.Glucose);

        diabetesData2 = new DiabetesData();
        diabetesData2.setId(TEST_ID_DATA2);
        diabetesData2.setValue(TEST_VALUE_2);
        diabetesData2.setDate(TEST_DIABETES_DATE);
        diabetesData2.setType(DiabetesDataType.Food);

        diabetesDataDAO1 = new DiabetesDataDAO();
        diabetesDataDAO1.setValue(TEST_VALUE_1);
        diabetesDataDAO1.setId(TEST_ID_DATA1);
        diabetesDataDAO1.setDate(TEST_DIABETES_DATE);
        diabetesDataDAO1.setType(DiabetesDataType.Glucose);

        diabetesDataDAO2 = new DiabetesDataDAO();
        diabetesDataDAO2.setValue(TEST_VALUE_2);
        diabetesDataDAO2.setId(TEST_ID_DATA2);
        diabetesDataDAO2.setDate(TEST_DIABETES_DATE);
        diabetesDataDAO2.setType(DiabetesDataType.Food);
    }

    //region DAOToEntry
    @Test
    public void testConvertDAOToEntryWithDiabetesData() {
        EntryDAO entryDAO = createTestEntryDAOWithDiabetesData();
        Entry entry = sut.entryDAOtoEntry(entryDAO);
        compareEntryWithEntryDAO(entry, entryDAO);

        DiabetesData d1 = null, d2 = null;

        for (DiabetesData d : entry.getDiabetesData()) {
            if (d.getId() == diabetesDataDAO1.getId())
                d1 = d;
            else
                d2 = d;
        }

        compareDiabetesDataToDAO(d1, diabetesDataDAO1);
        compareDiabetesDataToDAO(d2, diabetesDataDAO2);
    }

    @Test
    public void testConvertDAOToEntryDAOWithoutDiabetesData() {
        EntryDAO entryDAO = createTestEntryDAOWithDiabetesData();
        entryDAO.setDiabetesData(new ArrayList<DiabetesDataDAO>());
        Entry e = sut.entryDAOtoEntry(entryDAO);
        Assert.assertEquals(entryDAO.getDiabetesData().size(), e.getDiabetesData().size());
    }
    //endregion


    //region EntryToDAO
    @Test
    public void testConvertEntryToDAOWithDiabetesData() {
        Entry e = createTestEntryWithDiabetesData();
        EntryDAO dao = sut.entryToEntryDAO(e);

        compareEntryWithEntryDAO(e, dao);

        DiabetesDataDAO d1DAO = null, d2DAO = null;
        for (DiabetesDataDAO dataDAO : dao.getDiabetesData()) {
            if (dataDAO.getId() == diabetesData1.getId())
                d1DAO = dataDAO;
            else
                d2DAO = dataDAO;
        }

        compareDiabetesDataToDAO(diabetesData1, d1DAO);
        compareDiabetesDataToDAO(diabetesData2, d2DAO);
    }

    @Test
    public void testConvertEntryToDAOWithoutDiabetesData() {
        Entry e = createTestEntryWithDiabetesData();
        e.setDiabetesDataAndUpdateDate(new ArrayList<DiabetesData>());
        EntryDAO dao = sut.entryToEntryDAO(e);
        Assert.assertEquals(e.getDiabetesData().size(), dao.getDiabetesData().size());
    }

    @Test
    public void testConvertEntryToDAOWithoutID() {
        Entry e = createTestEntryWithDiabetesData();
        e.setId(Constants.NO_ID);
        diabetesData1.setId(Constants.NO_ID);
        e.setDiabetesDataAndUpdateDate(Arrays.asList(diabetesData1));
        EntryDAO dao = sut.entryToEntryDAO(e);
        Assert.assertNull(dao.getId());
        Assert.assertNull(dao.getDiabetesData().get(0).getId());
    }
    //endregion

    private void compareEntryWithEntryDAO(Entry e, EntryDAO dao) {
        Assert.assertNotNull(e);
        Assert.assertNotNull(dao);
        Assert.assertEquals(e.getId(), (long) dao.getId());
        Assert.assertEquals(e.getDiabetesData().size(), dao.getDiabetesData().size());
        Assert.assertEquals(e.getNote(), dao.getNote());
        Assert.assertEquals(e.getCreatedAt(), dao.getCreatedAt());
        Assert.assertEquals(e.getDataCreatedAt(), dao.getDataCreatedAt());
    }

    private void compareDiabetesDataToDAO(DiabetesData diabetesData, DiabetesDataDAO dao) {
        Assert.assertNotNull(diabetesData);
        Assert.assertNotNull(dao);
        Assert.assertEquals(diabetesData.getDate(), dao.getDate());
        Assert.assertEquals(diabetesData.getId(), (long) dao.getId());
        Assert.assertEquals(diabetesData.getType(), dao.getType());
        Assert.assertEquals(diabetesData.getValue(), dao.getValue());
    }

    private Entry createTestEntryWithDiabetesData() {
        Entry entry = new Entry();
        entry.setNote(TEST_NOTE);
        entry.setCreatedAt(TEST_DATE);
        entry.setId(TEST_ID);
        entry.setDiabetesDataAndUpdateDate(Arrays.asList(diabetesData1, diabetesData2));
        return entry;
    }

    private EntryDAO createTestEntryDAOWithDiabetesData() {
        EntryDAO entry = new EntryDAO();
        entry.setNote(TEST_NOTE);
        entry.setCreatedAt(TEST_DATE);
        entry.setId(TEST_ID);
        entry.setDiabetesData(Arrays.asList(diabetesDataDAO1, diabetesDataDAO2));
        return entry;
    }

}
