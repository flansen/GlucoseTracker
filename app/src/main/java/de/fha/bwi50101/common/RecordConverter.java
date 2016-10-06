package de.fha.bwi50101.common;

import de.fha.bwi50101.common.model.Entry;
import de.fha.bwi50101.common.persistance.EntryRecord;

/**
 * Created by Florian on 06.10.2016.
 */

public interface RecordConverter {
    Entry entryRecordToEntry(EntryRecord entryRecord);

    EntryRecord entryToEntryRecord(Entry entry);
}
