package de.fha.bwi50101.overview.statistic.impl;

import java.util.List;

import de.fha.bwi50101.common.model.Entry;
import de.fha.bwi50101.overview.statistic.EntryToEntryVMConverter;
import de.fha.bwi50101.overview.statistic.FetchAllEntriesInteractor;
import de.fha.bwi50101.overview.statistic.ListItem;
import de.fha.bwi50101.overview.statistic.StatisticsFragmentPresenter;

/**
 * Created by Florian on 08.10.2016.
 */

public class StatisticsFragmentPresenterImpl implements StatisticsFragmentPresenter, FetchAllEntriesInteractor.Callback {
    private View view;
    private EntryToEntryVMConverter entryConverter;
    private FetchAllEntriesInteractor interactor;

    public StatisticsFragmentPresenterImpl(View view, EntryToEntryVMConverter entryConverter) {
        this.view = view;
        this.entryConverter = entryConverter;
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
    public void loadEntries() {
        if (interactor != null) {
            ((FetchAllEntriesInteractorImpl) interactor).execute();
            view.showLoading();
        }
    }

    @Override
    public void setInteractor(FetchAllEntriesInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onEntriesLoaded(List<Entry> entries) {
        List<ListItem> entryVMList = entryConverter.toSectionedVMList(entries);
        view.hideLoading();
        view.onEntriesLoaded(entryVMList);
    }
}
