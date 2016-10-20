package de.fha.bwi50101.overview.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.fha.bwi50101.R;
import de.fha.bwi50101.common.persistance.impl.RepositoryImpl;
import de.fha.bwi50101.overview.OverviewActivity;
import de.fha.bwi50101.overview.home.impl.HomeFragmentPresenterImpl;
import de.flhn.cleanboilerplate.MainThreadImpl;
import de.flhn.cleanboilerplate.domain.executor.impl.ThreadExecutor;

/**
 * Created by Florian on 08.10.2016.
 */

public class HomeFragment extends Fragment implements HomeFragmentPresenter.View {
    @BindView(R.id.home_recent_value)
    TextView recentValueText;
    @BindView(R.id.home_add_entry)
    Button addEntryButton;
    @BindView(R.id.home_alarm_list)
    ListView alarmList;
    @BindView(R.id.home_add_alarm)
    Button addAlarmButton;
    @BindString(R.string.home_recent_value)
    String noValueString;
    private HomeFragmentPresenter presenter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new HomeFragmentPresenterImpl(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), RepositoryImpl.getInstance());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        setOnClickListeners();
        presenter.setView(this);
        loadRecentEntry();
        return view;
    }

    private void setOnClickListeners() {
        addEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((OverviewActivity) getActivity()).createEntryClicked();
            }
        });

        addAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((OverviewActivity) getActivity()).createAlarmClicked();
            }
        });
    }

    @Override
    public void displayMostRecentValue(String displayString) {
        recentValueText.setText(displayString);
    }

    @Override
    public void onError() {
        recentValueText.setText(noValueString);
    }

    public void loadRecentEntry() {
        presenter.fetchMostRecentGlucoseValue();
    }
}
