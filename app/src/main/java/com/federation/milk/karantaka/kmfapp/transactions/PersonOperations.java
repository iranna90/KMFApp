package com.federation.milk.karantaka.kmfapp.transactions;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.federation.milk.karantaka.kmfapp.MyApplication;
import com.federation.milk.karantaka.kmfapp.R;
import com.federation.milk.karantaka.kmfapp.services.HttpUtils;
import com.federation.milk.karantaka.kmfapp.useroperations.UserEntity;

import java.io.IOException;

import cz.msebera.android.httpclient.HttpResponse;

import static java.lang.String.format;

public class PersonOperations extends AppCompatActivity {

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
        setContentView(R.layout.person_operation_details);

        mSectionsPagerAdapter = new PersonOperationAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.person_operation_pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.operation_tabs);
        tabLayout.setupWithViewPager(mViewPager);
        Intent activityThatCalled = getIntent();
        UserEntity user = (UserEntity) activityThatCalled.getSerializableExtra("user");
        ((TextView) findViewById(R.id.first_name)).setText(user.getFirstName());
        ((TextView) findViewById(R.id.last_name)).setText(user.getLastName());
        ((TextView) findViewById(R.id.aadhar_id)).setText(user.getPersonId());
        ((TextView) findViewById(R.id.amount)).setText(String.valueOf(user.getAmount()));
    }

    public void onClickStoreTransaction(View view) throws IOException {
        TextView personId = (TextView) findViewById(R.id.aadhar_id);
        EditText personName = (EditText) findViewById(R.id.which_person);
        EditText numberOfLiters = (EditText) findViewById(R.id.number_of_liters);
        TransactionPutEntity transactionEntity = new TransactionPutEntity(
                Integer.valueOf(numberOfLiters.getText().toString()),
                personName.getText().toString()
        );
        HttpResponse response = createTransaction(personId.getText().toString(), transactionEntity);
        if (response.getStatusLine().getStatusCode() == 200) {
            doneAndGoBack(view);
        } else {
            String message = format("Unable to store transaction, status code is : %s", response.getStatusLine().getStatusCode());
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickStorePayment(View view) throws IOException {
        TextView personId = (TextView) findViewById(R.id.aadhar_id);
        EditText personName = (EditText) findViewById(R.id.which_person);
        EditText numberOfLiters = (EditText) findViewById(R.id.amount_paid);
        PaymentPutEntity entity = new PaymentPutEntity(
                Integer.valueOf(numberOfLiters.getText().toString()),
                personName.getText().toString()
        );
        HttpResponse response = createPayment(personId.getText().toString(), entity);
        if (response.getStatusLine().getStatusCode() == 200) {
            doneAndGoBack(view);
        } else {
            String message = format("Unable to store transaction, status code is : %s", response.getStatusLine().getStatusCode());
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    private HttpResponse createPayment(String personId, PaymentPutEntity entity) throws IOException {
        String dairyId = ((MyApplication) getApplication()).getDairyId();
        return HttpUtils.post(format("%s/persons/%s/payments", dairyId, personId), entity);
    }

    private HttpResponse createTransaction(String personId, TransactionPutEntity entity) throws IOException {
        String dairyId = ((MyApplication) getApplication()).getDairyId();
        return HttpUtils.post(format("%s/persons/%s/transactions", dairyId, personId), entity);
    }


    public void onClickCancelAction(View view) {
        doneAndGoBack(view);
    }

    private void doneAndGoBack(View view) {
        setResult(RESULT_OK);
        finish();
    }

    public class TransactionPutEntity {
        @JsonProperty("numberOfliters")
        private final int numberOfliters;
        @JsonProperty("personName")
        private final String personName;

        private TransactionPutEntity(int numberOfliters, String personName) {
            this.numberOfliters = numberOfliters;
            this.personName = personName;
        }

        public int getNumberOfliters() {
            return numberOfliters;
        }

        public String getPersonName() {
            return personName;
        }

        @Override
        public String toString() {
            return "TransactionPutEntity{" +
                    "numberOfliters=" + numberOfliters +
                    ", personName='" + personName + '\'' +
                    '}';
        }
    }

    public class PaymentPutEntity {
        @JsonProperty("amount")
        private final int amount;
        @JsonProperty("paidTo")
        private final String paidTo;

        private PaymentPutEntity(int amount, String personName) {
            this.amount = amount;
            this.paidTo = personName;
        }

        public int getAmount() {
            return amount;
        }

        public String getPersonName() {
            return paidTo;
        }
    }
}
