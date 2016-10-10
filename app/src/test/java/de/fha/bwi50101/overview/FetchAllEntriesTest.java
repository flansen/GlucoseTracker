package de.fha.bwi50101.overview;

import android.os.Looper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import de.fha.bwi50101.AndroidMockUtil;
import de.fha.bwi50101.BaseData;
import de.fha.bwi50101.common.model.Entry;
import de.fha.bwi50101.common.persistance.Repository;
import de.fha.bwi50101.common.persistance.impl.RepositoryImpl;
import de.fha.bwi50101.overview.statistic.FetchAllEntriesInteractor;
import de.fha.bwi50101.overview.statistic.impl.FetchAllEntriesInteractorImpl;
import de.flhn.cleanboilerplate.MainThreadImpl;
import de.flhn.cleanboilerplate.domain.executor.impl.ThreadExecutor;

/**
 * Created by Florian on 10.10.2016.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Looper.class, MainThreadImpl.class})
public class FetchAllEntriesTest extends BaseData {
    FetchAllEntriesInteractor sut;
    Repository mockRepository;
    FetchAllEntriesInteractor.Callback mockCallback;

    @Before
    public void setUp() throws Exception {
        mockCallback = Mockito.mock(FetchAllEntriesInteractor.Callback.class);
        mockRepository = Mockito.mock(RepositoryImpl.class);
        AndroidMockUtil.mockMainThreadHandler();
        sut = new FetchAllEntriesInteractorImpl(MainThreadImpl.getInstance(), ThreadExecutor.getInstance(), mockRepository, mockCallback);
    }

    @Test
    public void shouldCall_RepositoryFindAll() {
        ((FetchAllEntriesInteractorImpl) sut).run();
        Mockito.verify(mockRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void shouldCall_Callback() {
        List<Entry> mockList = mockEntryList();
        Mockito.when(mockRepository.findAll()).thenReturn(mockList);
        ((FetchAllEntriesInteractorImpl) sut).run();
        Mockito.verify(mockCallback, Mockito.times(1)).onEntriesLoaded(mockList);
    }
}
