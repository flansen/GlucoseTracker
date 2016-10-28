package de.fha.bwi50101.overview.home.impl;

import de.fha.bwi50101.overview.home.AlarmHandler;
import de.fha.bwi50101.overview.home.SetAlarmInteractor;
import de.flhn.cleanboilerplate.domain.executor.Executor;
import de.flhn.cleanboilerplate.domain.executor.MainThread;
import de.flhn.cleanboilerplate.domain.interactors.base.AbstractInteractor;

/**
 * Created by Florian on 28.10.2016.
 */

public class SetAlarmInteractorImpl extends AbstractInteractor implements SetAlarmInteractor {

    private final AlarmHandler alarmHandler;


    public SetAlarmInteractorImpl(Executor threadExecutor, MainThread mainThread, AlarmHandler alarmHandler) {
        super(threadExecutor, mainThread);
        this.alarmHandler = alarmHandler;
    }

    @Override
    public void run() {
    }


    @Override
    public void setAlarm(long msUntilAlarm, boolean isEnabled) {
        alarmHandler.setAlarm(msUntilAlarm, isEnabled);
    }
}
