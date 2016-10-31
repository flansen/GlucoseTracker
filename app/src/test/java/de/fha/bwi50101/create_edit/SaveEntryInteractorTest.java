package de.fha.bwi50101.create_edit;

import android.os.Looper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import de.fha.bwi50101.AndroidMockUtil;
import de.fha.bwi50101.common.AppSettings;
import de.fha.bwi50101.common.model.Entry;
import de.fha.bwi50101.common.persistance.Repository;
import de.fha.bwi50101.common.persistance.impl.RepositoryImpl;
import de.fha.bwi50101.create_edit.impl.SaveEntryInteractorImpl;
import de.fha.bwi50101.overview.home.SaveAlarmInteractor;
import de.flhn.cleanboilerplate.MainThreadImpl;
import de.flhn.cleanboilerplate.domain.executor.impl.ThreadExecutor;

/**
 * Created by Florian on 18.10.2016.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Looper.class, MainThreadImpl.class})
public class SaveEntryInteractorTest {
    SaveEntryInteractorImpl sut;
    Repository repositoryMock;
    SaveEntryInteractor.Callback callbackMock;
    Entry entryMock;
    AppSettings appSettingsMock;
    SaveAlarmInteractor saveAlarmInteractorMock;

    @Before
    public void setUp() throws Exception {
        repositoryMock = Mockito.mock(RepositoryImpl.class);
        callbackMock = Mockito.mock(SaveEntryInteractor.Callback.class);
        entryMock = Mockito.mock(Entry.class);
        appSettingsMock = Mockito.mock(AppSettings.class);
        saveAlarmInteractorMock = Mockito.mock(SaveAlarmInteractor.class);
        AndroidMockUtil.mockMainThreadHandler();
        sut = new SaveEntryInteractorImpl(ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                callbackMock,
                repositoryMock,
                entryMock,
                true,
                saveAlarmInteractorMock,
                appSettingsMock
        );
    }

    @Test
    public void shouldCall_RepositorySave() {
        sut.execute();
        Mockito.verify(repositoryMock, Mockito.times(1)).save(entryMock);
    }

    @Test
    public void shouldCall_Callback() {
        Entry savedEntryMock = Mockito.mock(Entry.class);
        Mockito.when(repositoryMock.save(entryMock)).thenReturn(savedEntryMock);
        sut.run();
        Mockito.verify(callbackMock, Mockito.times(1)).entrySaved(savedEntryMock);
    }
}
