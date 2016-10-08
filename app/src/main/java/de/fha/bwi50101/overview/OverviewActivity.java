package de.fha.bwi50101.overview;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.fha.bwi50101.R;
import de.fha.bwi50101.overview.home.HomeFragment;
import de.fha.bwi50101.overview.impl.OverviewPresenterImpl;
import de.fha.bwi50101.overview.statistic.StatisticsFragment;


public class OverviewActivity extends AppCompatActivity implements OverviewPresenter.View, View.OnClickListener {
    @BindView(R.id.overview_toolbar)
    Toolbar toolbar;
    @BindString(R.string.action_bar_title_overview)
    String actionBarTitle;
    @BindView(R.id.fab_create)
    FloatingActionButton createActionButton;
    @BindView(R.id.overview_view_pager)
    ViewPager viewPager;
    @BindView(R.id.overview_tab_layout)
    TabLayout tabLayout;

    private Map<Class, Fragment> fragments;

    private OverviewPagerAdapter pagerAdapter;
    private OverviewPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(actionBarTitle);
        createActionButton.setOnClickListener(this);
        pagerAdapter = new OverviewPagerAdapter(getSupportFragmentManager(), createFragments());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        this.presenter = new OverviewPresenterImpl();
        presenter.resume();

    }

    private Fragment[] createFragments() {
        fragments = new HashMap<>();
        fragments.put(HomeFragment.class, HomeFragment.newInstance());
        fragments.put(StatisticsFragment.class, StatisticsFragment.newIntance());
        return fragments.values().toArray(new Fragment[0]);
    }


    @Override
    public void displayEntryVMs(List<EntryVM> routeVMs) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overview_menu, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v == createActionButton)
            presenter.onCreateClicked();
    }
}

