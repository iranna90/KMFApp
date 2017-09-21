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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.federation.milk.karantaka.kmfapp.R;
import com.federation.milk.karantaka.kmfapp.services.HttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by iranna.patil on 09/09/2017.
 */

public class PersonsFragment extends Fragment {
    private Context context;
    private final List<UserEntity> users = new ArrayList<>();
    private static final int LOAD_LIMIT = 15;
    // initial load
    private int previousTotal = 0;

    public void setContext(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.persons_fragments, container, false);
        final ListView listView = view.findViewById(R.id.person_list);
        // load 10 items
        users.addAll(getPersons(0, null));
        final ListAdapter adapter = new PersonListAdapter(context, users);
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
        listView.setOnScrollListener(new PersonLoader());
        SearchView searchView = ((SearchView) view.findViewById(R.id.searchView));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                users.clear();
                listView.deferNotifyDataSetChanged();
                users.addAll(getPersons(0, query));
                listView.deferNotifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("Query string request ", newText);
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Log.d("CLOSING", "laod");
                users.clear();
                listView.deferNotifyDataSetChanged();
                previousTotal = 0;
                users.addAll(getPersons(0, null));
                listView.deferNotifyDataSetChanged();
                return false;
            }
        });
        return view;
    }

    private List<UserEntity> getPersons(int offset, String query) {
        try {
            final String url;
            if (query != null) {
                url = "khajuri/persons?query=" + query;
            } else {
                url = "khajuri/persons?limit=" + LOAD_LIMIT + "&offset=" + offset;
            }
            Persons persons = HttpUtils.get(url, Persons.class);
            if (persons != null) {
                return persons.getUsers();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public class PersonLoader implements AbsListView.OnScrollListener {

        private boolean loading = true;
        private int threshold = 5;

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            Log.d("state changed", "first:" + firstVisibleItem + " v item: " + visibleItemCount + " count: " + totalItemCount);
            Log.d("values", " locading " + String.valueOf(loading) + " previoustotal " + previousTotal);
            if (loading) {
                if (totalItemCount > previousTotal) {
                    // the loading has finished
                    loading = false;
                    previousTotal = totalItemCount;
                }
            }

            // check if the List needs more data
            if (!loading && ((firstVisibleItem + visibleItemCount) >= (totalItemCount - threshold))) {
                loading = true;
                // List needs more data. Go fetch !!
                loadMore(view, firstVisibleItem,
                        visibleItemCount, totalItemCount);
            }
        }

        // Called when the user is nearing the end of the ListView
        // and the ListView is ready to add more items.
        public void loadMore(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            users.addAll(getPersons(totalItemCount, null));
            view.deferNotifyDataSetChanged();
        }
    }
}
