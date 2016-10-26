package de.fha.bwi50101.overview.home.impl;

import de.fha.bwi50101.common.model.DiabetesData;
import de.fha.bwi50101.common.model.DiabetesDataType;
import de.fha.bwi50101.common.model.Entry;
import de.fha.bwi50101.common.persistance.Repository;
import de.fha.bwi50101.overview.home.FetchMostRecentGlucoseValueInteractor;
import de.fha.bwi50101.overview.home.HomeFragmentPresenter;
import de.flhn.cleanboilerplate.domain.executor.Executor;
import de.flhn.cleanboilerplate.domain.executor.MainThread;

/**
 * Created by Florian on 08.10.2016.
 */

public class HomeFragmentPresenterImpl implements HomeFragmentPresenter, FetchMostRecentGlucoseValueInteractor.Callback {
    private final Executor executor;
    private final MainThread mainThread;
    private final Repository repository;
    private View view;
    private Entry recentEntry;

    public HomeFragmentPresenterImpl(Executor executor, MainThread mainThread, Repository repository) {
        this.executor = executor;
        this.mainThread = mainThread;
        this.repository = repository;
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
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void fetchMostRecentGlucoseValue() {
        new FetchMostRecentGlucoseValueInteractorImpl(executor, mainThread, repository, this).execute();
    }

    @Override
    public void onEntry(Entry entry) {
        //FIXME
        if (entry == null) {
            //TODO Hide unit label
            view.displayMostRecentValue("no data");
            return;
        }
        DiabetesData dd = entry.getDiabetesDataOfType(DiabetesDataType.Glucose);
        String displayString = String.format("%.0f", dd.getValue());
        view.displayMostRecentValue(displayString);
    }

    @Override
    public void onError() {
        view.onError();
    }
}
