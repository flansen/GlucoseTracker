package de.flansen.glucosetracker.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.flansen.glucosetracker.R;

/**
 * Created by Florian on 26.10.2016.
 */

public class SettingsActivity extends AppCompatActivity {
    @BindView(R.id.settings_toolbar)
    Toolbar toolbar;

    @BindString(R.string.settings_toolbar_title)
    String toolbarTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        getFragmentManager().beginTransaction()
                .replace(R.id.settings_content, new SettingsFragment())
                .commit();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(toolbarTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }
}
