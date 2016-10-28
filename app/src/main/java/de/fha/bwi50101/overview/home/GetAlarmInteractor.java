package de.fha.bwi50101.overview.home;

import de.flhn.cleanboilerplate.domain.interactors.base.Interactor;

/**
 * Created by Florian on 28.10.2016.
 */

public interface GetAlarmInteractor extends Interactor {
    interface Callback {
        void onAlarmFetched(LumindAlarm alarm);
    }
}
