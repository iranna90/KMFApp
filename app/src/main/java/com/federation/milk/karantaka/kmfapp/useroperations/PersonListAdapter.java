package com.federation.milk.karantaka.kmfapp.useroperations;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.federation.milk.karantaka.kmfapp.R;

/**
 * Created by iranna.patil on 09/09/2017.
 */

public class PersonListAdapter extends ArrayAdapter<UserEntity> {

    public PersonListAdapter(Context context, UserEntity[] values) {
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
        UserEntity person = getItem(position);

        Log.d("User detaisls", person.toString());
        TextView firstNameView = (TextView) theView.findViewById(R.id.first_name);
        firstNameView.setText(person.getFirstName());

        TextView lastNameView = (TextView) theView.findViewById(R.id.last_name);
        lastNameView.setText(person.getLastName());

        TextView personIdView = (TextView) theView.findViewById(R.id.aadhar_id);
        personIdView.setText(person.getPersonId());

        TextView amountView = (TextView) theView.findViewById(R.id.amount);
        amountView.setText(String.valueOf(person.getAmount()));
        return theView;

    }
}