package de.fha.bwi50101.overview.home;

import de.flhn.cleanboilerplate.presentation.presenters.base.BasePresenter;

/**
 * Created by Florian on 08.10.2016.
 */

public interface HomeFragmentPresenter extends BasePresenter {
    void setView(HomeFragmentPresenter.View view);

    interface View {
    }
}
