package de.fha.bwi50101.common.impl;

import java.util.ArrayList;
import java.util.List;

import de.fha.bwi50101.common.Constants;
import de.fha.bwi50101.common.DAOConverter;
import de.fha.bwi50101.common.model.DiabetesData;
import de.fha.bwi50101.common.model.Entry;
import de.fha.bwi50101.common.persistance.DiabetesDataRecord;
import de.fha.bwi50101.common.persistance.EntryRecord;

/**
 * Created by Florian on 06.10.2016.
 */

public class DAOConverterImpl implements DAOConverter {

    @Override
    public Entry entryDAOtoEntry(EntryRecord entryRecord) {
        Entry entry = createEntryFromDAO(entryRecord);
        return entry;
    }

    private Entry createEntryFromDAO(EntryRecord entryRecord) {
        Entry entry = new Entry();
        entry.setDiabetesDataAndUpdateDate(createDiabetesDataListFromDAOList(entryRecord));
        entry.setDataCreatedAt(entryRecord.getDataCreatedAt());
        entry.setId(entryRecord.getId());
        entry.setCreatedAt(entryRecord.getCreatedAt());
        entry.setNote(entryRecord.getNote());
        return entry;
    }

    private List<DiabetesData> createDiabetesDataListFromDAOList(EntryRecord entryRecord) {
        List<DiabetesData> diabetesDataList = new ArrayList<>();
        for (DiabetesDataRecord ddDAO : entryRecord.getDiabetesData()) {
            DiabetesData d = new DiabetesData();
            d.setType(ddDAO.getType());
            d.setValue(ddDAO.getValue());
            d.setDate(ddDAO.getDate());
            d.setId(ddDAO.getId());
            diabetesDataList.add(d);
        }
        return diabetesDataList;
    }

    @Override
    public EntryRecord entryToEntryDAO(Entry entry) {
        EntryRecord entryRecord = createDAOFromEntry(entry);
        return entryRecord;
    }

    private EntryRecord createDAOFromEntry(Entry entry) {
        EntryRecord entryRecord = new EntryRecord();
        entryRecord.setCreatedAt(entry.getCreatedAt());
        entryRecord.setDataCreatedAt(entry.getDataCreatedAt());
        entryRecord.setId(entry.getId() == Constants.NO_ID ? null : entry.getId());
        entryRecord.setNote(entry.getNote());
        List<DiabetesDataRecord> diabetesDataRecordList = createDAOListFromDiabetesDataList(entry.getDiabetesData(), entryRecord);
        entryRecord.setDiabetesData(diabetesDataRecordList);
        return entryRecord;
    }

    private List<DiabetesDataRecord> createDAOListFromDiabetesDataList(List<DiabetesData> diabetesData, EntryRecord entryRecord) {
        List<DiabetesDataRecord> diabetesDataRecordList = new ArrayList<>();
        for (DiabetesData d : diabetesData) {
            DiabetesDataRecord dao = createDAOFromDiabetesData(d, entryRecord);
            diabetesDataRecordList.add(dao);
        }
        return diabetesDataRecordList;
    }

    private DiabetesDataRecord createDAOFromDiabetesData(DiabetesData d, EntryRecord entryRecord) {
        DiabetesDataRecord dao = new DiabetesDataRecord();
        dao.setId(d.getId() == Constants.NO_ID ? null : d.getId());
        dao.setValue(d.getValue());
        dao.setDate(d.getDataDate());
        dao.setEntryDAO(entryRecord);
        dao.setType(d.getType());
        return dao;
    }
}
