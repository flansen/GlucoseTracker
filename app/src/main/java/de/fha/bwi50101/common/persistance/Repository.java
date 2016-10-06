package de.fha.bwi50101.common.persistance;

import java.util.Date;
import java.util.List;

import de.fha.bwi50101.common.model.Entry;

/**
 * Created by Florian on 06.10.2016.
 */

public interface Repository {
    Entry save(Entry entry);

    void delete(Entry entry);

    Entry findById(long id);

    List<Entry> findNewerThan(Date date);

    Entry findWithHighestGlucoseSince(Date since);

    Entry findMostRecentWithGlucoseValue();
}
