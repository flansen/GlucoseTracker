package de.fha.bwi50101.common.persistance;

import com.orm.SugarRecord;

import java.util.Date;

import de.fha.bwi50101.common.model.DiabetesDataType;

/**
 * Created by Florian on 06.10.2016.
 */

public class DiabetesDataRecord extends SugarRecord {
    private DiabetesDataType diabetesDataType;
    private float value;
    private EntryRecord entry;
    private Date dataDate;

    public DiabetesDataRecord() {
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

    public EntryRecord getEntry() {
        return entry;
    }

    public void setEntryDAO(EntryRecord entry) {
        this.entry = entry;
    }
}
