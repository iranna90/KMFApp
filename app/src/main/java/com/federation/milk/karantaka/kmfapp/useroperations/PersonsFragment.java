package com.federation.milk.karantaka.kmfapp.useroperations;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
    private static final int LOAD_LIMIT = 15;
    private ArrayAdapter<UserEntity> adapter;
    private ListView listView;
    final List<UserEntity> users = new ArrayList<>();
    // initial load
    private int previousTotal = 0;
    private boolean loading = true;
    private int threshold = 5;

    public void setContext(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.persons_fragments, container, false);
        listView = view.findViewById(R.id.person_list);
        // load 10 items
        adapter = new PersonListAdapter(context, users);
        adapter.addAll(getPersons(0, null));
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

        listView.setOnScrollListener(new PersonScrolListener());

        SearchView searchView = ((SearchView) view.findViewById(R.id.searchView));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.clear();
                listView.deferNotifyDataSetChanged();
                adapter.addAll(getPersons(0, query));
                adapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                users.clear();
                listView.deferNotifyDataSetChanged();
                previousTotal = 0;
                adapter.addAll(getPersons(0, null));
                adapter.notifyDataSetChanged();
                return false;
            }
        });
        return view;
    }

    private class PersonScrolListener implements AbsListView.OnScrollListener {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
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
                loadMore(totalItemCount);
            }
        }

        // Called when the user is nearing the end of the ListView
        // and the ListView is ready to add more items.
        public void loadMore(int totalItemCount) {
            adapter.addAll(getPersons(totalItemCount, null));
            adapter.notifyDataSetChanged();
        }
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
}
