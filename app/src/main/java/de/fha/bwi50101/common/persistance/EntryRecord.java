package de.fha.bwi50101.common.persistance;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.Date;
import java.util.List;

/**
 * Created by Florian on 06.10.2016.
 */

public class EntryRecord extends SugarRecord {
    @Ignore
    private List<DiabetesDataRecord> diabetesData;
    private Date createdAt;
    private Date dataCreatedAt;
    private String note;

    public EntryRecord() {
    }

    public List<DiabetesDataRecord> getDiabetesData() {
        if (diabetesData == null)
            diabetesData = DiabetesDataRecord.find(DiabetesDataRecord.class, "ENTRY_RECORD = ?", Long.toString(getId()));
        return diabetesData;
    }

    public void setDiabetesData(List<DiabetesDataRecord> diabetesData) {
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
