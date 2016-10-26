package de.fha.bwi50101.common;

/**
 * Created by Florian on 26.10.2016.
 */

public interface AppSettings {
    String getString(String key);

    void putString(String key, String value);
}
