package de.fha.bwi50101.create_edit;

import de.fha.bwi50101.common.model.Entry;

/**
 * Created by Florian on 15.10.2016.
 */

public interface FetchEntryForIdInteractor {
    interface Callback {
        void entryFound(Entry entry);
    }
}
