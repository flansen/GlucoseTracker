package de.flansen.glucosetracker.common.persistance;

import java.util.Date;
import java.util.List;

import de.flansen.glucosetracker.common.model.Entry;

/**
 * Created by Florian on 06.10.2016.
 */

public interface Repository {
    Entry save(Entry entry);

    void delete(Entry entry);

    Entry findById(long id);

    List<Entry> findNewerThan(Date date);

    Entry findMostRecentWithGlucoseValue();

    List<Entry> findAll();
}
