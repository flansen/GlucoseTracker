package de.fha.bwi50101.common.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.fha.bwi50101.common.DateConverter;

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
