package de.fha.bwi50101.graph.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import de.fha.bwi50101.common.AppSettings;
import de.fha.bwi50101.common.Constants;
import de.fha.bwi50101.common.model.DiabetesData;
import de.fha.bwi50101.common.model.DiabetesDataType;
import de.fha.bwi50101.common.model.Entry;
import de.fha.bwi50101.graph.GraphPresenter;
import de.flhn.cleanboilerplate.domain.executor.Executor;
import de.flhn.cleanboilerplate.domain.executor.MainThread;
import de.flhn.cleanboilerplate.presentation.presenters.base.AbstractPresenter;

/**
 * Created by Florian on 26.10.2016.
 */

public class GraphPresenterImpl extends AbstractPresenter implements GraphPresenter {

    private AppSettings appSettings;
    private View view;

    public GraphPresenterImpl(Executor executor, MainThread mainThread, AppSettings appSettings, View view) {
        super(executor, mainThread);
        this.appSettings = appSettings;
        this.view = view;
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
        List<Entry> filteredList = mock();
        int upperValue = Integer.parseInt(appSettings.getString(Constants.SETTINGS_KEY_UPPER_BOUND));
        int lowerValue = Integer.parseInt(appSettings.getString(Constants.SETTINGS_KEY_LOWER_BOUND));

        boolean isValidForGraph = determineShouldShowGraph(filteredList);
        if (isValidForGraph)
            view.displayGraph(filteredList, upperValue, lowerValue);
    }

    private List<Entry> mock() {
        List<Entry> entries = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            Date d = new Date(new Date().getTime() - 1000 * 60 * 60 * 24 * (7 - i));
            Entry e = new Entry();
            DiabetesData dd = new DiabetesData(DiabetesDataType.Glucose, 200 * r.nextFloat(), i, d);
            e.setDiabetesDataAndUpdateDate(Arrays.asList(dd));
            entries.add(e);
        }
        return entries;
    }

    private boolean determineShouldShowGraph(List<Entry> filteredList) {
        if (filteredList.size() < 3) return false;
        Set<String> days = new HashSet<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Entry e : filteredList) {
            Date d = e.getDiabetesDataOfType(DiabetesDataType.Glucose).getDate();
            days.add(sdf.format(d));
        }
        return days.size() > 3;
    }
}
