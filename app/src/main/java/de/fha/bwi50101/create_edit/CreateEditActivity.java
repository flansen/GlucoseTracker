package de.fha.bwi50101.create_edit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.fha.bwi50101.R;
import de.fha.bwi50101.common.Constants;
import de.fha.bwi50101.common.model.Entry;
import de.fha.bwi50101.common.persistance.impl.RepositoryImpl;
import de.fha.bwi50101.create_edit.food.FoodFragment;
import de.fha.bwi50101.create_edit.glucose.GlucoseFragment;
import de.fha.bwi50101.create_edit.impl.CreateEditEntryPresenterImpl;
import de.fha.bwi50101.create_edit.insulin.InsulinFragment;
import de.fha.bwi50101.create_edit.note.NoteFragment;
import de.flhn.cleanboilerplate.MainThreadImpl;
import de.flhn.cleanboilerplate.domain.executor.impl.ThreadExecutor;

public class CreateEditActivity extends AppCompatActivity implements DisableableViewPagerHolder, EntryProvider, CreateEditEntryPresenter.View {
    @BindView(R.id.create_edit_tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.create_edit_toolbar)
    Toolbar toolbar;
    @BindView(R.id.create_edit_view_pager)
    DisableableViewPager viewPager;
    private CreateEditEntryPresenter presenter;
    private Map<Class<? extends Fragment>, Fragment> fragments;
    private CreateEditPagerAdapter pagerAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit);
        ButterKnife.bind(this);
        presenter = new CreateEditEntryPresenterImpl(
                MainThreadImpl.getInstance(),
                ThreadExecutor.getInstance(),
                RepositoryImpl.getInstance(),
                this
        );
        createOrRecreateEntry(getIntent());
    }

    private void createOrRecreateEntry(Intent intent) {
        if (intent != null && intent.getExtras() != null && intent.getExtras().containsKey(Constants.BUNDLE_ENTRY_ID_KEY)) {
            long id = intent.getLongExtra(Constants.BUNDLE_ENTRY_ID_KEY, -1);
            presenter.loadEntryForId(id);
        } else {
            presenter.createNewEntry();
        }
    }

    private Fragment[] createFragments() {
        fragments = new LinkedHashMap<>();
        fragments.put(GlucoseFragment.class, GlucoseFragment.newInstance());
        fragments.put(InsulinFragment.class, InsulinFragment.newInstance());
        fragments.put(FoodFragment.class, FoodFragment.newInstance());
        fragments.put(NoteFragment.class, NoteFragment.newInstance());
        return fragments.values().toArray(new Fragment[0]);
    }

    @Override
    public void bringViewPagerToFront() {
        viewPager.bringToFront();
    }

    @Override
    public void bringViewPagerToBackground() {
        tabLayout.bringToFront();
        toolbar.bringToFront();
    }

    @Override
    public void enableViewPaging() {
        viewPager.setViewPagingEnabled(true);
    }

    @Override
    public void disableViewPaging() {
        viewPager.setViewPagingEnabled(false);
    }

    @Override
    public Entry getEntry() {
        return presenter.getEntry();
    }

    @Override
    public void displayLoading() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog.show(this, "Loading data...",
                    "Loading. Please wait...", true);
        }
    }

    @Override
    public void finishLoading() {
        if (progressDialog != null) {
            progressDialog.hide();
            progressDialog = null;
        }
    }

    @Override
    public void createTabs() {
        pagerAdapter = new CreateEditPagerAdapter(getSupportFragmentManager(), createFragments());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
