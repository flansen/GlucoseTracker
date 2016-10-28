package de.fha.bwi50101.overview.home;

import de.flhn.cleanboilerplate.domain.interactors.base.Interactor;

/**
 * Created by Florian on 28.10.2016.
 */

public interface SetAlarmInteractor extends Interactor {

    void setAlarm(long msUntilAlarm, boolean isEnabled);
}
