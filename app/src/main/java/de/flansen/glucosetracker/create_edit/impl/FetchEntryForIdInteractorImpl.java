package de.flansen.glucosetracker.create_edit.impl;

import de.flansen.glucosetracker.common.model.Entry;
import de.flansen.glucosetracker.common.persistance.Repository;
import de.flansen.glucosetracker.create_edit.FetchEntryForIdInteractor;
import de.flhn.cleanboilerplate.domain.executor.Executor;
import de.flhn.cleanboilerplate.domain.executor.MainThread;
import de.flhn.cleanboilerplate.domain.interactors.base.AbstractInteractor;

/**
 * Created by Florian on 15.10.2016.
 */

public class FetchEntryForIdInteractorImpl extends AbstractInteractor implements FetchEntryForIdInteractor {
    private final Repository repository;
    private final long id;
    private final Callback callback;

    public FetchEntryForIdInteractorImpl(Executor threadExecutor, MainThread mainThread, Repository repository, long id, Callback callback) {
        super(threadExecutor, mainThread);
        this.repository = repository;
        this.id = id;
        this.callback = callback;
    }

    @Override
    public void run() {
        Entry entry = repository.findById(id);
        callback.entryFound(entry);
    }
}
