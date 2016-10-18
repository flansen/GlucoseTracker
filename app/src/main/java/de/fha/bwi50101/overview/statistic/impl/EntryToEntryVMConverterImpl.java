package de.fha.bwi50101.overview.statistic.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import de.fha.bwi50101.common.DateConverter;
import de.fha.bwi50101.common.model.DiabetesData;
import de.fha.bwi50101.common.model.DiabetesDataType;
import de.fha.bwi50101.common.model.Entry;
import de.fha.bwi50101.overview.statistic.EntryToEntryVMConverter;
import de.fha.bwi50101.overview.statistic.EntryVM;
import de.fha.bwi50101.overview.statistic.ListItem;
import de.fha.bwi50101.overview.statistic.SectionVM;

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
    public List<ListItem> toSectionedVMList(List<Entry> entryList) {
        Collections.sort(entryList, new Comparator<Entry>() {
            @Override
            public int compare(Entry o1, Entry o2) {
                if (o1.getDataCreatedAt() != null && o2.getDataCreatedAt() != null)
                    return o2.getDataCreatedAt().compareTo(o1.getDataCreatedAt());
                return o2.getCreatedAt().compareTo(o1.getCreatedAt());
            }
        });
        List<ListItem> entryVMs = new LinkedList<>();
        Date currentDate = null;
        for (Entry entry : entryList) {
            if (currentDate == null || !dateIsSameDay(currentDate, entry.getCreatedAt())) {
                currentDate = entry.getDataCreatedAt();
                addSectionVMForDate(currentDate, entryVMs);
            }
            entryVMs.add(convertEntryToVM(entry));
        }
        return entryVMs;
    }

    private void addSectionVMForDate(Date currentDate, List<ListItem> entryVMs) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
        String dateString = sdf.format(currentDate);
        SectionVM vm = new SectionVM();
        vm.setDateString(dateString);
        entryVMs.add(vm);
    }

    private boolean dateIsSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
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
        entryVM.setFoodUnit("BU");
        entryVM.setGlucoseUnit("mg/dl");
        entryVM.setInsulinUnit("IU");
        entryVM.setNoteString(entry.getNote());
        entryVM.setModelId(entry.getId());
        return entryVM;
    }
}
