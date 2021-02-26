package com.example.quakereport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class quake_adapter extends ArrayAdapter<quake_description> {

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
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM DD, yyyy");
        String dateToDisplay = dateFormatter.format(dateObject);
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        String timetodisplay=timeFormat.format(dateObject);
        TextView textViewmag= listItemView.findViewById(R.id.mag);
        textViewmag.setText(current.getMag());

        String place=current.getPlace();
        String primary;
        String Secondary;
        if(!place.contains("of"))
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
