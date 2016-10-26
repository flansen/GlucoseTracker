package de.fha.bwi50101.graph;

import java.util.List;

import de.fha.bwi50101.common.model.Entry;
import de.flhn.cleanboilerplate.presentation.presenters.base.BasePresenter;

/**
 * Created by Florian on 26.10.2016.
 */

public interface GraphPresenter extends BasePresenter {
    void viewCreated();

    interface View {
        void displayGraph(List<Entry> entryList, int lowerBound, int upperBound);
    }
}
