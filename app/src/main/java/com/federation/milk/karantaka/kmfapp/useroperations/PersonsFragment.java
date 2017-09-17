package com.federation.milk.karantaka.kmfapp.useroperations;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.federation.milk.karantaka.kmfapp.R;
import com.federation.milk.karantaka.kmfapp.services.HttpUtils;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

import cz.msebera.android.httpclient.Header;

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
        String[] names = {"iranna", "viswanath", "abhinash", "arun", "pradhan", "kashi", "dinesh"};
        UserEntity[] persons = new UserEntity[7];
        for (int i = 0; i < 7; i++) {
            persons[i] = new UserEntity(10 + (i * 5), names[i], names[i], UUID.randomUUID().toString());
        }
        ListView listView = view.findViewById(R.id.person_list);
        final ListAdapter adapter = new PersonListAdapter(context, persons);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                UserEntity selectedItem = (UserEntity) adapterView.getItemAtPosition(position);
                Intent personDetailsScreen = new Intent(context, PersonDetailsActivity.class);
                personDetailsScreen.putExtra("personId", selectedItem.getPersonId());
                startActivity(personDetailsScreen);
            }
        });
        getPersons();
        return view;
    }

    private UserEntity[] getPersons() {
        HttpUtils.get("khajuri/persons", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                // Pull out the first event on the public timeline
                Log.d("Json array is", timeline.toString());
                JSONObject firstEvent = null;
                try {
                    firstEvent = (JSONObject) timeline.get(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return null;
    }

}
