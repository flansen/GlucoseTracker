package de.fha.bwi50101.graph;

import java.util.List;

import de.fha.bwi50101.common.model.Entry;

/**
 * Created by Florian on 26.10.2016.
 */

public interface FetchEntriesForGraphInteractor {
    interface Callback {
        void onEntriesFetched(List<Entry> entryList);
    }
}
