package de.flansen.glucosetracker.common.impl;

import java.util.ArrayList;
import java.util.List;

import de.flansen.glucosetracker.common.Constants;
import de.flansen.glucosetracker.common.RecordConverter;
import de.flansen.glucosetracker.common.model.DiabetesData;
import de.flansen.glucosetracker.common.model.Entry;
import de.flansen.glucosetracker.common.persistance.DiabetesDataRecord;
import de.flansen.glucosetracker.common.persistance.EntryRecord;

/**
 * Created by Florian on 06.10.2016.
 */

public class RecordConverterImpl implements RecordConverter {

    @Override
    public Entry entryRecordToEntry(EntryRecord entryRecord) {
        Entry entry = createEntryFromRecord(entryRecord);
        return entry;
    }

    private Entry createEntryFromRecord(EntryRecord entryRecord) {
        Entry entry = new Entry();
        entry.setDiabetesDataAndUpdateDate(createDiabetesDataListFromRecordList(entryRecord));
        entry.setDataCreatedAt(entryRecord.getDataCreatedAt());
        entry.setId(entryRecord.getId());
        entry.setCreatedAt(entryRecord.getCreatedAt());
        entry.setNote(entryRecord.getNote());
        return entry;
    }

    private List<DiabetesData> createDiabetesDataListFromRecordList(EntryRecord entryRecord) {
        List<DiabetesData> diabetesDataList = new ArrayList<>();
        for (DiabetesDataRecord record : entryRecord.getDiabetesData()) {
            DiabetesData d = new DiabetesData();
            d.setType(record.getType());
            d.setValue(record.getValue());
            d.setDate(record.getDate());
            d.setId(record.getId());
            d.setActive(true);
            diabetesDataList.add(d);
        }
        return diabetesDataList;
    }

    @Override
    public EntryRecord entryToEntryRecord(Entry entry) {
        EntryRecord entryRecord = createRecordFromEntry(entry);
        return entryRecord;
    }

    private EntryRecord createRecordFromEntry(Entry entry) {
        EntryRecord entryRecord = new EntryRecord();
        entryRecord.setCreatedAt(entry.getCreatedAt());
        entryRecord.setDataCreatedAt(entry.getDataCreatedAt());
        entryRecord.setId(entry.getId() == Constants.NO_ID ? null : entry.getId());
        entryRecord.setNote(entry.getNote());
        List<DiabetesDataRecord> diabetesDataRecordList = createRecordsFromDiabetesData(entry.getDiabetesData(), entryRecord);
        entryRecord.setDiabetesData(diabetesDataRecordList);
        return entryRecord;
    }

    private List<DiabetesDataRecord> createRecordsFromDiabetesData(List<DiabetesData> diabetesData, EntryRecord entryRecord) {
        List<DiabetesDataRecord> diabetesDataRecordList = new ArrayList<>();
        for (DiabetesData d : diabetesData) {
            if (d.isActive()) {
                DiabetesDataRecord r = createRecordFromDiabetesData(d, entryRecord);
                diabetesDataRecordList.add(r);
            }
        }
        return diabetesDataRecordList;
    }

    private DiabetesDataRecord createRecordFromDiabetesData(DiabetesData d, EntryRecord entryRecord) {
        DiabetesDataRecord r = new DiabetesDataRecord();
        r.setId(d.getId() == Constants.NO_ID ? null : d.getId());
        r.setValue(d.getValue());
        r.setDate(d.getDate());
        r.setEntryRecord(entryRecord);
        r.setType(d.getType());
        return r;
    }
}
