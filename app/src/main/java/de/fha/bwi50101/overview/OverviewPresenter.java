package de.fha.bwi50101.overview;

import java.util.List;

import de.flhn.cleanboilerplate.presentation.presenters.base.BasePresenter;

/**
 * Created by Florian on 08.10.2016.
 */

public interface OverviewPresenter extends BasePresenter {
    void onCreateClicked();

    interface View {
        void displayEntryVMs(List<EntryVM> routeVMs);
    }

}
