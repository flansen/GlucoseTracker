package de.fha.bwi50101.graph;

import java.util.List;

import de.fha.bwi50101.common.model.Entry;
import de.flhn.cleanboilerplate.domain.interactors.base.Interactor;

/**
 * Created by Florian on 26.10.2016.
 */

public interface FetchEntriesForGraphInteractor extends Interactor {
    interface Callback {
        void onEntriesFetched(List<Entry> entryList);
    }
}
