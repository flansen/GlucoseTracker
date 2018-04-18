package de.flansen.glucosetracker.overview.home;

/**
 * Created by Florian on 28.10.2016.
 */

public interface AlarmHandler {
    void setAlarm(long msUntilAlarm, boolean isEnabled);
}
