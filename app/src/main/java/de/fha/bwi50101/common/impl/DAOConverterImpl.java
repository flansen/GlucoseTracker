package de.fha.bwi50101.common.impl;

import java.util.ArrayList;
import java.util.List;

import de.fha.bwi50101.common.Constants;
import de.fha.bwi50101.common.DAOConverter;
import de.fha.bwi50101.common.model.DiabetesData;
import de.fha.bwi50101.common.model.Entry;
import de.fha.bwi50101.common.persistance.DiabetesDataDAO;
import de.fha.bwi50101.common.persistance.EntryDAO;

/**
 * Created by Florian on 06.10.2016.
 */

public class DAOConverterImpl implements DAOConverter {

    @Override
    public Entry entryDAOtoEntry(EntryDAO entryDAO) {
        Entry entry = createEntryFromDAO(entryDAO);
        return entry;
    }

    private Entry createEntryFromDAO(EntryDAO entryDAO) {
        Entry entry = new Entry();
        entry.setDiabetesDataAndUpdateDate(createDiabetesDataListFromDAOList(entryDAO));
        entry.setDataCreatedAt(entryDAO.getDataCreatedAt());
        entry.setId(entryDAO.getId());
        entry.setCreatedAt(entryDAO.getCreatedAt());
        entry.setNote(entryDAO.getNote());
        return entry;
    }

    private List<DiabetesData> createDiabetesDataListFromDAOList(EntryDAO entryDAO) {
        List<DiabetesData> diabetesDataList = new ArrayList<>();
        for (DiabetesDataDAO ddDAO : entryDAO.getDiabetesData()) {
            DiabetesData d = new DiabetesData();
            d.setType(ddDAO.getType());
            d.setValue(ddDAO.getValue());
            d.setDate(ddDAO.getDate());
            d.setId(ddDAO.getId());
            diabetesDataList.add(d);
        }
        return diabetesDataList;
    }

    @Override
    public EntryDAO entryToEntryDAO(Entry entry) {
        EntryDAO entryDAO = createDAOFromEntry(entry);
        return entryDAO;
    }

    private EntryDAO createDAOFromEntry(Entry entry) {
        EntryDAO entryDAO = new EntryDAO();
        entryDAO.setCreatedAt(entry.getCreatedAt());
        entryDAO.setDataCreatedAt(entry.getDataCreatedAt());
        entryDAO.setId(entry.getId() == Constants.NO_ID ? null : entry.getId());
        entryDAO.setNote(entry.getNote());
        List<DiabetesDataDAO> diabetesDataDAOList = createDAOListFromDiabetesDataList(entry.getDiabetesData(), entryDAO);
        entryDAO.setDiabetesData(diabetesDataDAOList);
        return entryDAO;
    }

    private List<DiabetesDataDAO> createDAOListFromDiabetesDataList(List<DiabetesData> diabetesData, EntryDAO entryDAO) {
        List<DiabetesDataDAO> diabetesDataDAOList = new ArrayList<>();
        for (DiabetesData d : diabetesData) {
            DiabetesDataDAO dao = createDAOFromDiabetesData(d, entryDAO);
            diabetesDataDAOList.add(dao);
        }
        return diabetesDataDAOList;
    }

    private DiabetesDataDAO createDAOFromDiabetesData(DiabetesData d, EntryDAO entryDAO) {
        DiabetesDataDAO dao = new DiabetesDataDAO();
        dao.setId(d.getId() == Constants.NO_ID ? null : d.getId());
        dao.setValue(d.getValue());
        dao.setDate(d.getDataDate());
        dao.setEntryDAO(entryDAO);
        dao.setType(d.getType());
        return dao;
    }
}
