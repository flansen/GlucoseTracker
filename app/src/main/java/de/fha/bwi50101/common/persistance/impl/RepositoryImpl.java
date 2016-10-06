package de.fha.bwi50101.common.persistance.impl;

import java.util.Date;
import java.util.List;

import de.fha.bwi50101.common.DAOConverter;
import de.fha.bwi50101.common.model.Entry;
import de.fha.bwi50101.common.persistance.EntryDAO;
import de.fha.bwi50101.common.persistance.Repository;

/**
 * Created by Florian on 06.10.2016.
 */

public class RepositoryImpl implements Repository {
    private DAOConverter daoConverter;

    public RepositoryImpl(DAOConverter daoConverter) {
        this.daoConverter = daoConverter;
    }


    @Override
    public Entry save(Entry entry) {
        EntryDAO entryDAO = daoConverter.entryToEntryDAO(entry);

        return null;
    }

    @Override
    public void delete(Entry entry) {

    }

    @Override
    public Entry findById(long id) {
        return null;
    }

    @Override
    public List<Entry> findNewerThan(Date date) {
        return null;
    }

    @Override
    public Entry findWithHighestGlucoseSince(Date since) {
        return null;
    }

    @Override
    public Entry findMostRecentWithGlucoseValue() {
        return null;
    }
}
