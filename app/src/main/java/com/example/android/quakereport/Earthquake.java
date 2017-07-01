package com.example.android.quakereport;

/**
 * Object holding earthquake data
 */

public class Earthquake {

    // magnitude of the earthquake
    private double mMagnitude;

    // location of the earthquake
    private String mLocation;

    // date of the earthquake in milliseconds (UNIX time)
    private long mDate;

    // website uri of the earthquake
    private String mWeb;

    // constructor of the class
    public Earthquake (double magnitude, String location, long date, String web){
        mMagnitude = magnitude;
        mLocation = location;
        mDate = date;
        mWeb = web;
    }

    /**
     * Gets the magnitude of the earthquake.
     * @return current value of mMagnitude.
     */
    public double getMagnitude(){
        return mMagnitude;
    }

    /**
     * Gets the location of the earthquake.
     * @return current value of mLocation.
     */
    public String getLocation(){
        return mLocation;
    }

    /**
     * Gets the date of the earthquake.
     * @return the current value of mDate.
     */
    public long getDate(){
        return mDate;
    }

    /**
     * Gets the web page uri of the earthquake
     */
    public String getWeb(){
        return mWeb;
    }

}
