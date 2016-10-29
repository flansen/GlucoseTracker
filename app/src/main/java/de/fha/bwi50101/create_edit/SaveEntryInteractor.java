package de.fha.bwi50101.create_edit;

import de.fha.bwi50101.common.model.Entry;

/**
 * Created by Florian on 17.10.2016.
 */

public interface SaveEntryInteractor {
    interface Callback {
        void entrySaved(Entry entry);
    }
}
