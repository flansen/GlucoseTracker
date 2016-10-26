package de.fha.bwi50101.graph;

import java.util.List;

import de.flhn.cleanboilerplate.presentation.presenters.base.BasePresenter;

/**
 * Created by Florian on 26.10.2016.
 */

public interface GraphPresenter extends BasePresenter {
    void viewCreated();

    interface View {
        void displayGraph(List<com.github.mikephil.charting.data.Entry> entryList, int lowerBound, int upperBound);
    }
}
