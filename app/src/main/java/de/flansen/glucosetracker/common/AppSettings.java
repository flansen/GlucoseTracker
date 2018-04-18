package de.flansen.glucosetracker.common;

/**
 * Created by Florian on 26.10.2016.
 */

public interface AppSettings {
    String getString(String key);

    void putString(String key, String value);

    void putLong(String key, long value);

    long getLong(String key);

    void putBoolean(String key, boolean value);

    boolean getBoolean(String key, boolean defaultValue);


    void putInt(String key, int value);

    int getInt(String key, int defaultValue);

}
