package de.fha.bwi50101.create_edit;

import de.flhn.cleanboilerplate.domain.interactors.base.Interactor;

/**
 * Created by Florian on 20.10.2016.
 */

public interface DeleteEntryInteractor extends Interactor {
    interface Callback {
        void onDeleted();
    }
}
