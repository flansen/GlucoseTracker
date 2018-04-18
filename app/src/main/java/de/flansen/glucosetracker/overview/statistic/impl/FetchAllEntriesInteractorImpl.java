package de.flansen.glucosetracker.overview.statistic.impl;

import java.util.List;

import de.flansen.glucosetracker.common.model.Entry;
import de.flansen.glucosetracker.common.persistance.Repository;
import de.flansen.glucosetracker.overview.statistic.FetchAllEntriesInteractor;
import de.flhn.cleanboilerplate.domain.executor.Executor;
import de.flhn.cleanboilerplate.domain.executor.MainThread;
import de.flhn.cleanboilerplate.domain.interactors.base.AbstractInteractor;

/**
 * Created by Florian on 09.10.2016.
 */

public class FetchAllEntriesInteractorImpl extends AbstractInteractor implements FetchAllEntriesInteractor {

    private final Repository repository;
    private final Callback callback;

    public FetchAllEntriesInteractorImpl(MainThread mainThread, Executor executor, Repository repository, Callback callback) {
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
