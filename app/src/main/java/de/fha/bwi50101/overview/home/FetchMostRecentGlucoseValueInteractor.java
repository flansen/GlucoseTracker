package de.fha.bwi50101.overview.home;

import de.fha.bwi50101.common.model.Entry;

/**
 * Created by Florian on 20.10.2016.
 */

public interface FetchMostRecentGlucoseValueInteractor {
    interface Callback {
        void onEntry(Entry entry);

        void onError();
    }
}
