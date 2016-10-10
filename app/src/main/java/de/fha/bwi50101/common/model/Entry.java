package de.fha.bwi50101.common.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.fha.bwi50101.common.Constants;

/**
 * Created by Florian on 03.10.2016.
 */
public class Entry {
    private List<DiabetesData> diabetesData;
    private Date createdAt;
    private Date dataCreatedAt;
    private String note;
    private long id;

    public Entry() {
        diabetesData = new ArrayList<>();
        id = Constants.NO_ID;
    }

    public Entry(List<DiabetesData> diabetesData, Date createdAt, Date dataCreatedAt, String note, long id) {
        this.diabetesData = diabetesData;
        this.createdAt = createdAt;
        this.dataCreatedAt = dataCreatedAt;
        this.note = note;
        this.id = id;
    }

    public List<DiabetesData> getDiabetesData() {
        return diabetesData;
    }

    public void setDiabetesDataAndUpdateDate(List<DiabetesData> diabetesData) {
        this.diabetesData = diabetesData;
        updateDataCreatedAt();
    }

    private void updateDataCreatedAt() {
        dataCreatedAt = findNewestDiabetesDataDate();
    }

    private Date findNewestDiabetesDataDate() {
        Date glucoseDate = null, foodDate = null, insulinDate = null;
        for (DiabetesData d : diabetesData) {
            if (d.getType() == DiabetesDataType.Glucose) {
                glucoseDate = d.getDate();
            } else if (d.getType() == DiabetesDataType.Food) {
                foodDate = d.getDate();
            } else {
                insulinDate = d.getDate();
            }
        }
        return dataCreatedAt = glucoseDate != null ? glucoseDate : foodDate != null ? foodDate : insulinDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getDataCreatedAt() {
        return dataCreatedAt;
    }

    public void setDataCreatedAt(Date dataCreatedAt) {
        this.dataCreatedAt = dataCreatedAt;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
