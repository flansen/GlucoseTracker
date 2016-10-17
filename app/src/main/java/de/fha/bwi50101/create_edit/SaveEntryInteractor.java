package de.fha.bwi50101.create_edit;

import de.fha.bwi50101.common.model.Entry;
import de.flhn.cleanboilerplate.domain.interactors.base.Interactor;

/**
 * Created by Florian on 17.10.2016.
 */

public interface SaveEntryInteractor extends Interactor {
    interface Callback {
        void entrySaved(Entry entry);
    }
}
