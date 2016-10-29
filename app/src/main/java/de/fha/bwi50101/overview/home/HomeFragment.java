package de.fha.bwi50101.overview.home;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.fha.bwi50101.R;
import de.fha.bwi50101.common.Constants;
import de.fha.bwi50101.common.impl.AppSettingsImpl;
import de.fha.bwi50101.common.persistance.impl.RepositoryImpl;
import de.fha.bwi50101.overview.OverviewActivity;
import de.fha.bwi50101.overview.home.impl.AlarmHandlerImpl;
import de.fha.bwi50101.overview.home.impl.HomeFragmentPresenterImpl;
import de.fha.bwi50101.overview.home.impl.SetAlarmInteractorImpl;
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
    @BindString(R.string.home_recent_value)
    String noValueString;
    @BindView(R.id.home_top_layout)
    RelativeLayout layout;
    @BindView(R.id.home_recent_value_unit)
    TextView unitText;
    @BindView(R.id.home_alarm_switch)
    Switch alarmSwitch;
    @BindView(R.id.home_alarm_time)
    TextView alarmTime;

    private BroadcastReceiver myBroadcastReceiver;
    private HomeFragmentPresenter presenter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        presenter = new HomeFragmentPresenterImpl(ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                RepositoryImpl.getInstance(),
                new AppSettingsImpl(PreferenceManager.getDefaultSharedPreferences(getContext())),
                new SetAlarmInteractorImpl(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), new AlarmHandlerImpl(getActivity().getApplicationContext(), alarmManager)));
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadAlarm();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        registerBroadcastReceiver();
        setOnClickListeners();
        presenter.setView(this);
        loadRecentEntry();
        presenter.loadAlarm();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(myBroadcastReceiver);
    }

    private void setOnClickListeners() {
        addEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((OverviewActivity) getActivity()).createEntryClicked();
            }
        });

        alarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                presenter.handleTimerStateChanged(isChecked);
            }
        });

        alarmTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
            }
        });
    }

    @Override
    public void displayMostRecentValue(String displayString, boolean shouldShowUnitLabel) {
        if (shouldShowUnitLabel)
            unitText.setVisibility(View.VISIBLE);
        else
            unitText.setVisibility(View.INVISIBLE);
        recentValueText.setText(displayString);
    }

    @Override
    public void onError() {
        recentValueText.setText(noValueString);
    }

    @Override
    public void displayAlarmData(String time, boolean enabled) {
        alarmTime.setText(time);
        alarmSwitch.setChecked(enabled);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void registerBroadcastReceiver() {
        myBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (presenter != null)
                    presenter.handleTimerStateChanged(false);
            }
        };
        getActivity().registerReceiver(myBroadcastReceiver, new IntentFilter(Constants.ACTION_ALARM_INTENT));
    }

    @Override
    public void updateAlarmSwitchState(boolean state) {
        alarmSwitch.setChecked(state);
    }


    public void loadRecentEntry() {
        presenter.fetchMostRecentGlucoseValue();
    }

    public void onTimeSet(int hourOfDay, int min) {
        presenter.handleTimerChanged(hourOfDay, min, true);
    }
}
