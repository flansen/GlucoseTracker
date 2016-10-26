package de.fha.bwi50101.graph.impl;

import com.github.mikephil.charting.data.Entry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import de.fha.bwi50101.common.AppSettings;
import de.fha.bwi50101.common.Constants;
import de.fha.bwi50101.common.model.DiabetesDataType;
import de.fha.bwi50101.common.persistance.Repository;
import de.fha.bwi50101.graph.FetchEntriesForGraphInteractor;
import de.fha.bwi50101.graph.GraphPresenter;
import de.flhn.cleanboilerplate.domain.executor.Executor;
import de.flhn.cleanboilerplate.domain.executor.MainThread;
import de.flhn.cleanboilerplate.presentation.presenters.base.AbstractPresenter;

/**
 * Created by Florian on 26.10.2016.
 */

public class GraphPresenterImpl extends AbstractPresenter implements GraphPresenter, FetchEntriesForGraphInteractor.Callback {

    private final FetchEntriesForGraphInteractor interactor;
    private AppSettings appSettings;
    private View view;

    public GraphPresenterImpl(Executor executor, MainThread mainThread, AppSettings appSettings, View view, Repository repository) {
        super(executor, mainThread);
        this.appSettings = appSettings;
        this.view = view;
        this.interactor = new FetchEntriesForGraphInteractorImpl(executor, mainThread, repository, this);
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
        interactor.execute();
        /*List<Entry> filteredList = mock();
        int upperValue = Integer.parseInt(appSettings.getString(Constants.SETTINGS_KEY_UPPER_BOUND));
        int lowerValue = Integer.parseInt(appSettings.getString(Constants.SETTINGS_KEY_LOWER_BOUND));

        boolean isValidForGraph = determineShouldShowGraph(filteredList);
        if (isValidForGraph)
            view.displayGraph(filteredList, lowerValue, upperValue);*/
    }

    private List<Entry> mock() {
        List<Entry> entries = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            Date d = new Date(new Date().getTime() - 1000 * 60 * 60 * 24 * (7 - i));
            Entry entry = new Entry(d.getTime(), 250 * r.nextFloat());
            entries.add(entry);
        }
        return entries;
    }

    private boolean determineShouldShowGraph(List<Entry> filteredList) {
        if (filteredList.size() < 3) return false;
        Set<String> days = new HashSet<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Entry e : filteredList) {
            Date d = new Date((long) e.getX());
            days.add(sdf.format(d));
        }
        return days.size() > 3;
    }

    @Override
    public void onEntriesFetched(List<de.fha.bwi50101.common.model.Entry> entryList) {
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

    private List<Entry> convertEntriesToGraphEntries(List<de.fha.bwi50101.common.model.Entry> entryList) {
        List<Entry> entries = new LinkedList<>();
        for (de.fha.bwi50101.common.model.Entry e : entryList) {
            entries.add(new Entry(e.getDataCreatedAt().getTime(), e.getDiabetesDataOfType(DiabetesDataType.Glucose).getValue()));
        }
        return entries;

    }
}
