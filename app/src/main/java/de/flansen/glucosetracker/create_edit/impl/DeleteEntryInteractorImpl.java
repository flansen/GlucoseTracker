package de.flansen.glucosetracker.create_edit.impl;

import de.flansen.glucosetracker.common.model.Entry;
import de.flansen.glucosetracker.common.persistance.Repository;
import de.flansen.glucosetracker.create_edit.DeleteEntryInteractor;
import de.flhn.cleanboilerplate.domain.executor.Executor;
import de.flhn.cleanboilerplate.domain.executor.MainThread;
import de.flhn.cleanboilerplate.domain.interactors.base.AbstractInteractor;

/**
 * Created by Florian on 20.10.2016.
 */

public class DeleteEntryInteractorImpl extends AbstractInteractor implements DeleteEntryInteractor {

    private final Repository repository;
    private final Entry entry;
    private final Callback callback;

    public DeleteEntryInteractorImpl(Executor threadExecutor, MainThread mainThread, Repository repository, Entry entry, Callback callback) {
        super(threadExecutor, mainThread);
        this.repository = repository;
        this.entry = entry;
        this.callback = callback;
    }

    @Override
    public void run() {
        repository.delete(entry);
        callback.onDeleted();
    }
}
