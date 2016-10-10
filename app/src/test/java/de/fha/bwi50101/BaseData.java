package de.fha.bwi50101;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import de.fha.bwi50101.common.model.DiabetesData;
import de.fha.bwi50101.common.model.DiabetesDataType;
import de.fha.bwi50101.common.model.Entry;

/**
 * Created by Florian on 10.10.2016.
 */

public class BaseData {

    public List<Entry> mockEntryList() {
        Entry e1 = new Entry(mockDiabetesData(), new Date(), new Date(), "A note", 5);
        Entry e2 = new Entry(mockDiabetesData(), new Date(), new Date(), "Another note", 6);
        return Arrays.asList(e1, e2);
    }

    private List<DiabetesData> mockDiabetesData() {
        DiabetesData d1 = new DiabetesData(DiabetesDataType.Food, new Random().nextFloat() * 250f, 1, new Date());
        DiabetesData d2 = new DiabetesData(DiabetesDataType.Glucose, new Random().nextFloat() * 250f, 2, new Date());
        DiabetesData d3 = new DiabetesData(DiabetesDataType.StandardInsulin, new Random().nextFloat() * 250f, 2, new Date());
        return Arrays.asList(d1, d2, d3);
    }
}
