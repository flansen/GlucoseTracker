package de.fha.bwi50101.create_edit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import de.fha.bwi50101.R;

public class CreateEditActivity extends AppCompatActivity implements ViewPagerHolder {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit);
    }

    @Override
    public void bringViewPagerToFront() {

    }

    @Override
    public void bringViewPagerToBackground() {

    }
}
