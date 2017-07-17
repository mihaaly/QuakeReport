package com.example.android.quakereport;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
            import android.preference.PreferenceManager;
            import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Let's add our SettingsActivity, and to launch it, we'll add a menu to EarthquakeActivity!
 */

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }

    public static class EarthquakePreferenceFragment extends PreferenceFragment implements
            Preference.OnPreferenceChangeListener{

        /**
         *  in the SettingsActivity, within the EarthquakePreferenceFragment inner class,
         *  override the onCreate() method to use the settings_main XML resource that we defined earlier.
         */
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            //However, we still need to update the preference summary when the settings activity is
            // launched. Given the key of a preference, we can use PreferenceFragment's
            // findPreference() method to get the Preference object, and setup the preference using
            // a helper method called bindPreferenceSummaryToValue().

            // get the Preference object for minimum magnitude
            Preference minMagnitude = findPreference(getString(R.string.settings_min_magnitude_key));
            // custom helper method to set the current EarhtquakePreferenceFragment instance as the
            // listener on each preference.
            // get minMagnitude preference's value and display it in preference summary
            bindPreferenceSummaryToValue(minMagnitude);

            // get the Preference object for order by
            Preference orderBy = findPreference(getString(R.string.settings_order_by_key));
            // get orderby preference's value and display it in preference summary
            bindPreferenceSummaryToValue(orderBy);
        }

        // sets summery to hte Preference object based on its String value
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            // Since this is the first ListPreference that the EarthquakePreferenceFragment is
            // encountering, update the onPreferenceChange() method in EarthquakePreferenceFragment
            // to properly update the summary of a ListPreference (using the label, instead of the key).
            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    CharSequence[] labels = listPreference.getEntries();
                    preference.setSummary(labels[prefIndex]);
                }
            } else {
                preference.setSummary(stringValue);
            }
            return true;
        }

        /**
         * Now we need to define the bindPreferenceSummaryToValue() helper method to set the current
         * EarhtquakePreferenceFragment instance as the listener on each preference. We also read
         * the current value of the preference stored in the SharedPreferences on the device, and
         * display that in the preference summary (so that the user can see the current value of
         * the preference).
         * @param preference
         */
        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(this);

            // get the preferences' values to update Preference Summary
            // cf. EarthquakeActivity onCreateLoader()...
            SharedPreferences preferences =
                    PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = preferences.getString(preference.getKey(), "");

            onPreferenceChange(preference, preferenceString);
        }
    }
}
