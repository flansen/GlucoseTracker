package de.fha.bwi50101.graph.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import de.fha.bwi50101.common.model.DiabetesDataType;
import de.fha.bwi50101.common.model.Entry;
import de.fha.bwi50101.common.persistance.Repository;
import de.fha.bwi50101.graph.FetchEntriesForGraphInteractor;
import de.flhn.cleanboilerplate.domain.executor.Executor;
import de.flhn.cleanboilerplate.domain.executor.MainThread;
import de.flhn.cleanboilerplate.domain.interactors.base.AbstractInteractor;

/**
 * Created by Florian on 26.10.2016.
 */

public class FetchEntriesForGraphInteractorImpl extends AbstractInteractor implements FetchEntriesForGraphInteractor {
    private static final int WEEK_OFFSET = 1000 * 60 * 60 * 24 * 5;
    private final Repository repository;
    private final Callback callback;

    public FetchEntriesForGraphInteractorImpl(Executor threadExecutor, MainThread mainThread, Repository repository, Callback callback) {
        super(threadExecutor, mainThread);
        this.repository = repository;
        this.callback = callback;
    }

    @Override
    public void run() {
        Date newerThanDate = new Date(new Date().getTime() - WEEK_OFFSET);
        List<Entry> entryList = repository.findNewerThan(newerThanDate);
        final List<Entry> filteredList = new LinkedList<>();
        for (Entry e : entryList) {
            if (e.hasDiabetesDataOfType(DiabetesDataType.Glucose))
                filteredList.add(e);
        }
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onEntriesFetched(filteredList);
            }
        });
    }
}
