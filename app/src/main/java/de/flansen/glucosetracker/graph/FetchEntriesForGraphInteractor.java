package de.flansen.glucosetracker.graph;

import java.util.List;

import de.flansen.glucosetracker.common.model.Entry;

/**
 * Created by Florian on 26.10.2016.
 */

public interface FetchEntriesForGraphInteractor {
    interface Callback {
        void onEntriesFetched(List<Entry> entryList);
    }
}
