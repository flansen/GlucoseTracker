package de.fha.bwi50101.create_edit.impl;

import java.util.Date;

import de.fha.bwi50101.common.model.Entry;
import de.fha.bwi50101.common.persistance.Repository;
import de.fha.bwi50101.create_edit.CreateEditEntryPresenter;
import de.fha.bwi50101.create_edit.DeleteEntryInteractor;
import de.fha.bwi50101.create_edit.FetchEntryForIdInteractor;
import de.fha.bwi50101.create_edit.SaveEntryInteractor;
import de.flhn.cleanboilerplate.domain.executor.Executor;
import de.flhn.cleanboilerplate.domain.executor.MainThread;

/**
 * Created by Florian on 09.10.2016.
 */

public class CreateEditEntryPresenterImpl implements CreateEditEntryPresenter, FetchEntryForIdInteractor.Callback, SaveEntryInteractor.Callback, DeleteEntryInteractor.Callback {
    private final MainThread mainThread;
    private final Executor threadExecutor;
    private final Repository repository;
    private View view;
    private Entry entry;

    public CreateEditEntryPresenterImpl(MainThread mainThread, Executor threadExecutor, Repository repository, View view) {
        this.mainThread = mainThread;
        this.threadExecutor = threadExecutor;
        this.repository = repository;
        this.view = view;
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
        entry = new Entry();
        entry.setCreatedAt(new Date());
        view.createTabs();
    }

    @Override
    public void loadEntryForId(long id) {
        view.showLoading();
        new FetchEntryForIdInteractorImpl(threadExecutor, mainThread, repository, id, this).execute();
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
        new SaveEntryInteractorImpl(threadExecutor, mainThread, this, repository, entry).execute();
        view.showLoading();
    }

    @Override
    public void onDeleteClicked() {
        new DeleteEntryInteractorImpl(threadExecutor, mainThread, repository, entry, this).execute();
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
