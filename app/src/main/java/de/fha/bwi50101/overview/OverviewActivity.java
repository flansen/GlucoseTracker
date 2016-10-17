package de.fha.bwi50101.overview;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.fha.bwi50101.R;
import de.fha.bwi50101.common.Constants;
import de.fha.bwi50101.create_edit.CreateEditActivity;
import de.fha.bwi50101.overview.home.HomeFragment;
import de.fha.bwi50101.overview.impl.OverviewPresenterImpl;
import de.fha.bwi50101.overview.statistic.EntryVM;
import de.fha.bwi50101.overview.statistic.StatisticsFragment;


public class OverviewActivity extends AppCompatActivity implements OverviewPresenter.View, View.OnClickListener {
    private static final int CREATE_EDIT_ENTRY_CODE = 1256;
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
        createActionButton.setOnClickListener(this);
        pagerAdapter = new OverviewPagerAdapter(getSupportFragmentManager(), createFragments());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        presenter = new OverviewPresenterImpl();
        presenter.resume();
    }

    private Fragment[] createFragments() {
        fragments = new LinkedHashMap<>();
        fragments.put(HomeFragment.class, HomeFragment.newInstance());
        fragments.put(StatisticsFragment.class, StatisticsFragment.newInstance());
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
        if (v == createActionButton) {
            presenter.onCreateClicked();
            Intent intent = new Intent(this, CreateEditActivity.class);
            startActivityForResult(intent, CREATE_EDIT_ENTRY_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CREATE_EDIT_ENTRY_CODE) {
            if (resultCode == RESULT_OK) {
                long id = data.getLongExtra(Constants.CREATE_EDIT_RESULT, -1);
                Toast.makeText(this, "Successfully saved Entry with id < " + id + ">", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void setPresenter(OverviewPresenter presenter) {
        this.presenter = presenter;
    }
}

