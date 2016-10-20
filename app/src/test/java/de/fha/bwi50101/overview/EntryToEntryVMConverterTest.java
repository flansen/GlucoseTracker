package de.fha.bwi50101.overview;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import de.fha.bwi50101.BaseData;
import de.fha.bwi50101.common.DateConverter;
import de.fha.bwi50101.common.impl.DateConverterImpl;
import de.fha.bwi50101.common.model.DiabetesData;
import de.fha.bwi50101.common.model.DiabetesDataType;
import de.fha.bwi50101.common.model.Entry;
import de.fha.bwi50101.overview.statistic.EntryToEntryVMConverter;
import de.fha.bwi50101.overview.statistic.EntryVM;
import de.fha.bwi50101.overview.statistic.ListItem;
import de.fha.bwi50101.overview.statistic.impl.EntryToEntryVMConverterImpl;

/**
 * Created by Florian on 10.10.2016.
 */

public class EntryToEntryVMConverterTest extends BaseData {

    EntryToEntryVMConverter sut;
    DateConverter converterMock;
    List<Entry> mockList;

    @Before
    public void setUp() {
        converterMock = Mockito.mock(DateConverterImpl.class);
        sut = new EntryToEntryVMConverterImpl(converterMock);
        mockList = mockEntryList();
    }

    @Test
    public void shouldCall_ConverterConvert() {
        sut.toSectionedVMList(mockEntryList());
        Mockito.verify(converterMock, Mockito.times(mockList.size())).dateToOverviewDateString(Mockito.any(Date.class));
    }

    @Test
    public void testConversionResult() {
        Mockito.when(converterMock.dateToOverviewDateString(Mockito.any(Date.class))).thenReturn("01/01");
        List<Entry> localMockList = Arrays.asList(mockEntryList().get(0));
        List<ListItem> entryVMs = sut.toSectionedVMList(localMockList);
        //compareConversionResult(entryVMs, localMockList);
    }

    private void compareConversionResult(List<EntryVM> entryVMs, List<Entry> entryList) {
        Assert.assertEquals(entryVMs.size(), entryList.size());
        for (int i = 0; i < entryVMs.size(); i++) {
            EntryVM entryVM = entryVMs.get(i);
            Entry entry = entryList.get(i);
            DiabetesData food = null, glucose = null, insulin = null;
            for (DiabetesData dd : entry.getDiabetesData()) {
                if (dd.getType() == DiabetesDataType.Food)
                    food = dd;
                else if (dd.getType() == DiabetesDataType.Glucose)
                    glucose = dd;
                else if (dd.getType() == DiabetesDataType.StandardInsulin)
                    insulin = dd;
            }
            Assert.assertEquals(String.format(EntryToEntryVMConverterImpl.STRING_FORMAT, food.getValue()), entryVM.getFoodString());
            Assert.assertEquals(String.format(EntryToEntryVMConverterImpl.STRING_FORMAT, glucose.getValue()), entryVM.getGlucoseString());
            Assert.assertEquals(String.format(EntryToEntryVMConverterImpl.STRING_FORMAT, insulin.getValue()), entryVM.getInsulinString());
        }
    }


}
