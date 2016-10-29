package de.fha.bwi50101.graph.impl;

import com.github.mikephil.charting.data.Entry;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.fha.bwi50101.common.AppSettings;
import de.fha.bwi50101.common.Constants;
import de.fha.bwi50101.common.model.DiabetesDataType;
import de.fha.bwi50101.common.persistance.Repository;
import de.fha.bwi50101.graph.GraphPresenter;
import de.fha.bwi50101.overview.statistic.FetchAllEntriesInteractor;
import de.fha.bwi50101.overview.statistic.impl.FetchAllEntriesInteractorImpl;
import de.flhn.cleanboilerplate.domain.executor.Executor;
import de.flhn.cleanboilerplate.domain.executor.MainThread;
import de.flhn.cleanboilerplate.presentation.presenters.base.AbstractPresenter;

/**
 * Created by Florian on 26.10.2016.
 */

public class GraphPresenterImpl extends AbstractPresenter implements GraphPresenter, FetchAllEntriesInteractor.Callback {

    private final FetchAllEntriesInteractor interactor;
    private AppSettings appSettings;
    private View view;

    public GraphPresenterImpl(Executor executor, MainThread mainThread, AppSettings appSettings, View view, Repository repository) {
        super(executor, mainThread);
        this.appSettings = appSettings;
        this.view = view;
        this.interactor = new FetchAllEntriesInteractorImpl(mainThread, executor, repository, this);
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
    public void viewCreated() {
        ((FetchAllEntriesInteractorImpl) interactor).execute();
    }

    private boolean determineShouldShowGraph(List<Entry> filteredList) {
        if (filteredList.size() < 3) return false;
        Set<String> days = new HashSet<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Entry e : filteredList) {
            Date d = new Date((long) e.getX());
            days.add(sdf.format(d));
        }
        return days.size() >= 3;
    }

    private List<Entry> convertEntriesToGraphEntries(List<de.fha.bwi50101.common.model.Entry> entryList) {
        List<Entry> entries = new LinkedList<>();
        for (de.fha.bwi50101.common.model.Entry e : entryList) {
            if (!e.hasDiabetesDataOfType(DiabetesDataType.Glucose))
                continue;
            entries.add(new Entry(e.getDataCreatedAt().getTime(), e.getDiabetesDataOfType(DiabetesDataType.Glucose).getValue()));
        }
        return entries;

    }

    @Override
    public void onEntriesLoaded(List<de.fha.bwi50101.common.model.Entry> entryList) {
        List<Entry> entries = convertEntriesToGraphEntries(entryList);
        int upperValue = Integer.parseInt(appSettings.getString(Constants.SETTINGS_KEY_UPPER_BOUND));
        int lowerValue = Integer.parseInt(appSettings.getString(Constants.SETTINGS_KEY_LOWER_BOUND));
        boolean isValidForGraph = determineShouldShowGraph(entries);
        if (isValidForGraph) {
            Collections.sort(entries, new Comparator<Entry>() {
                @Override
                public int compare(Entry o1, Entry o2) {
                    return Float.compare(o1.getX(), o2.getX());
                }
            });
            view.displayGraph(entries, upperValue, lowerValue);
        }
    }
}
