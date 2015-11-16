package com.example.sashok.todoo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by asmurthy on 11/14/15.
 */
public class MyCustomArrayAdapter extends ArrayAdapter<Item> {

    private static class ViewHolder {
        TextView name;
        TextView priority;
    }



    public MyCustomArrayAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Item item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.tvName);
            viewHolder.priority = (TextView) convertView.findViewById(R.id.tvPrio);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.name.setText(item.name);
        viewHolder.priority.setText(item.priority);
        // Return the completed view to render on screen
        return convertView;
    }


}
