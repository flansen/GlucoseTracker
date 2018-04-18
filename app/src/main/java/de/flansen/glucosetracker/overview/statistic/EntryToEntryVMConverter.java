package de.flansen.glucosetracker.overview.statistic;

import java.util.List;

import de.flansen.glucosetracker.common.model.Entry;

/**
 * Created by Florian on 10.10.2016.
 */

public interface EntryToEntryVMConverter {
    List<ListItem> toSectionedVMList(List<Entry> entryList);
}
