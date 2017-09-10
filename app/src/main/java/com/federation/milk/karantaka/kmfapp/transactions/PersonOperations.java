package com.federation.milk.karantaka.kmfapp.transactions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.federation.milk.karantaka.kmfapp.R;

public class PersonOperations extends AppCompatActivity {

    private Context context;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private PersonOperationAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.person_operation_details);

        mSectionsPagerAdapter = new PersonOperationAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.person_operation_pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.operation_tabs);
        tabLayout.setupWithViewPager(mViewPager);
        Intent activityThatCalled = getIntent();
        String userId = activityThatCalled.getExtras().getString("personId");
        final TextView viewById = (TextView) findViewById(R.id.aadhar_id_value);
        Log.d("User id is ", userId);
        viewById.setText(userId);
    }

    public void onClickStoreTransaction(View view) {
        TextView personId = (TextView) findViewById(R.id.aadhar_id_value);
        EditText personName = (EditText) findViewById(R.id.which_person);
        EditText numberOfLiters = (EditText) findViewById(R.id.number_of_liters);
        Toast.makeText(context,
                "Person name " + personName.getText().toString() +
                        " number of liters " + numberOfLiters.getText().toString() +
                        " person Id " + personId.getText(),
                Toast.LENGTH_SHORT).show();
    }

    public void onClickCancelAction(View view) {
        doneAndGoBack(view);
    }

    private void doneAndGoBack(View view) {
        setResult(RESULT_OK);
        finish();
    }
}
