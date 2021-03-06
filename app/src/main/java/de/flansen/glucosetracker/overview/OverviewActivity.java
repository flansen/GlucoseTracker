package de.flansen.glucosetracker.overview;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.flansen.glucosetracker.R;
import de.flansen.glucosetracker.create_edit.CreateEditActivity;
import de.flansen.glucosetracker.overview.home.HomeFragment;
import de.flansen.glucosetracker.overview.impl.OverviewPresenterImpl;
import de.flansen.glucosetracker.overview.statistic.StatisticsFragment;
import de.flansen.glucosetracker.settings.SettingsActivity;


public class OverviewActivity extends AppCompatActivity implements OverviewPresenter.View, TimeSetListener {
    public static final int CREATE_EDIT_ENTRY_CODE = 1256;
    @BindView(R.id.overview_toolbar)
    Toolbar toolbar;
    @BindString(R.string.action_bar_title_overview)
    String actionBarTitle;
    @BindView(R.id.overview_view_pager)
    ViewPager viewPager;
    @BindView(R.id.overview_tab_layout)
    TabLayout tabLayout;

    private Map<Class<? extends Fragment>, Fragment> fragments;

    private OverviewPagerAdapter pagerAdapter;
    private OverviewPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(actionBarTitle);
        pagerAdapter = new OverviewPagerAdapter(getSupportFragmentManager(), createFragments());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        presenter = new OverviewPresenterImpl();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent startIntent = new Intent(this, SettingsActivity.class);
            startActivity(startIntent);
            return true;
        }
        return false;
    }

    private Fragment[] createFragments() {
        fragments = new LinkedHashMap<>();
        fragments.put(HomeFragment.class, HomeFragment.newInstance());
        fragments.put(StatisticsFragment.class, StatisticsFragment.newInstance());
        return fragments.values().toArray(new Fragment[0]);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.overview_menu, menu);
        return true;
    }

    public void createEntryClicked() {
        Intent intent = new Intent(this, CreateEditActivity.class);
        startActivityForResult(intent, CREATE_EDIT_ENTRY_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CREATE_EDIT_ENTRY_CODE) {
            if (resultCode == RESULT_OK) {
                ((StatisticsFragment) fragments.get(StatisticsFragment.class)).reloadList();
                ((HomeFragment) fragments.get(HomeFragment.class)).loadRecentEntry();
            }
        }
    }

    public void setPresenter(OverviewPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onTimeSet(int hourOfDay, int min) {
        Fragment f = fragments.get(HomeFragment.class);
        if (f != null) {
            ((HomeFragment) f).onTimeSet(hourOfDay, min);
        }
    }

    public Map<Class<? extends Fragment>, Fragment> getFragments() {
        return fragments;
    }
}

