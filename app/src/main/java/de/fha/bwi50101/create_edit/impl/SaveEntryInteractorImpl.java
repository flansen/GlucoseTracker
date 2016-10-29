package de.fha.bwi50101.create_edit.impl;

import java.util.Calendar;

import de.fha.bwi50101.common.AppSettings;
import de.fha.bwi50101.common.Constants;
import de.fha.bwi50101.common.model.Entry;
import de.fha.bwi50101.common.persistance.Repository;
import de.fha.bwi50101.create_edit.SaveEntryInteractor;
import de.fha.bwi50101.overview.home.LumindAlarm;
import de.fha.bwi50101.overview.home.SaveAlarmInteractor;
import de.flhn.cleanboilerplate.domain.executor.Executor;
import de.flhn.cleanboilerplate.domain.executor.MainThread;
import de.flhn.cleanboilerplate.domain.interactors.base.AbstractInteractor;

/**
 * Created by Florian on 17.10.2016.
 */

public class SaveEntryInteractorImpl extends AbstractInteractor implements SaveEntryInteractor, SaveAlarmInteractor.Callback {
    private final Entry entry;
    private final SaveAlarmInteractor saveAlarmInteractor;
    private final AppSettings appSettings;
    private final boolean isNewEntry;
    private Callback callback;
    private Repository repository;
    private Entry resultEntry;

    public SaveEntryInteractorImpl(Executor threadExecutor, MainThread mainThread, Callback callback, Repository repository, Entry entry, boolean isNewEntry, SaveAlarmInteractor saveAlarmInteractor, AppSettings appSettings) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.repository = repository;
        this.entry = entry;
        this.isNewEntry = isNewEntry;
        this.saveAlarmInteractor = saveAlarmInteractor;
        this.appSettings = appSettings;
    }

    @Override
    public void run() {
        resultEntry = repository.save(entry);
        if (isNewEntry) {
            int alarmInterval = appSettings.getInt(Constants.SETTINGS_KEY_ALARM_INTERVAL, 3);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.HOUR_OF_DAY, alarmInterval);
            LumindAlarm alarm = new LumindAlarm(true, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
            saveAlarmInteractor.saveAlarm(alarm);
        } else {
            postResult(resultEntry);
        }
    }

    private void postResult(final Entry savedEntry) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.entrySaved(savedEntry);
            }
        });
    }

    @Override
    public void onAlarmSaved(LumindAlarm alarm) {
        postResult(resultEntry);
    }
}
