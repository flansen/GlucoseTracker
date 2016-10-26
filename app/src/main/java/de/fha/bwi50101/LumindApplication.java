package de.fha.bwi50101;

import android.app.Application;
import android.preference.PreferenceManager;

import com.facebook.stetho.Stetho;
import com.orm.SugarContext;

/**
 * Created by Florian on 03.10.2016.
 */

public class LumindApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

    }

    @Override
    public void onTerminate() {
        SugarContext.terminate();
        super.onTerminate();
    }
}
