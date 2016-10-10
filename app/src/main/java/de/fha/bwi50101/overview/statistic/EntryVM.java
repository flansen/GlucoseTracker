package de.fha.bwi50101.overview.statistic;

/**
 * Created by Florian on 08.10.2016.
 */
public class EntryVM {
    private String dateString, glucoseString, foodString, insulinString;

    public EntryVM() {
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String getGlucoseString() {
        return glucoseString;
    }

    public void setGlucoseString(String glucoseString) {
        this.glucoseString = glucoseString;
    }

    public String getFoodString() {
        return foodString;
    }

    public void setFoodString(String foodString) {
        this.foodString = foodString;
    }

    public String getInsulinString() {
        return insulinString;
    }

    public void setInsulinString(String insulinString) {
        this.insulinString = insulinString;
    }
}
