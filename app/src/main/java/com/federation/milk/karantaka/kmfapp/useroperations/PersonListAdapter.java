package com.federation.milk.karantaka.kmfapp.useroperations;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.federation.milk.karantaka.kmfapp.R;

/**
 * Created by iranna.patil on 09/09/2017.
 */

public class PersonListAdapter extends ArrayAdapter<String> {

    public PersonListAdapter(Context context, String[] values) {
        super(context, R.layout.person_row_layout, values);
    }

    // Override getView which is responsible for creating the rows for our list
    // position represents the index we are in for the array.

    // convertView is a reference to the previous view that is available for reuse. As
    // the user scrolls the information is populated as needed to conserve memory.

    // A ViewGroup are invisible containers that hold a bunch of views and
    // define their layout properties.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // The LayoutInflator puts a layout into the right View
        LayoutInflater theInflater = LayoutInflater.from(getContext());

        // inflate takes the resource to load, the parent that the resource may be
        // loaded into and true or false if we are loading into a parent view.
        View theView = theInflater.inflate(R.layout.person_row_layout, parent, false);

        // We retrieve the text from the array
        String person = getItem(position);

        // Get the TextView we want to edit
        TextView theTextView = (TextView) theView.findViewById(R.id.person_row);

        // Put the next TV Show into the TextView
        theTextView.setText(person);

        return theView;

    }
}