package de.fha.bwi50101.overview.statistic;

/**
 * Created by Florian on 18.10.2016.
 */

public class SectionVM implements ListItem {
    private String dateString;

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    @Override
    public boolean isSection() {
        return true;
    }
}
