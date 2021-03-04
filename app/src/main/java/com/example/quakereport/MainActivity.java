package com.example.quakereport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=40";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EarthquakeAsyncTask task=new EarthquakeAsyncTask();
        task.execute();
       // ArrayList<quake_description> earthquakes = QueryUtils.extractEarthquakes();

        // ArrayList<quake_description>earthquakes=getearthquakes();
        // Find a reference to the {@link ListView} in the layout
      /*  ListView earthquakeListView = (ListView) findViewById(R.id.list);
        quake_adapter adapter=new quake_adapter(this,earthquakes);


        // Create a new {@link ArrayAdapter} of earthquakes

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);*/
     /*   earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String url =  earthquakes.get(position).getUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });*/


    }
    private class EarthquakeAsyncTask extends AsyncTask<String,Void,ArrayList<quake_description>>{
        @Override
        protected ArrayList<quake_description> doInBackground(String... strings) {
             ArrayList<quake_description> earthquakes = QueryUtils.extractEarthquakes(USGS_REQUEST_URL);
            return earthquakes;
        }

        @Override
        protected void onPostExecute(ArrayList<quake_description> earthquakess) {

            updateui(earthquakess);



        }
    }
    void updateui(ArrayList<quake_description>earthquakes)
    {
        ListView earthquakeListView = (ListView) findViewById(R.id.list);
        quake_adapter adapter=new quake_adapter(this,earthquakes);


        // Create a new {@link ArrayAdapter} of earthquakes

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String url =  earthquakes.get(position).getUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

}