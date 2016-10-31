package de.fha.bwi50101;

import android.app.Application;
import android.preference.PreferenceManager;

import com.orm.SugarContext;

/**
 * Created by Florian on 03.10.2016.
 */

public class LumindApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }

    @Override
    public void onTerminate() {
        SugarContext.terminate();
        super.onTerminate();
    }
}
