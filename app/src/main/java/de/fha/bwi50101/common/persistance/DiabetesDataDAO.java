package de.fha.bwi50101.common.persistance;

import com.orm.SugarRecord;

import java.util.Date;

import de.fha.bwi50101.common.model.DiabetesDataType;

/**
 * Created by Florian on 06.10.2016.
 */

public class DiabetesDataDAO extends SugarRecord {
    private DiabetesDataType diabetesDataType;
    private float value;
    private EntryDAO entry;
    private Date dataDate;

    public DiabetesDataDAO() {
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

    public EntryDAO getEntry() {
        return entry;
    }

    public void setEntryDAO(EntryDAO entry) {
        this.entry = entry;
    }
}
