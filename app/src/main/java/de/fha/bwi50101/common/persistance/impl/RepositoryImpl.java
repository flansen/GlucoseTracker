package de.fha.bwi50101.common.persistance.impl;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.fha.bwi50101.common.RecordConverter;
import de.fha.bwi50101.common.model.Entry;
import de.fha.bwi50101.common.persistance.DiabetesDataRecord;
import de.fha.bwi50101.common.persistance.EntryRecord;
import de.fha.bwi50101.common.persistance.Repository;

/**
 * Created by Florian on 06.10.2016.
 */
//DiabetesDataRecord ddr = DiabetesDataRecord.find(DiabetesDataRecord.class, "VALUE = (SELECT max(VALUE) FROM DIABETES_DATA_RECORD)", null).get(0);

public class RepositoryImpl implements Repository {
    private RecordConverter recordConverter;

    public RepositoryImpl(RecordConverter recordConverter) {
        this.recordConverter = recordConverter;
    }


    @Override
    public Entry save(Entry entry) {
        EntryRecord entryRecord = recordConverter.entryToEntryRecord(entry);
        entryRecord.save();
        for (DiabetesDataRecord diabetesDataRecord : entryRecord.getDiabetesData()) {
            diabetesDataRecord.save();
        }
        return recordConverter.entryRecordToEntry(entryRecord);
    }

    @Override
    public void delete(Entry entry) {
        EntryRecord entryRecord = recordConverter.entryToEntryRecord(entry);
        for (DiabetesDataRecord diabetesDataRecord : entryRecord.getDiabetesData())
            diabetesDataRecord.delete();
        entryRecord.delete();
    }

    @Override
    public Entry findById(long id) {
        EntryRecord record = EntryRecord.findById(EntryRecord.class, id);
        return recordConverter.entryRecordToEntry(record);
    }

    @Override
    public List<Entry> findNewerThan(Date date) {
        List<Entry> entries = new ArrayList<>();
        List<EntryRecord> entryRecords = Select.from(EntryRecord.class)
                .where(Condition.prop("DATA_CREATED_AT").gt(date.getTime()))
                .orderBy("DATA_CREATED_AT DESC")
                .list();
        for (EntryRecord r : entryRecords) {
            entries.add(recordConverter.entryRecordToEntry(r));
        }
        return entries;
    }

    @Override
    public Entry findMostRecentWithGlucoseValue() {
        DiabetesDataRecord ddr = Select.from(DiabetesDataRecord.class)
                .where(Condition.prop("DIABETES_DATA_TYPE").eq("Glucose"))
                .orderBy("DATA_CREATED_AT DESC")
                .first();
        if (ddr != null) {
            EntryRecord entryRecord = EntryRecord.findById(EntryRecord.class, ddr.getEntryRecord().getId());
            return recordConverter.entryRecordToEntry(entryRecord);
        }
        return null;
    }
}
