package com.example.quakereport;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;
import android.graphics.drawable.GradientDrawable;

public class quake_adapter extends ArrayAdapter<quake_description> {
    private int getMagnitudeColor(String magnitude) {
        int magnitudeColorResourceId;
        char magnitudeFloor = magnitude.charAt(0);
        switch (magnitudeFloor) {
            case '0':
            case '1':
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case '2':
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case '3':
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case '4':
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case '5':
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case '6':
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case '7':
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case '8':
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case '9':
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }

    public quake_adapter(@NonNull Context context,ArrayList<quake_description> earthquakes) {
        super(context,0, earthquakes);
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_view, parent, false);
        }
        quake_description current=getItem(position);
        long time=current.getTime();
        Date dateObject = new Date(time);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd,yyyy");
        String dateToDisplay = dateFormatter.format(dateObject);
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        String timetodisplay=timeFormat.format(dateObject);
        String mag=current.getMag();
        if(mag.length()>1)
        {
            mag=mag.substring(0,3);
        }
        else
        {
            mag+=".0";
        }
        TextView textViewmag= listItemView.findViewById(R.id.mag);
        textViewmag.setText(mag);
        GradientDrawable magnitudeCircle = (GradientDrawable) textViewmag.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor =getMagnitudeColor(current.getMag());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        String place=current.getPlace();
        String primary;
        String Secondary;
        if(!place.contains("of")||!place.contains("km"))
        {
            primary=place;
             Secondary="Near of";
        }
        else
        {
            int i=place.indexOf("of");
            Secondary=place.substring(0,i+2);
            primary=place.substring(i+2,place.length());


        }
        
        TextView textViewplaceprimary= listItemView.findViewById(R.id.placeprimary);
        textViewplaceprimary.setText( primary);
        TextView textViewplacesecondary= listItemView.findViewById(R.id.placesecondary);
        textViewplacesecondary.setText( Secondary);
        TextView textViewdate= listItemView.findViewById(R.id.date);
        textViewdate.setText( dateToDisplay);
        TextView textViewtime= listItemView.findViewById(R.id.time);
        textViewtime.setText( timetodisplay);

        return listItemView;


    }


}
