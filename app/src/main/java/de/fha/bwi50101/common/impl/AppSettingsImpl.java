package de.fha.bwi50101.common.impl;

import android.content.SharedPreferences;

import de.fha.bwi50101.common.AppSettings;

/**
 * Created by Florian on 26.10.2016.
 */

public class AppSettingsImpl implements AppSettings {
    private SharedPreferences sharedPreferences;

    public AppSettingsImpl(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }


    @Override
    public String getString(String key) {
        return sharedPreferences.getString(key, null);
    }

    @Override
    public void putString(String key, String value) {
        sharedPreferences.edit().putString(key, value).commit();
    }
}
