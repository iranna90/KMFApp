package com.federation.milk.karantaka.kmfapp.useroperations;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.federation.milk.karantaka.kmfapp.R;
import com.federation.milk.karantaka.kmfapp.services.HttpUtils;

import java.io.IOException;
import java.util.Collections;

/**
 * Created by iranna.patil on 09/09/2017.
 */

public class PersonsFragment extends Fragment {
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.persons_fragments, container, false);
        ListView listView = view.findViewById(R.id.person_list);
        final ListAdapter adapter = new PersonListAdapter(context, getPersons());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                UserEntity selectedItem = (UserEntity) adapterView.getItemAtPosition(position);
                Intent personDetailsScreen = new Intent(context, PersonDetailsActivity.class);
                personDetailsScreen.putExtra("user", selectedItem);
                startActivity(personDetailsScreen);
            }
        });
        view.findViewById(R.id.progressbar_loading).setVisibility(View.GONE);
        return view;
    }

    private UserEntity[] getPersons() {
        try {
            return HttpUtils.get("khajuri/persons", Persons.class).getUsers();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (UserEntity[]) Collections.EMPTY_LIST.toArray();
    }

}
