package com.federation.milk.karantaka.kmfapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.federation.milk.karantaka.kmfapp.services.HttpUtils;
import com.federation.milk.karantaka.kmfapp.useroperations.CreateUser;
import com.federation.milk.karantaka.kmfapp.useroperations.PersonDetailsActivity;
import com.federation.milk.karantaka.kmfapp.useroperations.PersonListAdapter;
import com.federation.milk.karantaka.kmfapp.useroperations.Persons;
import com.federation.milk.karantaka.kmfapp.useroperations.UserEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DairyOperationActivity extends AppCompatActivity {

    private Context context;
    private static final int LOAD_LIMIT = 15;
    private ArrayAdapter<UserEntity> adapter;
    private ListView listView;
    final List<UserEntity> users = new ArrayList<>();
    // initial load
    private int previousTotal = 0;
    private boolean loading = true;
    private int threshold = 5;
    private SwipeRefreshLayout swipeRefreshLayout;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO : needs to change
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        context = this;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.main_content);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.person_list);
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

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_users);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reload();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_person);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int resultCode = 1;
                Intent createUserIntent = new Intent(context, CreateUser.class);
                startActivityForResult(createUserIntent, resultCode);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.searchView).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.clear();
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
                return reload();
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            UserEntity entity = (UserEntity) data.getSerializableExtra("user");
            int position = data.getIntExtra("updated", -2);
            if (position != -2) {
                String message;
                if (position == -1) {
                    adapter.insert(entity, 0);
                    message = "User added successful ";
                } else {
                    UserEntity existingUser = adapter.getItem(position);
                    existingUser.setAmount(entity.getAmount());
                    message = "User update successfully ";
                }
                adapter.notifyDataSetChanged();
                Toast.makeText(context, message + entity.getFirstName(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean reload() {
        adapter.clear();
        previousTotal = 0;
        loading = false;
        adapter.addAll(getPersons(0, null));
        adapter.notifyDataSetChanged();
        return false;
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
