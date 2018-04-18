package de.flansen.glucosetracker.overview.home.impl;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import de.flansen.glucosetracker.common.AppSettings;
import de.flansen.glucosetracker.common.impl.TimeConverter;
import de.flansen.glucosetracker.common.model.DiabetesData;
import de.flansen.glucosetracker.common.model.DiabetesDataType;
import de.flansen.glucosetracker.common.model.Entry;
import de.flansen.glucosetracker.common.persistance.Repository;
import de.flansen.glucosetracker.overview.home.FetchMostRecentGlucoseValueInteractor;
import de.flansen.glucosetracker.overview.home.GetAlarmInteractor;
import de.flansen.glucosetracker.overview.home.HomeFragmentPresenter;
import de.flansen.glucosetracker.overview.home.LumindAlarm;
import de.flansen.glucosetracker.overview.home.SaveAlarmInteractor;
import de.flansen.glucosetracker.overview.home.SetAlarmInteractor;
import de.flhn.cleanboilerplate.domain.executor.Executor;
import de.flhn.cleanboilerplate.domain.executor.MainThread;

/**
 * Created by Florian on 08.10.2016.
 */

public class HomeFragmentPresenterImpl implements HomeFragmentPresenter, FetchMostRecentGlucoseValueInteractor.Callback, GetAlarmInteractor.Callback, SaveAlarmInteractor.Callback {
    private final Executor executor;
    private final MainThread mainThread;
    private final Repository repository;
    private AppSettings appSettings;
    private SetAlarmInteractor setAlarmManagerInteractor;
    private View view;
    private LumindAlarm alarm;

    public HomeFragmentPresenterImpl(Executor executor, MainThread mainThread, Repository repository, AppSettings appSettings, SetAlarmInteractor setAlarmManagerInteractor) {
        this.executor = executor;
        this.mainThread = mainThread;
        this.repository = repository;
        this.appSettings = appSettings;
        this.setAlarmManagerInteractor = setAlarmManagerInteractor;
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
    public void handleTimerChanged(int hourOfDay, int min, boolean isEnabled) {
        SaveAlarmInteractor interactor = new SaveAlarmInteractorImpl(executor, mainThread, appSettings, setAlarmManagerInteractor);
        interactor.setCallback(this);
        LumindAlarm alarm = new LumindAlarm(isEnabled, hourOfDay, min);
        interactor.saveAlarm(alarm);
    }

    @Override
    public void handleTimerStateChanged(boolean isEnabled) {
        if (isEnabled == alarm.isEnabled())
            return;
        handleTimerChanged(alarm.getHourOfDay(), alarm.getMinutes(), isEnabled);
    }

    @Override
    public void loadAlarm() {
        new GetAlarmInteractorImpl(executor, mainThread, this, appSettings).execute();
    }

    @Override
    public void onEntry(Entry entry) {
        if (entry == null) {
            view.displayMostRecentValue("no data", false);
            return;
        }
        DiabetesData dd = entry.getDiabetesDataOfType(DiabetesDataType.Glucose);
        String displayString = String.format(Locale.GERMANY, "%.0f", dd.getValue());
        view.displayMostRecentValue(displayString, true);
    }

    @Override
    public void onError() {
        view.onError();
    }

    @Override
    public void onAlarmFetched(LumindAlarm alarm) {
        this.alarm = alarm;
        String displayString = makeDisplayStringForAlarmTime(alarm);
        view.displayAlarmData(displayString, alarm.isEnabled());
    }

    private String makeDisplayStringForAlarmTime(LumindAlarm alarm) {
        return String.format(Locale.GERMANY, "%02d:%02d", alarm.getHourOfDay(), alarm.getMinutes());
    }

    @Override
    public void onAlarmSaved(LumindAlarm alarm) {
        this.alarm = alarm;
        view.displayAlarmData(makeDisplayStringForAlarmTime(alarm), alarm.isEnabled());
        if (false == alarm.isEnabled()) {
            return;
        }
        String timeString = makeTimeDisplayString(alarm.getHourOfDay(), alarm.getMinutes());
        view.showMessage("Alarm is going to fire in " + timeString + " h.");
    }

    private String makeTimeDisplayString(int hoursOfDay, int minutes) {
        long timeUntilAlarm = TimeConverter.calculateMsUntil(hoursOfDay, minutes);
        return convertMillisecondsToHHMMString(timeUntilAlarm);
    }


    private String convertMillisecondsToHHMMString(long timeUntilAlarm) {
        return String.format(Locale.GERMANY, "%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(timeUntilAlarm),
                TimeUnit.MILLISECONDS.toMinutes(timeUntilAlarm) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeUntilAlarm)));
    }
}
