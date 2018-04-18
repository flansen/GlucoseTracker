package de.flansen.glucosetracker.create_edit.impl;

import java.util.Calendar;

import de.flansen.glucosetracker.common.AppSettings;
import de.flansen.glucosetracker.common.Constants;
import de.flansen.glucosetracker.common.model.Entry;
import de.flansen.glucosetracker.common.persistance.Repository;
import de.flansen.glucosetracker.create_edit.SaveEntryInteractor;
import de.flansen.glucosetracker.overview.home.LumindAlarm;
import de.flansen.glucosetracker.overview.home.SaveAlarmInteractor;
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
