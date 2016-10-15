package de.fha.bwi50101.create_edit.impl;

import de.fha.bwi50101.common.model.Entry;
import de.fha.bwi50101.common.persistance.Repository;
import de.fha.bwi50101.create_edit.CreateEditEntryPresenter;
import de.fha.bwi50101.create_edit.FetchEntryForIdInteractor;
import de.flhn.cleanboilerplate.domain.executor.Executor;
import de.flhn.cleanboilerplate.domain.executor.MainThread;

/**
 * Created by Florian on 09.10.2016.
 */

public class CreateEditEntryPresenterImpl implements CreateEditEntryPresenter, FetchEntryForIdInteractor.Callback {
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
    }

    @Override
    public void loadEntryForId(long id) {
        view.displayLoading();
        new FetchEntryForIdInteractorImpl(threadExecutor, mainThread, repository, id, this).execute();
    }

    @Override
    public Entry getEntry() {
        //FIXME: This might break the code. When editing, the fetch for entry may be faster than the db request.
        if (entry == null) {
            throw new RuntimeException("Somebody wanted to get CreateEntryPresenters Entry but it was null.");
        }
        return entry;
    }

    @Override
    public void entryFound(Entry entry) {
        this.entry = entry;
        view.finishLoading();
    }
}
