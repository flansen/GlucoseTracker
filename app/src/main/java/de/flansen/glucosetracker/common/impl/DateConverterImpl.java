package de.flansen.glucosetracker.common.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.flansen.glucosetracker.common.DateConverter;

/**
 * Created by Florian on 10.10.2016.
 */

public class DateConverterImpl implements DateConverter {
    private static final String overviewFormat = "dd/MM";


    @Override
    public String dateToOverviewDateString(Date date) {
        return new SimpleDateFormat(overviewFormat, Locale.getDefault()).format(date);
    }
}
