package de.flansen.glucosetracker.create_edit.impl;

import java.util.Date;

import de.flansen.glucosetracker.common.AppSettings;
import de.flansen.glucosetracker.common.model.Entry;
import de.flansen.glucosetracker.common.persistance.Repository;
import de.flansen.glucosetracker.create_edit.CreateEditEntryPresenter;
import de.flansen.glucosetracker.create_edit.DeleteEntryInteractor;
import de.flansen.glucosetracker.create_edit.FetchEntryForIdInteractor;
import de.flansen.glucosetracker.create_edit.SaveEntryInteractor;
import de.flansen.glucosetracker.overview.home.AlarmHandler;
import de.flansen.glucosetracker.overview.home.SaveAlarmInteractor;
import de.flansen.glucosetracker.overview.home.impl.SaveAlarmInteractorImpl;
import de.flansen.glucosetracker.overview.home.impl.SetAlarmInteractorImpl;
import de.flhn.cleanboilerplate.domain.executor.Executor;
import de.flhn.cleanboilerplate.domain.executor.MainThread;
import de.flhn.cleanboilerplate.presentation.presenters.base.AbstractPresenter;

/**
 * Created by Florian on 09.10.2016.
 */

public class CreateEditEntryPresenterImpl extends AbstractPresenter implements CreateEditEntryPresenter, FetchEntryForIdInteractor.Callback, SaveEntryInteractor.Callback, DeleteEntryInteractor.Callback {
    private final Repository repository;
    private View view;
    private Entry entry;
    private boolean isNewEntry;
    private AppSettings appSettings;
    private AlarmHandler alarmHandler;

    public CreateEditEntryPresenterImpl(MainThread mainThread, Executor threadExecutor, Repository repository, View view, AppSettings appSettings, AlarmHandler alarmHandler) {
        super(threadExecutor, mainThread);
        this.repository = repository;
        this.view = view;
        this.appSettings = appSettings;
        this.alarmHandler = alarmHandler;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void createNewEntry() {
        isNewEntry = true;
        entry = new Entry();
        entry.setCreatedAt(new Date());
        view.createTabs();
    }

    @Override
    public void loadEntryForId(long id) {
        isNewEntry = false;
        view.showLoading();
        new FetchEntryForIdInteractorImpl(mExecutor, mMainThread, repository, id, this).execute();
    }

    @Override
    public Entry getEntry() {
        if (entry == null) {
            throw new RuntimeException("Somebody wanted to get CreateEntryPresenters Entry but it was null.");
        }
        return entry;
    }

    @Override
    public void onSaveClicked() {
        entry.updateDataCreatedAt();
        SaveAlarmInteractor saveAlarmInteractor = new SaveAlarmInteractorImpl(mExecutor,
                mMainThread,
                appSettings,
                new SetAlarmInteractorImpl(mExecutor, mMainThread, alarmHandler)
        );

        SaveEntryInteractorImpl saveEntryInteractor = new SaveEntryInteractorImpl(mExecutor,
                mMainThread,
                this,
                repository,
                entry,
                isNewEntry,
                saveAlarmInteractor,
                appSettings);
        saveAlarmInteractor.setCallback(saveEntryInteractor);
        saveEntryInteractor.execute();
        view.showLoading();
    }

    @Override
    public void onDeleteClicked() {
        new DeleteEntryInteractorImpl(mExecutor, mMainThread, repository, entry, this).execute();
    }

    @Override
    public void entryFound(Entry entry) {
        this.entry = entry;
        view.finishLoading();
        view.createTabs();
    }

    @Override
    public void entrySaved(Entry entry) {
        view.finishLoading();
        view.finishWithEntryResult(entry);
    }

    @Override
    public void onDeleted() {
        view.finishWithEntryDeleted();
    }
}
