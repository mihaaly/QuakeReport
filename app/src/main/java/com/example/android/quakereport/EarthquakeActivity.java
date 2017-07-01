/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

//import android.os.AsyncTask;

public class EarthquakeActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<ArrayList<Earthquake>> {

    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int EARTHQUAKE_LOADER_ID = 1;

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    /**
     * Sample JSON response for a USGS query
     */
    private static final String SAMPLE_JSON_RESPONSE =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=3&limit=100";

    private EarthquakeAdapter mAdapter;
    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        earthquakeListView.setEmptyView(mEmptyStateTextView);

        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(mAdapter);

        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();
        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        Log.i(LOG_TAG, "TEST: initLoader");
        loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);


        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // find the current earthquake that was clicked on
                Earthquake currentEarthquake = mAdapter.getItem(position); // earthquakes.get(position) also worked

                // Convert the String URL into URI object (to pass into the Intent constructor)
                Uri earthquakeUri = Uri.parse(currentEarthquake.getWeb());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // send the intent to launch a new activity
                startActivity(websiteIntent);

//                // This one also worked
//                String webUrl = currentEarthquake.getWeb();
//                startActivity(new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(webUrl)));
            }
        });

//        NetTask task = new NetTask();
//
//        task.execute(SAMPLE_JSON_RESPONSE);


    }

    @Override
    public Loader<ArrayList<Earthquake>> onCreateLoader(int i, Bundle bundle) {
        Log.i(LOG_TAG, "TEST: onCreateLoader");
        return new EarthquakeLoader(EarthquakeActivity.this, SAMPLE_JSON_RESPONSE);

    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Earthquake>> loader, ArrayList<Earthquake> earthquakes) {
        Log.i(LOG_TAG, "TEST: onLoadFinished");
        // Set empty state text to display "No earthquakes found."
        mEmptyStateTextView.setText(R.string.no_earthquakes);
        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (earthquakes != null && !earthquakes.isEmpty()) {
            mAdapter.addAll(earthquakes);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Earthquake>> loader) {
        Log.i(LOG_TAG, "TEST: onLoaderReset");
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }


//    private void updateUI(ArrayList<Earthquake> earthquakes) {
//
////        // Create a new {@link ArrayAdapter} of earthquakes
////        // (Note that we also had to add the “final” modifier on the EarthquakeAdapter
////        // local variable, so that we could access the adapter variable within the OnItemClickListener.)
////        final EarthquakeAdapter adapter = new EarthquakeAdapter(this, earthquakes);
//    }

//        private class NetTask extends AsyncTask<String, Void, ArrayList<Earthquake>> {
//
//            @Override
//            protected ArrayList<Earthquake> doInBackground(String... urls) {
//                if (urls.length < 1 || urls[0] == null) {
//                    return null;
//                }
//                // Create a fake list of earthquake locations.
//                ArrayList<Earthquake> earthquakes = QueryUtils.fetchData(urls[0]);
//                return earthquakes;
//            }
//
//            @Override
//            protected void onPostExecute(ArrayList<Earthquake> earthquakes) {
//                // Clear the adapter of previous earthquake data
//                mAdapter.clear();
//
//                // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
//                // data set. This will trigger the ListView to update.
//                if (earthquakes != null && !earthquakes.isEmpty()) {
//                    mAdapter.addAll(earthquakes);
//                }
//
//            }
//        }
    }


