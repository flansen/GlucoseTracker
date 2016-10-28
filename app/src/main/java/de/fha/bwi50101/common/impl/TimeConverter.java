package de.fha.bwi50101.common.impl;

import java.util.Calendar;

/**
 * Created by Florian on 28.10.2016.
 */

public class TimeConverter {
    public static long calculateMsUntil(int hoursOfDay, int minutes) {
        Calendar c = Calendar.getInstance();
        if (c.get(Calendar.HOUR_OF_DAY) > hoursOfDay || (c.get(Calendar.HOUR_OF_DAY) == hoursOfDay && c.get(Calendar.MINUTE) > minutes))
            c.add(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, hoursOfDay);
        c.set(Calendar.MINUTE, minutes);
        return c.getTimeInMillis() - System.currentTimeMillis();
    }
}
