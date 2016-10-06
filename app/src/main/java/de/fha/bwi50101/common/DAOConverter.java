package de.fha.bwi50101.common;

import de.fha.bwi50101.common.model.Entry;
import de.fha.bwi50101.common.persistance.EntryDAO;

/**
 * Created by Florian on 06.10.2016.
 */

public interface DAOConverter {
    Entry entryDAOtoEntry(EntryDAO entryDAO);

    EntryDAO entryToEntryDAO(Entry entry);
}
