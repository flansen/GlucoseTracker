package de.fha.bwi50101.overview.statistic.impl;

import java.util.ArrayList;
import java.util.List;

import de.fha.bwi50101.common.DateConverter;
import de.fha.bwi50101.common.model.DiabetesData;
import de.fha.bwi50101.common.model.DiabetesDataType;
import de.fha.bwi50101.common.model.Entry;
import de.fha.bwi50101.overview.statistic.EntryToEntryVMConverter;
import de.fha.bwi50101.overview.statistic.EntryVM;

/**
 * Created by Florian on 10.10.2016.
 */

public class EntryToEntryVMConverterImpl implements EntryToEntryVMConverter {
    public static final String STRING_FORMAT = "%.0f";
    private DateConverter dateConverter;

    public EntryToEntryVMConverterImpl(DateConverter dateConverter) {
        this.dateConverter = dateConverter;
    }

    @Override
    public List<EntryVM> toEntryVMList(List<Entry> entryList) {
        List<EntryVM> entryVMs = new ArrayList<>();
        for (Entry entry : entryList) {
            entryVMs.add(convertEntryToVM(entry));
        }
        return entryVMs;
    }

    private EntryVM convertEntryToVM(Entry entry) {
        EntryVM entryVM = new EntryVM();
        if (entry.getDataCreatedAt() != null) {
            entryVM.setDateString(dateConverter.dateToOverviewDateString(entry.getDataCreatedAt()));
        }
        for (DiabetesData d : entry.getDiabetesData()) {
            if (d.getType() == DiabetesDataType.Glucose)
                entryVM.setGlucoseString(String.format(STRING_FORMAT, d.getValue()));
            else if (d.getType() == DiabetesDataType.Food)
                entryVM.setFoodString(String.format(STRING_FORMAT, d.getValue()));
            else if (d.getType() == DiabetesDataType.StandardInsulin)
                entryVM.setInsulinString(String.format(STRING_FORMAT, d.getValue()));
        }
        return entryVM;
    }
}
