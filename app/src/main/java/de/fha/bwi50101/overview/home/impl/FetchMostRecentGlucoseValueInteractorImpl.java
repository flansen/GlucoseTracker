package de.fha.bwi50101.overview.home.impl;

import de.fha.bwi50101.common.model.Entry;
import de.fha.bwi50101.common.persistance.Repository;
import de.fha.bwi50101.overview.home.FetchMostRecentGlucoseValueInteractor;
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
        Entry entry = repository.findMostRecentWithGlucoseValue();
        callback.onEntry(entry);
    }
}
