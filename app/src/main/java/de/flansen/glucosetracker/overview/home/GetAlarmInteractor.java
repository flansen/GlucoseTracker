package de.flansen.glucosetracker.overview.home;

/**
 * Created by Florian on 28.10.2016.
 */

public interface GetAlarmInteractor {
    interface Callback {
        void onAlarmFetched(LumindAlarm alarm);
    }
}
