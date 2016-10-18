package de.fha.bwi50101.create_edit;

import android.os.Looper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import de.fha.bwi50101.AndroidMockUtil;
import de.fha.bwi50101.common.model.Entry;
import de.fha.bwi50101.common.persistance.Repository;
import de.fha.bwi50101.common.persistance.impl.RepositoryImpl;
import de.fha.bwi50101.create_edit.impl.FetchEntryForIdInteractorImpl;
import de.flhn.cleanboilerplate.MainThreadImpl;
import de.flhn.cleanboilerplate.domain.executor.impl.ThreadExecutor;

/**
 * Created by Florian on 18.10.2016.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Looper.class, MainThreadImpl.class})
public class FetchEntryForIdInteractorTest {
    FetchEntryForIdInteractor sut;
    Repository repositoryMock;
    FetchEntryForIdInteractor.Callback callbackMock;
    long id;

    @Before
    public void setUp() throws Exception {
        id = 1;
        AndroidMockUtil.mockMainThreadHandler();
        callbackMock = Mockito.mock(FetchEntryForIdInteractor.Callback.class);
        repositoryMock = Mockito.mock(RepositoryImpl.class);
        sut = new FetchEntryForIdInteractorImpl(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), repositoryMock, id, callbackMock);
    }

    @Test
    public void shouldCall_FindById() {
        sut.execute();
        Mockito.verify(repositoryMock, Mockito.times(1)).findById(id);
    }

    @Test
    public void shouldCall_Callback() {
        Entry entryMock = Mockito.mock(Entry.class);
        Mockito.when(repositoryMock.findById(id)).thenReturn(entryMock);
        ((FetchEntryForIdInteractorImpl) sut).run();
        Mockito.verify(callbackMock, Mockito.times(1)).entryFound(entryMock);
    }
}
