package de.fha.bwi50101;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.fha.bwi50101.common.impl.RecordConverterImpl;
import de.fha.bwi50101.common.model.Entry;
import de.fha.bwi50101.common.persistance.impl.RepositoryImpl;


public class OverviewActivity extends AppCompatActivity {
    @BindView(R.id.overview_toolbar)
    Toolbar toolbar;
    @BindString(R.string.action_bar_title_overview)
    String actionBarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(actionBarTitle);
        Entry e = new Entry();
        new RepositoryImpl(new RecordConverterImpl()
        ).save(e);
    }
}

/*
        Entry e = new Entry();
        DiabetesData d1 = new DiabetesData();
        d1.setType(DiabetesDataType.Food);
        d1.setDate(new Date(2000));
        d1.setValue(100f);
        DiabetesData d2 = new DiabetesData();
        d2.setType(DiabetesDataType.Glucose);
        d2.setDate(new Date(1000));
        d2.setValue(10f);
        e.setDiabetesDataAndUpdateDate(Arrays.asList(d1, d2));

        Entry e2 = new Entry();
        DiabetesData d3 = new DiabetesData();
        d3.setType(DiabetesDataType.Glucose);
        d3.setDate(new Date());
        d3.setValue(1000f);
        e2.setDiabetesDataAndUpdateDate(Arrays.asList(d3));

        RepositoryImpl repository = new RepositoryImpl(new RecordConverterImpl());
        e = repository.save(e);
        e2 = repository.save(e2);
        Assert.assertNotSame(Constants.NO_ID, e.getId());
        Assert.assertNotSame(Constants.NO_ID, e.getDiabetesData().get(0).getId());
        Assert.assertNotSame(Constants.NO_ID, e.getDiabetesData().get(1).getId());

        Entry mr = repository.findMostRecentWithGlucoseValue();
        Assert.assertEquals(mr.getId(), e2.getId());
        e.setDiabetesDataAndUpdateDate(Arrays.asList(d1));
        e = repository.save(e);
        Entry newE1 = repository.findById(e.getId());
        Assert.assertEquals(1, newE1.getDiabetesData().size());

        int oldSize = repository.findAll().size();
        repository.delete(e);
        Assert.assertTrue(repository.findAll().size() == oldSize - 1);

        Assert.assertTrue(repository.findNewerThan(new Date()).size() == 0);
        Assert.assertTrue(repository.findNewerThan(new Date(1999)).size() < repository.findAll().size());
        */
