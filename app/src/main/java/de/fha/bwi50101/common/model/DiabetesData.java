package de.fha.bwi50101.common.model;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by Florian on 03.10.2016.
 */

public class DiabetesData extends SugarRecord {
    private DiabetesDataType diabetesDataType;
    private float value;
    private Entry entry;
    private Date dataDate;

    public DiabetesData() {
    }


    public DiabetesDataType getType() {
        return diabetesDataType;
    }

    public void setType(DiabetesDataType diabetesDataType) {
        this.diabetesDataType = diabetesDataType;
    }


    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public Date getDate() {
        return dataDate;
    }

    public void setDate(Date dataDate) {
        this.dataDate = dataDate;
    }

    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }
}
