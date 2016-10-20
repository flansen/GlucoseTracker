package de.fha.bwi50101.overview.home;

import de.fha.bwi50101.common.model.Entry;
import de.flhn.cleanboilerplate.domain.interactors.base.Interactor;

/**
 * Created by Florian on 20.10.2016.
 */

public interface FetchMostRecentGlucoseValueInteractor extends Interactor {
    interface Callback {
        void onEntry(Entry entry);

        void onError();
    }
}
