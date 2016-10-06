package de.fha.bwi50101;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;


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
    }
}
