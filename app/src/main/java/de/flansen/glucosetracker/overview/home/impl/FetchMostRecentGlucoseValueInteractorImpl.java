package de.flansen.glucosetracker.overview.home.impl;

import de.flansen.glucosetracker.common.model.Entry;
import de.flansen.glucosetracker.common.persistance.Repository;
import de.flansen.glucosetracker.overview.home.FetchMostRecentGlucoseValueInteractor;
import de.flhn.cleanboilerplate.domain.executor.Executor;
import de.flhn.cleanboilerplate.domain.executor.MainThread;
import de.flhn.cleanboilerplate.domain.interactors.base.AbstractInteractor;

/**
 * Created by Florian on 20.10.2016.
 */

public class FetchMostRecentGlucoseValueInteractorImpl extends AbstractInteractor implements FetchMostRecentGlucoseValueInteractor {

    private final Repository repository;
    private final Callback callback;

    public FetchMostRecentGlucoseValueInteractorImpl(Executor threadExecutor, MainThread mainThread, Repository repository, Callback callback) {
        super(threadExecutor, mainThread);
        this.repository = repository;
        this.callback = callback;
    }


    @Override
    public void run() {
        final Entry entry = repository.findMostRecentWithGlucoseValue();

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onEntry(entry);
            }
        });

    }
}
