package de.fha.bwi50101.create_edit;

import de.fha.bwi50101.common.model.Entry;
import de.flhn.cleanboilerplate.presentation.presenters.base.BasePresenter;

/**
 * Created by Florian on 09.10.2016.
 */

public interface CreateEditEntryPresenter extends BasePresenter {
    void createNewEntry();

    void loadEntryForId(long id);

    Entry getEntry();

    interface View {
        void displayLoading();

        void finishLoading();

        void createTabs();
    }
}