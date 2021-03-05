package com.example.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

 /**
  * Loads a list of earthquakes by using an AsyncTask to perform the
  * network request to the given URL.
  */
 public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<quake_description>> {

     private String mUrl;

     public EarthquakeLoader(Context context, String url) {
         super(context);
         mUrl = url;
     }

     @Override
     protected void onStartLoading() {
          forceLoad();
     }

     /**
      * This is on a background thread.
      */
     @Override
     public ArrayList<quake_description> loadInBackground() {

         if (mUrl == null) {
             return null;
         }

         // Perform the network request, parse the response, and extract a list of earthquakes.
         ArrayList<quake_description>earthquakes = QueryUtils.extractEarthquakes(mUrl);
         return earthquakes;
     }
 }