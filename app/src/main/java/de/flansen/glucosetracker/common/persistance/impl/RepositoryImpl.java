package de.flansen.glucosetracker.common.persistance.impl;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.flansen.glucosetracker.common.Constants;
import de.flansen.glucosetracker.common.RecordConverter;
import de.flansen.glucosetracker.common.impl.RecordConverterImpl;
import de.flansen.glucosetracker.common.model.Entry;
import de.flansen.glucosetracker.common.persistance.DiabetesDataRecord;
import de.flansen.glucosetracker.common.persistance.EntryRecord;
import de.flansen.glucosetracker.common.persistance.Repository;

/**
 * Created by Florian on 06.10.2016.
 */
public class RepositoryImpl implements Repository {
    private static volatile Repository instance;
    private RecordConverter recordConverter;

    private RepositoryImpl(RecordConverter recordConverter) {
        this.recordConverter = recordConverter;
    }

    public static Repository getInstance() {
        if (instance == null) {
            instance = new RepositoryImpl(new RecordConverterImpl());
        }
        return instance;
    }

    @Override
    public Entry save(Entry entry) {
        EntryRecord entryRecord = recordConverter.entryToEntryRecord(entry);
        entryRecord.save();
        if (entry.getId() != Constants.NO_ID)
            DiabetesDataRecord.deleteAll(DiabetesDataRecord.class, "ENTRY_RECORD = ?", Long.toString(entry.getId()));

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
        List<EntryRecord> entryRecords = Select.from(EntryRecord.class)
                .where(Condition.prop("DATA_CREATED_AT").gt(date.getTime()))
                .orderBy("DATA_CREATED_AT DESC")
                .list();
        return convertRecordListToEntryList(entryRecords);
    }

    @Override
    public Entry findMostRecentWithGlucoseValue() {
        DiabetesDataRecord ddr = Select.from(DiabetesDataRecord.class)
                .where(Condition.prop("DIABETES_DATA_TYPE").eq("Glucose"))
                .orderBy("DATA_DATE DESC")
                .first();
        if (ddr != null) {
            EntryRecord entryRecord = EntryRecord.findById(EntryRecord.class, ddr.getEntryRecord().getId());
            return recordConverter.entryRecordToEntry(entryRecord);
        }
        return null;
    }

    @Override
    public List<Entry> findAll() {
        List<EntryRecord> entryRecords = Select.from(EntryRecord.class)
                .orderBy("DATA_CREATED_AT DESC")
                .list();
        return convertRecordListToEntryList(entryRecords);
    }

    private List<Entry> convertRecordListToEntryList(List<EntryRecord> records) {
        List<Entry> entries = new ArrayList<>();
        for (EntryRecord record : records) {
            entries.add(recordConverter.entryRecordToEntry(record));
        }
        return entries;
    }
}
