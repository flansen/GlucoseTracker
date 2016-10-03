package de.flhn.cleanboilerplate.domain.executor;

import javax.inject.Singleton;

import dagger.Component;
import de.flhn.cleanboilerplate.domain.interactors.base.AbstractInteractor;

/**
 * Created by Florian on 19.09.2016.
 */
@Component(modules = {ThreadExecutionModule.class})
@Singleton
public interface ThreadExecutionComponent {
    void inject(AbstractInteractor interactor);
}
