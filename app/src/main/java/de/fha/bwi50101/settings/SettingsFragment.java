package de.fha.bwi50101.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceGroup;
import android.preference.PreferenceManager;
import android.widget.Toast;

import butterknife.BindString;
import butterknife.ButterKnife;
import de.fha.bwi50101.R;
import de.fha.bwi50101.common.impl.AppSettingsImpl;
import de.fha.bwi50101.settings.impl.SettingsPresenterImpl;
import de.flhn.cleanboilerplate.MainThreadImpl;
import de.flhn.cleanboilerplate.domain.executor.impl.ThreadExecutor;

/**
 * Created by Florian on 26.10.2016.
 */

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener, SettingsPresenter.View {
    @BindString(R.string.settings_key_lower_bound)
    String lowerBoundKey;
    @BindString(R.string.settings_key_upper_bound)
    String upperBoundKey;
    private Preference lowerTargetValuePreference;
    private Preference upperTargetValuePreference;
    private Preference alarmIntervalPreference;
    private SettingsPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        ButterKnife.bind(this, getActivity());
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        lowerTargetValuePreference = findPreference(getResources().getString(R.string.settings_key_lower_bound));
        upperTargetValuePreference = findPreference(getResources().getString(R.string.settings_key_upper_bound));
        alarmIntervalPreference = findPreference(getResources().getString(R.string.settings_key_alarm_time));
        registerPreferenceListeners();
        this.presenter = new SettingsPresenterImpl(ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                new AppSettingsImpl(PreferenceManager.getDefaultSharedPreferences(getActivity())),
                this);
    }

    private void registerPreferenceListeners() {
        lowerTargetValuePreference.setOnPreferenceClickListener(this);
        lowerTargetValuePreference.setOnPreferenceChangeListener(this);
        upperTargetValuePreference.setOnPreferenceClickListener(this);
        upperTargetValuePreference.setOnPreferenceChangeListener(this);
        alarmIntervalPreference.setOnPreferenceClickListener(this);
        alarmIntervalPreference.setOnPreferenceChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        for (int i = 0; i < getPreferenceScreen().getPreferenceCount(); ++i) {
            Preference preference = getPreferenceScreen().getPreference(i);
            if (preference instanceof PreferenceGroup) {
                PreferenceGroup preferenceGroup = (PreferenceGroup) preference;
                for (int j = 0; j < preferenceGroup.getPreferenceCount(); ++j) {
                    Preference singlePref = preferenceGroup.getPreference(j);
                    updatePreference(singlePref, singlePref.getKey());
                }
            } else {
                updatePreference(preference, preference.getKey());
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        updatePreference(findPreference(key), key);
    }

    private void updatePreference(Preference preference, String key) {
        if (preference == null) return;
        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            listPreference.setSummary(listPreference.getEntry());
        }
        SharedPreferences sharedPrefs = getPreferenceManager().getSharedPreferences();
        preference.setSummary(sharedPrefs.getString(key, ""));
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        EditTextPreference editPref = (EditTextPreference) preference;
        editPref.getEditText().setSelection(editPref.getText().length());
        return true;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == null)
            return false;
        EditTextPreference pref = (EditTextPreference) preference;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplication());
        switch (pref.getKey()) {
            case "upper_bound":
                return presenter.onUpperBoundChanged(Integer.valueOf((String) newValue));
            case "lower_bound":
                return presenter.onLowerBoundChange(Integer.valueOf((String) newValue));
            case "settings_alarm_time":
                return presenter.onAlarmIntervalChanged(Integer.valueOf((String) newValue));
        }
        return true;
    }

    @Override
    public void showMessage(String m) {
        Toast.makeText(getActivity(), m, Toast.LENGTH_LONG).show();
    }
}
