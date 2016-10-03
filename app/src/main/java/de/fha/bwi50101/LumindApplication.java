package de.fha.bwi50101;

import com.facebook.stetho.Stetho;
import com.orm.SugarApp;
import com.orm.SugarContext;

/**
 * Created by Florian on 03.10.2016.
 */

public class LumindApplication extends SugarApp {
    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());
    }

    @Override
    public void onTerminate() {
        SugarContext.terminate();
        super.onTerminate();
    }
}
