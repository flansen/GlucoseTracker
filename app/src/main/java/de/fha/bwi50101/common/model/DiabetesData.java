package de.fha.bwi50101.common.model;

import com.orm.dsl.Ignore;

import java.util.Date;

import de.fha.bwi50101.common.Constants;

/**
 * Created by Florian on 03.10.2016.
 */

public class DiabetesData {
    private DiabetesDataType diabetesDataType;
    private float value;
    private long id;
    private Date dataDate;
    @Ignore
    private boolean isActive;

    public DiabetesData() {
        diabetesDataType = DiabetesDataType.NotAssigned;
        id = Constants.NO_ID;
        isActive = false;
    }

    public DiabetesData(DiabetesDataType diabetesDataType, float value, long id, Date dataDate) {
        this.diabetesDataType = diabetesDataType;
        this.value = value;
        this.id = id;
        this.dataDate = dataDate;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
