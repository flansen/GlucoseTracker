package de.fha.bwi50101.overview.statistic;

/**
 * Created by Florian on 08.10.2016.
 */
public class EntryVM implements ListItem {
    private String dateString, glucoseString, foodString = "0", insulinString = "0", noteString, insulinUnit, foodUnit, glucoseUnit;
    private long modelId;

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

    public String getNoteString() {
        return noteString;
    }

    public void setNoteString(String noteString) {
        this.noteString = noteString;
    }

    public String getInsulinUnit() {
        return insulinUnit;
    }

    public void setInsulinUnit(String insulinUnit) {
        this.insulinUnit = insulinUnit;
    }

    public String getFoodUnit() {
        return foodUnit;
    }

    public void setFoodUnit(String foodUnit) {
        this.foodUnit = foodUnit;
    }

    public String getGlucoseUnit() {
        return glucoseUnit;
    }

    public void setGlucoseUnit(String glucoseUnit) {
        this.glucoseUnit = glucoseUnit;
    }

    @Override
    public boolean isSection() {
        return false;
    }

    public long getModelId() {
        return modelId;
    }

    public void setModelId(long modelId) {
        this.modelId = modelId;
    }
}
