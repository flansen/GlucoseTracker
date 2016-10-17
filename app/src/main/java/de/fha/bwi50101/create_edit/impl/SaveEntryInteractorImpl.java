package de.fha.bwi50101.create_edit.impl;

import de.fha.bwi50101.common.model.Entry;
import de.fha.bwi50101.common.persistance.Repository;
import de.fha.bwi50101.create_edit.SaveEntryInteractor;
import de.flhn.cleanboilerplate.domain.executor.Executor;
import de.flhn.cleanboilerplate.domain.executor.MainThread;
import de.flhn.cleanboilerplate.domain.interactors.base.AbstractInteractor;

/**
 * Created by Florian on 17.10.2016.
 */

public class SaveEntryInteractorImpl extends AbstractInteractor implements SaveEntryInteractor {
    private final Entry entry;
    private Callback callback;
    private Repository repository;

    public SaveEntryInteractorImpl(Executor threadExecutor, MainThread mainThread, Callback callback, Repository repository, Entry entry) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.repository = repository;
        this.entry = entry;
    }

    @Override
    public void run() {
        final Entry savedEntry = repository.save(entry);
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.entrySaved(savedEntry);
            }
        });
    }
}
