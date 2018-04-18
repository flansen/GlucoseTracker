package de.flansen.glucosetracker.overview.statistic;

import java.util.List;

import de.flansen.glucosetracker.common.model.Entry;

/**
 * Created by Florian on 09.10.2016.
 */

public interface FetchAllEntriesInteractor {
    interface Callback {
        void onEntriesLoaded(List<Entry> entries);
    }

}
