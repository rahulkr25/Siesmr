package com.example.quakereport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
        TextView textViewmag=(TextView) listItemView.findViewById(R.id.mag);
        textViewmag.setText(current.getMag());
        TextView textViewplace=(TextView) listItemView.findViewById(R.id.place);
        textViewplace.setText( current.getPlace());
        TextView textViewdate=(TextView) listItemView.findViewById(R.id.date);
        textViewdate.setText( current.getDate());

        return listItemView;
    }
}
