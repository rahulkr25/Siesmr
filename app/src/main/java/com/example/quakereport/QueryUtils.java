package com.example.quakereport;

import android.text.TextUtils;
import android.util.Log;

import androidx.loader.content.CursorLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public final class QueryUtils {
    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link quake_description} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<quake_description> extractEarthquakes(String url) {
          // Create an empty ArrayList that we can start adding earthquakes to

        URL urlQuake = createUrl(url);
        ArrayList<quake_description> earthquakes;
        String earthquakes_string="";
        try {
            earthquakes_string= makeHttpRequest(urlQuake);
        } catch (IOException e) {
            // TODO Handle the IOException
           Log.e("QueryUtils","couldnt get response back from  the given url",e);
            return null;
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
         earthquakes = extractFeatureFromJson(earthquakes_string);

        // Return the {@link Event} object as the result fo the {@link TsunamiAsyncTask}
        return earthquakes;
        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.


        // Return the list of earthquakes

    }
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            //Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;
        }
        return url;
    }
    public static ArrayList<quake_description> extractFeatureFromJson(String earthquakeJSON) {
        ArrayList<quake_description>earthquakes=new ArrayList<>();
        try {
            JSONObject root=new JSONObject(earthquakeJSON);
            JSONArray features=root.optJSONArray("features");
            for(int i=0;i<features.length();i++)
            {
                JSONObject features_object=features.getJSONObject(i);
                JSONObject properties=features_object.getJSONObject("properties");
                String mag= properties.optString("mag");
                String place=properties.optString("place");
                // String time=properties.optString("time");
                long time_milisecond=properties.getLong("time");
                /*  */
                String url=properties.getString("url");
                earthquakes.add(new quake_description(mag,place,time_milisecond,url));
            }
            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }
    private static String makeHttpRequest(URL url) throws IOException {
        if(url==null)
        {
            return "";
        }
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            if(urlConnection.getResponseCode()==200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            else
            {
                //Log.e(LOG_TAG,"Other code");
            }

        }
        catch (ProtocolException e) {
          //  Log.e(LOG_TAG,"Request Method isnt valid for HTTP",e);
        }catch (IOException e) {
            // TODO: Handle the exception
           // Log.e(LOG_TAG,"Problem from reading input Stream",e);
        }

        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
