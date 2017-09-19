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
import android.widget.Toast;

import com.federation.milk.karantaka.kmfapp.R;
import com.federation.milk.karantaka.kmfapp.services.HttpUtils;

import java.io.IOException;
import java.util.Collections;

/**
 * Created by iranna.patil on 09/09/2017.
 */

public class PersonsFragment extends Fragment {
    private Context context;
    private final UserEntity[] users = new UserEntity[]{};

    public void setContext(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.persons_fragments, container, false);
        ListView listView = view.findViewById(R.id.person_list);
        // load 10 items
        addUsersToList(users, getPersons(10, 0));
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
        listView.setOnScrollListener(new LazyLoader());
        view.findViewById(R.id.progressbar_loading).setVisibility(View.GONE);
        return view;
    }

    private void addUsersToList(UserEntity[] users, UserEntity[] persons) {
        Log.d("retireved persons ", String.valueOf(persons.length));
        int existingSize = users.length;
        for (int i = 0; i < persons.length; i++) {
            users[existingSize + i] = persons[i];
        }
    }

    private UserEntity[] getPersons(int limit, int offset) {
        try {
            return HttpUtils.get("khajuri/persons?limit=" + limit + "&offset=" + offset, Persons.class).getUsers();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (UserEntity[]) Collections.EMPTY_LIST.toArray();
    }

    public class LazyLoader implements AbsListView.OnScrollListener {

        private static final int DEFAULT_THRESHOLD = 10;

        private boolean loading = true;
        private int previousTotal = 0;
        private int threshold = DEFAULT_THRESHOLD;

        public LazyLoader() {
        }

        public LazyLoader(int threshold) {
            this.threshold = threshold;
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

            Log.d("state changed", "first:" + scrollState);
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            Log.d("state changed", "first:" + firstVisibleItem + " v item: " + visibleItemCount + " count: " + totalItemCount);
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
            Log.d("state changed", "first:" + firstVisibleItem + " v item: " + visibleItemCount + " count: " + totalItemCount);
            Toast.makeText(context, " First item " + firstVisibleItem + " count " + visibleItemCount + " total " + totalItemCount, Toast.LENGTH_SHORT).show();
        }
    }
}
