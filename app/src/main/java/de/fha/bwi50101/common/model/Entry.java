package de.fha.bwi50101.common.model;

import com.orm.SugarRecord;

import java.util.Date;
import java.util.List;

/**
 * Created by Florian on 03.10.2016.
 */
public class Entry extends SugarRecord {
    private List<DiabetesData> diabetesData;
    private Date createdAt;
    private Date dataCreatedAt;
    private String note;

    public Entry() {
    }

    public List<DiabetesData> getDiabetesData() {
        if (diabetesData == null)
            diabetesData = DiabetesData.find(DiabetesData.class, "entry = ?", Long.toString(getId()));
        return diabetesData;
    }

    public void setDiabetesData(List<DiabetesData> diabetesData) {
        this.diabetesData = diabetesData;
        updateCreatedAt();
    }

    private void updateCreatedAt() {
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
        dataCreatedAt = glucoseDate != null ? glucoseDate : foodDate != null ? foodDate : insulinDate;
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
}
