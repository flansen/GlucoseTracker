package de.fha.bwi50101;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OverviewActivity extends AppCompatActivity {
    @BindView(R.id.test)
    Button test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        ButterKnife.bind(this);
        new TestInteractor().execute();
    }
}
