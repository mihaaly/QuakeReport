package com.example.android.quakereport;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Custom ArrayAdapter for an ArrayList of custom Earthquake objects.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    /**
     * Constructor
     * @param context
     * @param earthquakes list of Earthquake.java objects to be displayed
     */
    public EarthquakeAdapter(Activity context, ArrayList<Earthquake> earthquakes){
        super(context, 0, earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // pass in the custom layout for a list item
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,
                    parent, false);
        }

        // get current Earthquake.java element
        Earthquake currentEarthquake = getItem(position);

        /**
         * Magnitude data
         */
        // Initialize new DecimalFormat instance with the desired pattern, on decimal
        DecimalFormat magFormatter = new DecimalFormat("0.0");
        // format the data from the Earthquake.java object with the DecimalFormat instance
        // (magFormatter) and store it in a String variable, DecimalFormat.format(double d)
        String formattedMag = magFormatter.format(currentEarthquake.getMagnitude());
        // Find the vie you want to show the magnitude value in
        TextView textViewMagnitude = (TextView) listItemView.findViewById(R.id.textViewMagnitude);
        // set the magnitude value (now formatted and store in String formattedMag) on that vie
        textViewMagnitude.setText(formattedMag);
        /**
         * Magnitude background
         * Set the proper background color on the magnitude circle.
         */
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) textViewMagnitude.getBackground();
        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());
        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        /**
         * Location
         */
        // TextView for 1st part of location ("xxkm N of ", OR "Near the ")
        TextView textViewLocationOffset =
                (TextView) listItemView.findViewById(R.id.textViewLocationOffset);
        // TextView for 2nd half of location ("Budapest, Hungary")
        TextView textViewLocation = (TextView) listItemView.findViewById(R.id.textViewLocation);
        // String variables for storing 1st and 2nd half of the location
        String offsetLocation, primaryLocation;
        // original not split location information
        String location = currentEarthquake.getLocation();
        // see if there is a "xxkm N of " part in the location information
        if (location.contains("of")){
            //get the index of the firs "f" character
            int splitPoint = location.indexOf("f");
            //increase it by one, as substring() 2nd argument is exclusive, we need the "f"
            splitPoint++;
            //create substring for the 1st half of the location
            offsetLocation = location.substring(0, splitPoint);
            //set it on the appropriate TextView
            textViewLocationOffset.setText(offsetLocation);
            //increase index further by one, as substring() 1st argument is inclusive,
            // we do not need the space
            splitPoint++;
            //create substring for the 2nd half of the location
            primaryLocation = location.substring(splitPoint);
            //set it on the appropriate TextView
            textViewLocation.setText(primaryLocation);

        // if not, 1st half, for UI purposes, will be "Near the "
        } else {
            offsetLocation = "Near the ";
            //set offsetLocation on the appropriate TextView
            textViewLocationOffset.setText(offsetLocation);
            //set original location data on the second TextView
            textViewLocation.setText(location);
        }

        // alternative solution of string manipulation
//        String string = "004-034556";
//        String[] parts = string.split("(?<=-)");
//        String part1 = parts[0]; // 004-
//        String part2 = parts[1]; // 034556


        /**
         * Date
         */
        // Create new Date object from the time in milliseconds of the Earthquake.java
        Date dateObject = new Date(currentEarthquake.getDate());
        // Initialize SimpleDateFormat instance with the desired pattern
        SimpleDateFormat simpleDateFormatter = new SimpleDateFormat("MMM dd, yyyy");
        // Format the date string (i.e. "Mar 3, 1984"), SimpleDateFormat.format(Date dateObject)
        String formattedDate = simpleDateFormatter.format(dateObject);
        // Find the TextView containing the date
        TextView textViewDate = (TextView) listItemView.findViewById(R.id.textViewDate);
        // Set formatted date as a string to the TextView
        textViewDate.setText(formattedDate);

        // repeat process for Time and pass in the same dateObject (UNIX time stores date and time)
        SimpleDateFormat simpleTimeFormatter = new SimpleDateFormat("h:mm a");
        String formattedTime = simpleTimeFormatter.format(dateObject);
        TextView textView = (TextView) listItemView.findViewById(R.id.textViewTime);
        textView.setText(formattedTime);

        return listItemView;
    }

    /**
     * Helper method to get appropriate integer color value based on the magnitude
     * @param magnitude earthquake magnitude
     * @return integer color value
     */
    private int getMagnitudeColor(double magnitude){
        // feed into this variable the color resource ID which will be converted into
        // a color integer value at the end of the method
        int magnitudeResourceColorId;
        // the passed in double is rounded to fit into a category and converted into an integer
        // According to the documentation, the switch statement cannot accept a double value,
        // so we should convert our decimal magnitude value into an integer.
        // This means finding the closest integer less than the decimal value.
        // The floor of the value 1.2 would be the integer 1. Informally,
        // for a positive decimal number, you can think of it as truncating the part of the number
        // after the decimal point.
        int magnitudeFloor = (int) Math.floor(magnitude);
        // the passed in magnitude (now magnitudeFloor integer) is categorized
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeResourceColorId = R.color.magnitude1;
                break;
            case 2:
                magnitudeResourceColorId = R.color.magnitude2;
                break;
            case 3:
                magnitudeResourceColorId = R.color.magnitude3;
                break;
            case 4:
                magnitudeResourceColorId = R.color.magnitude4;
                break;
            case 5:
                magnitudeResourceColorId = R.color.magnitude5;
                break;
            case 6:
                magnitudeResourceColorId = R.color.magnitude6;
                break;
            case 7:
                magnitudeResourceColorId = R.color.magnitude7;
                break;
            case 8:
                magnitudeResourceColorId = R.color.magnitude8;
                break;
            case 9:
                magnitudeResourceColorId = R.color.magnitude9;
                break;
            case 10:
                magnitudeResourceColorId = R.color.magnitude10plus;
                break;
            default:
                magnitudeResourceColorId = R.color.magnitude1;
                break;
        }

        // Remember that color resource IDs just point to the resource we defined,
        // but not the value of the color.
        // you refer to color with a resource ID which is an integer, you still have to convert
        // them to an integer though:
        // call ContextCompat.getColor() to convert the color resource ID into
        // an actual integer color value
        // int magnitude1Color = ContextCompat.getColor(getContext(), R.color.magnitude1);
        return ContextCompat.getColor(getContext(), magnitudeResourceColorId);
    }
}
