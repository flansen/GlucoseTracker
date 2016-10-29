package de.fha.bwi50101.overview.home;

/**
 * Created by Florian on 28.10.2016.
 */

public interface SaveAlarmInteractor {
    void saveAlarm(LumindAlarm alarm);

    void setCallback(Callback callback);

    interface Callback {
        void onAlarmSaved(LumindAlarm alarm);

    }
}
