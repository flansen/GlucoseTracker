package de.flansen.glucosetracker.common;

import android.graphics.Color;

/**
 * Created by Florian on 06.10.2016.
 */

public class Constants {
    public static final long NO_ID = -1;
    public static final String BUNDLE_ENTRY_ID_KEY = "entry";
    public static final String CREATE_EDIT_RESULT = "result_entry_id";

    public static final int GLUCOSE_UPPER_LIMIT_LOWER_BOUND = 100;
    public static final int GLUCOSE_UPPER_LIMIT_UPPER_BOUND = 300;
    public static final int GLUCOSE_LOWER_LIMIT_LOWER_BOUND = 50;
    public static final int GLUCOSE_LOWER_LIMIT_UPPER_BOUND = 150;

    public static final String SETTINGS_KEY_UPPER_BOUND = "upper_bound";
    public static final String SETTINGS_KEY_LOWER_BOUND = "lower_bound";
    public static final String SETTINGS_KEY_ALARM_INTERVAL = "settings_key_alarm_time";
    public static final String ACTION_ALARM_INTENT = "de.fha.bwi50101.alarm";

    public static final String ALARM_HOUR_KEY = "alarm_time_h";
    public static final String ALARM_MINUTES_KEY = "alarm_time_min";
    public static final String ALARM_ENABLED_KEY = "alarm_enabled";
    public static final String NOTIFICATION_TITLE = "Lumind Reminder";
    public static final String NOTIFICATION_TEXT = "You should measure your glucose again!";

    public static class COLORS {
        public static int GREY = Color.rgb(0, 0, 0);
        public static int FULL_OPAC = 255;
        public static int HALF_OPAC = 127;
        public static int QUARTER_OPAC = 64;
        public static int NO_OPAC = 0;
    }
}
