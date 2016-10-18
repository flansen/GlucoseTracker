package de.fha.bwi50101.overview.statistic;

import java.util.List;

import de.flhn.cleanboilerplate.presentation.presenters.base.BasePresenter;

/**
 * Created by Florian on 08.10.2016.
 */

public interface StatisticsFragmentPresenter extends BasePresenter {
    void loadEntries();

    void setInteractor(FetchAllEntriesInteractor interactor);

    interface View {
        void showLoading();

        void hideLoading();

        void onEntriesLoaded(List<ListItem> entryVM);
    }
}
