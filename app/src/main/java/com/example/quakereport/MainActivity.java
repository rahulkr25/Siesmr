package com.example.quakereport;

import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements   android.app.LoaderManager.LoaderCallbacks<ArrayList<quake_description>> {

    private TextView mEmptyStateTextView;
    public static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=40";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmptyStateTextView=(TextView)findViewById(R.id.empty_view_text);
        ListView earthquakeListView = findViewById(R.id.list);
        earthquakeListView.setEmptyView(mEmptyStateTextView);
        ConnectivityManager cm =
                (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(!isConnected)
        {
            ProgressBar progressBar=(ProgressBar)findViewById(R.id.loading_spinner);
            progressBar.setVisibility(View.GONE);

            mEmptyStateTextView.setText("No Internet Connection..");
        }
        else{




            android.app.LoaderManager loaderManager=getLoaderManager();
            loaderManager.initLoader(0,null,this).forceLoad();}


    }
    void update(ArrayList<quake_description>earthquakes)
    {
        if(earthquakes.size()==0)
        {
            return;
        }
        ListView earthquakeListView = findViewById(R.id.list);

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

    @Override
    public Loader<ArrayList<quake_description>> onCreateLoader(int id, Bundle args) {
        return new EarthquakeLoader(this,USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<quake_description>> loader, ArrayList<quake_description> data) {

        ProgressBar progressBar=(ProgressBar)findViewById(R.id.loading_spinner);
        progressBar.setVisibility(View.GONE);

        update(data);

        mEmptyStateTextView.setText("No earthquakes found.");
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<quake_description>> loader) {
        update(null);
    }




}