package de.fha.bwi50101.overview.statistic.impl;

import java.util.List;

import de.fha.bwi50101.common.model.Entry;
import de.fha.bwi50101.common.persistance.Repository;
import de.fha.bwi50101.overview.statistic.FetchAllEntriesInteractor;
import de.flhn.cleanboilerplate.domain.executor.Executor;
import de.flhn.cleanboilerplate.domain.executor.MainThread;
import de.flhn.cleanboilerplate.domain.interactors.base.AbstractInteractor;

/**
 * Created by Florian on 09.10.2016.
 */

public class FetchAllEntriesInteractorImpl extends AbstractInteractor implements FetchAllEntriesInteractor {

    private final Repository repository;
    private final Callback callback;

    public FetchAllEntriesInteractorImpl(Repository repository, Callback callback, MainThread mainThread, Executor executor) {
        super(executor, mainThread);
        this.repository = repository;
        this.callback = callback;
    }

    @Override
    public void run() {
        final List<Entry> entryList = repository.findAll();
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onEntriesLoaded(entryList);
            }
        });
    }
}
