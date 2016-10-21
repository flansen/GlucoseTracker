package de.fha.bwi50101.overview.statistic;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.fha.bwi50101.R;
import de.fha.bwi50101.common.Constants;
import de.fha.bwi50101.common.impl.DateConverterImpl;
import de.fha.bwi50101.common.persistance.impl.RepositoryImpl;
import de.fha.bwi50101.create_edit.CreateEditActivity;
import de.fha.bwi50101.overview.OverviewActivity;
import de.fha.bwi50101.overview.statistic.impl.EntryToEntryVMConverterImpl;
import de.fha.bwi50101.overview.statistic.impl.FetchAllEntriesInteractorImpl;
import de.fha.bwi50101.overview.statistic.impl.StatisticsFragmentPresenterImpl;
import de.flhn.cleanboilerplate.MainThreadImpl;
import de.flhn.cleanboilerplate.domain.executor.impl.ThreadExecutor;

/**
 * Created by Florian on 08.10.2016.
 */

public class StatisticsFragment extends Fragment implements StatisticsFragmentPresenter.View {
    @BindView(R.id.statistics_list)
    ListView listView;
    @BindString(R.string.statistics_list_empty)
    String listEmptyString;
    //@BindView(R.id.statistics_empty_list)
    //TextView emptyListView;
    private StatisticsFragmentPresenter presenter;
    private ProgressDialog progressDialog;
    private StatisticsListAdapter listAdapter;

    public static StatisticsFragment newInstance() {
        return new StatisticsFragment();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_statistic, menu);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        presenter = new StatisticsFragmentPresenterImpl(this, new EntryToEntryVMConverterImpl(new DateConverterImpl()));
        presenter.setInteractor(new FetchAllEntriesInteractorImpl(
                MainThreadImpl.getInstance(),
                ThreadExecutor.getInstance(),
                RepositoryImpl.getInstance(),
                (FetchAllEntriesInteractor.Callback) presenter)
        );
        presenter.loadEntries();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        ButterKnife.bind(this, view);
        // listView.setEmptyView(emptyListView);
        createListAndAdapter();
        createListClickListener();
        return view;
    }

    private void createListClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long entryId = ((EntryVM) listAdapter.getItem(position)).getModelId();
                Intent startEditIntent = new Intent(StatisticsFragment.this.getContext(), CreateEditActivity.class);
                startEditIntent.putExtra(Constants.BUNDLE_ENTRY_ID_KEY, entryId);
                getActivity().startActivityForResult(startEditIntent, OverviewActivity.CREATE_EDIT_ENTRY_CODE);
            }
        });
    }


    private void createListAndAdapter() {
        listAdapter = new StatisticsListAdapter(this.getContext(), new ArrayList<ListItem>());
        listView.setAdapter(listAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.resume();
    }

    @Override
    public void showLoading() {
        if (progressDialog == null)
            progressDialog = ProgressDialog.show(this.getContext(), "Loading Entries", "Please wait a second...", true, false);
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null) {
            progressDialog.cancel();
            progressDialog = null;
        }
    }


    @Override
    public void onEntriesLoaded(final List<ListItem> entryVMs) {
        //listAdapter = new StatisticsListAdapter(this.getContext(), entryVMs);
        //listView.setAdapter(listAdapter);
        listAdapter.clear();
        // listView.invalidateViews();
        //listView.invalidate();
        listAdapter.addAll(entryVMs);
        listAdapter.notifyDataSetChanged();
        //listView.invalidateViews();
        //listView.invalidate();

    }

    @Override
    public void reloadList() {
        presenter.loadEntries();
    }

    static class StatisticsViewHolderItem {
        @BindView(R.id.stat_item_food_text)
        TextView foodText;
        @BindView(R.id.stat_item_glucose_text)
        TextView glucoseText;
        @BindView(R.id.stat_item_insulin_text)
        TextView insulinText;
        @BindView(R.id.stat_item_note_text)
        TextView noteText;

        private StatisticsViewHolderItem(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class StatisticsViewDividerItem {
        @BindView(R.id.stat_divider_date_text)
        TextView dateText;

        private StatisticsViewDividerItem(View v) {
            ButterKnife.bind(this, v);
        }
    }

    private class StatisticsListAdapter extends ArrayAdapter<ListItem> {
        private LayoutInflater layoutInflater;

        public StatisticsListAdapter(Context context, List<ListItem> objects) {
            super(context, 0, objects);
            layoutInflater = LayoutInflater.from(getContext());
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v;
            ListItem item = getItem(position);
            if (item == null) {
                throw new RuntimeException("Item in Adapter was null.");
            }
            if (item.isSection()) {
                v = inflateSectionItemView((SectionVM) item);
            } else {
                v = inflateEntryItemView((EntryVM) item, convertView);
            }
            return v;
        }

        private View inflateSectionItemView(SectionVM sectionVM) {
            View v;
            v = layoutInflater.inflate(R.layout.fragment_statistics_list_divider, null);
            StatisticsViewDividerItem holder = new StatisticsViewDividerItem(v);
            holder.dateText.setText(sectionVM.getDateString());
            return v;
        }

        private View inflateEntryItemView(EntryVM vm, View convertView) {
            StatisticsViewHolderItem holder;
            if (convertView != null && convertView.getTag() != null && convertView.getTag() instanceof StatisticsViewHolderItem) {
                holder = (StatisticsViewHolderItem) convertView.getTag();
            } else {
                convertView = layoutInflater.inflate(R.layout.fragment_statistics_list_entry_item, null);
                holder = new StatisticsViewHolderItem(convertView);
                convertView.setTag(holder);
            }
            if (vm.getNoteString() == null || vm.getNoteString().isEmpty()) {
                holder.noteText.setVisibility(View.GONE);
            } else {
                holder.noteText.setText(vm.getNoteString());
            }
            holder.foodText.setText(vm.getFoodString());
            holder.glucoseText.setText(vm.getGlucoseString());
            holder.insulinText.setText(vm.getInsulinString());
            return convertView;
        }


        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public boolean isEnabled(int position) {
            return getItem(position).isSection() == false;
        }

        @Override
        public int getItemViewType(int position) {
            return getItem(position).isSection() == true ? 1 : 0;
        }
    }
}
