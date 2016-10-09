package de.fha.bwi50101.overview.statistic;

import java.util.List;

import de.fha.bwi50101.common.model.Entry;

/**
 * Created by Florian on 09.10.2016.
 */

public interface FetchAllEntriesInteractor {
    interface Callback {
        void onEntriesLoaded(List<Entry> entries);
    }
}
