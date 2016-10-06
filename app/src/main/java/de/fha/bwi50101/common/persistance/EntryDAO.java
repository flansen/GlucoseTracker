package de.fha.bwi50101.common.persistance;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.Date;
import java.util.List;

/**
 * Created by Florian on 06.10.2016.
 */

public class EntryDAO extends SugarRecord {
    @Ignore
    private List<DiabetesDataDAO> diabetesData;
    private Date createdAt;
    private Date dataCreatedAt;
    private String note;

    public EntryDAO() {
    }

    public List<DiabetesDataDAO> getDiabetesData() {
        if (diabetesData == null)
            diabetesData = DiabetesDataDAO.find(DiabetesDataDAO.class, "entry = ?", Long.toString(getId()));
        return diabetesData;
    }

    public void setDiabetesData(List<DiabetesDataDAO> diabetesData) {
        this.diabetesData = diabetesData;
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
