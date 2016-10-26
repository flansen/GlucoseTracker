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

/**
 * Created by Florian on 26.10.2016.
 */

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {
    @BindString(R.string.settings_key_lower_bound)
    String lowerBoundKey;
    @BindString(R.string.settings_key_upper_bound)
    String upperBoundKey;
    private Preference lowerTargetValuePreference;
    private Preference upperTargetValuePreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        ButterKnife.bind(this, getActivity());
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        lowerTargetValuePreference = findPreference(getResources().getString(R.string.settings_key_lower_bound));
        upperTargetValuePreference = findPreference(getResources().getString(R.string.settings_key_upper_bound));
        registerPreferenceListeners();
    }

    private void registerPreferenceListeners() {
        lowerTargetValuePreference.setOnPreferenceClickListener(this);
        lowerTargetValuePreference.setOnPreferenceChangeListener(this);
        upperTargetValuePreference.setOnPreferenceClickListener(this);
        upperTargetValuePreference.setOnPreferenceChangeListener(this);
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
        int lowerValueLowerBound = Integer.parseInt(getActivity().getString(R.string.glucose_lower_limit_lower_bound));
        int lowerValueUpperBound = Integer.parseInt(getActivity().getString(R.string.glucose_lower_limit_upper_bound));
        int upperValueLowerBound = Integer.parseInt(getActivity().getString(R.string.glucose_upper_limit_lower_bound));
        int upperValueUpperBound = Integer.parseInt(getActivity().getString(R.string.glucose_upper_limit_upper_bound));
        EditTextPreference pref = (EditTextPreference) preference;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplication());
        switch (pref.getKey()) {
            case "upper_bound":
                String lowerValueStr = sharedPreferences.getString(lowerBoundKey, null);
                int lowerValue = Integer.valueOf(lowerValueStr);
                int newUpperVal = Integer.valueOf((String) newValue);
                if (newUpperVal > upperValueLowerBound || newUpperVal < upperValueUpperBound || newUpperVal < lowerValue) {
                    Toast.makeText(getActivity(), "Upper value must be between " + upperValueLowerBound + "  and " + upperValueUpperBound + " and greater than the lower bound.", Toast.LENGTH_LONG).show();
                    return false;
                } else {
                    sharedPreferences.edit().putString(upperBoundKey, Integer.toString(newUpperVal)).apply();
                    return true;
                }
            case "lower_bound":
                String upperValueStr = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(upperBoundKey, null);
                int upperValue = Integer.valueOf(upperValueStr);
                int newLowerVal = Integer.valueOf((String) newValue);
                if (newLowerVal < lowerValueLowerBound || newLowerVal > lowerValueUpperBound || newLowerVal > upperValue) {
                    Toast.makeText(getActivity(), "Lower value must be between " + lowerValueLowerBound + " and " + lowerValueUpperBound + " and lower than the lower value.", Toast.LENGTH_LONG).show();
                    return false;
                } else {
                    sharedPreferences.edit().putString(lowerBoundKey, Integer.toString(newLowerVal)).apply();
                    return true;
                }
        }
        return true;
    }
}
