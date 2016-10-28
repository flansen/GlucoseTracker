package de.fha.bwi50101.overview.home;

import de.flhn.cleanboilerplate.domain.interactors.base.Interactor;

/**
 * Created by Florian on 28.10.2016.
 */

public interface SaveAlarmInteractor extends Interactor {
    void saveAlarm(LumindAlarm alarm);


    interface Callback {
        void onAlarmSaved(LumindAlarm alarm);

    }
}
