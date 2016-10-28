package de.fha.bwi50101.overview.home;

/**
 * Created by Florian on 28.10.2016.
 */

public class LumindAlarm {
    private final int hourOfDay;
    private final int minutes;
    private boolean isEnabled;

    public LumindAlarm(boolean isEnabled, int hourOfDay, int minutes) {
        this.hourOfDay = hourOfDay;
        this.minutes = minutes;
        this.isEnabled = isEnabled;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }


    public int getMinutes() {
        return minutes;
    }

    public int getHourOfDay() {
        return hourOfDay;
    }
}
