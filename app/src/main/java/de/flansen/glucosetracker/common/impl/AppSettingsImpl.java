package de.flansen.glucosetracker.common.impl;

import android.content.SharedPreferences;

import de.flansen.glucosetracker.common.AppSettings;

/**
 * Created by Florian on 26.10.2016.
 */

public class AppSettingsImpl implements AppSettings {
    public static final long NO_LONG_VALUE = Long.MIN_VALUE;
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
        sharedPreferences.edit().putString(key, value).apply();
    }

    @Override
    public void putLong(String key, long value) {
        sharedPreferences.edit().putLong(key, value).apply();
    }

    @Override
    public long getLong(String key) {
        return sharedPreferences.getLong(key, NO_LONG_VALUE);
    }

    @Override
    public void putBoolean(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    @Override
    public void putInt(String key, int value) {
        sharedPreferences.edit().putInt(key, value).apply();
    }

    @Override
    public int getInt(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }


}
