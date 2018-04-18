package de.flansen.glucosetracker.overview.home;

import de.flansen.glucosetracker.common.model.Entry;

/**
 * Created by Florian on 20.10.2016.
 */

public interface FetchMostRecentGlucoseValueInteractor {
    interface Callback {
        void onEntry(Entry entry);

        void onError();
    }
}
