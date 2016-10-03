package de.flhn.cleanboilerplate.domain.executor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.flhn.cleanboilerplate.MainThreadImpl;
import de.flhn.cleanboilerplate.domain.executor.impl.ThreadExecutor;

/**
 * Created by Florian on 19.09.2016.
 */
@Module
public class ThreadExecutionModule {
    public ThreadExecutionModule() {
    }

    @Provides
    @Singleton
    MainThread providesMainThread() {
        return new MainThreadImpl();
    }

    @Provides
    @Singleton
    Executor providesThreadExecutor() {
        return new ThreadExecutor();
    }
}
