package de.flansen.glucosetracker.common;

import de.flansen.glucosetracker.common.model.Entry;
import de.flansen.glucosetracker.common.persistance.EntryRecord;

/**
 * Created by Florian on 06.10.2016.
 */

public interface RecordConverter {
    Entry entryRecordToEntry(EntryRecord entryRecord);

    EntryRecord entryToEntryRecord(Entry entry);
}
