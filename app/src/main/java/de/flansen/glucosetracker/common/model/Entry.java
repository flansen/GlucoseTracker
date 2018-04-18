package de.flansen.glucosetracker.common.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.flansen.glucosetracker.common.Constants;

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

    public void updateDataCreatedAt() {
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

    public boolean hasDiabetesDataOfType(DiabetesDataType type) {
        for (DiabetesData d : diabetesData) {
            if (d.getType() == type) return true;
        }
        return false;
    }

    public DiabetesData getDiabetesDataOfType(DiabetesDataType type) {
        for (DiabetesData d : diabetesData) {
            if (d.getType() == type) return d;
        }
        throw new NoSuchDiabetesDataException(type);
    }

    public void addOrReplaceDiabetesData(DiabetesData diabetesData) {
        if (hasDiabetesDataOfType(diabetesData.getType())) {
            replaceDiabetesData(diabetesData);
        } else {
            this.diabetesData.add(diabetesData);
            updateDataCreatedAt();
        }
    }

    private void replaceDiabetesData(DiabetesData diabetesData) {
        for (int i = 0; i < this.diabetesData.size(); i++) {
            if (this.diabetesData.get(i).getType() != diabetesData.getType())
                continue;
            this.diabetesData.set(i, diabetesData);
        }
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
