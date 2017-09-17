package com.federation.milk.karantaka.kmfapp.useroperations;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.federation.milk.karantaka.kmfapp.R;

public class PersonListAdapter extends ArrayAdapter<UserEntity> {

    public PersonListAdapter(Context context, UserEntity[] values) {
        super(context, R.layout.person_row_layout, values);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater theInflater = LayoutInflater.from(getContext());
        View theView = theInflater.inflate(R.layout.person_row_layout, parent, false);
        UserEntity person = getItem(position);
        theView.<TextView>findViewById(R.id.first_name).setText(person.getFirstName());
        theView.<TextView>findViewById(R.id.last_name).setText(person.getLastName());
        theView.<TextView>findViewById(R.id.aadhar_id).setText(person.getPersonId());
        theView.<TextView>findViewById(R.id.amount).setText(String.valueOf(person.getAmount()));
        return theView;

    }
}