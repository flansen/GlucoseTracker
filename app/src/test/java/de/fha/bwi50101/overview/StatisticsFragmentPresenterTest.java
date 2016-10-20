package de.fha.bwi50101.overview;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import de.fha.bwi50101.BaseData;
import de.fha.bwi50101.common.model.Entry;
import de.fha.bwi50101.overview.statistic.EntryToEntryVMConverter;
import de.fha.bwi50101.overview.statistic.FetchAllEntriesInteractor;
import de.fha.bwi50101.overview.statistic.ListItem;
import de.fha.bwi50101.overview.statistic.StatisticsFragmentPresenter;
import de.fha.bwi50101.overview.statistic.impl.EntryToEntryVMConverterImpl;
import de.fha.bwi50101.overview.statistic.impl.FetchAllEntriesInteractorImpl;
import de.fha.bwi50101.overview.statistic.impl.StatisticsFragmentPresenterImpl;

/**
 * Created by Florian on 10.10.2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class StatisticsFragmentPresenterTest extends BaseData {

    StatisticsFragmentPresenter sut;
    FetchAllEntriesInteractorImpl interactorMock;
    EntryToEntryVMConverter converterMock;
    StatisticsFragmentPresenter.View viewMock;

    @Before
    public void setUp() {
        interactorMock = Mockito.mock(FetchAllEntriesInteractorImpl.class);
        converterMock = Mockito.mock(EntryToEntryVMConverterImpl.class);
        viewMock = Mockito.mock(StatisticsFragmentPresenter.View.class);
        sut = new StatisticsFragmentPresenterImpl(viewMock, converterMock);
        sut.setInteractor(interactorMock);
    }


    @Test
    public void shouldCall_InteractorExecute() {
        sut.loadEntries();
        Mockito.verify(interactorMock, Mockito.times(1)).execute();
    }

    @Test
    public void shouldCall_ConverterConvert() {
        List<Entry> mockEntries = mockEntryList();
        ((FetchAllEntriesInteractor.Callback) sut).onEntriesLoaded(mockEntries);
        Mockito.verify(converterMock, Mockito.times(1)).toSectionedVMList(mockEntries);
    }

    @Test
    public void shouldCall_ViewOnEntriesLoaded() {
        List<Entry> mockEntries = mockEntryList();
        ((FetchAllEntriesInteractor.Callback) sut).onEntriesLoaded(mockEntries);
        Mockito.verify(viewMock, Mockito.times(1)).onEntriesLoaded(Mockito.anyListOf(ListItem.class));
    }
}
