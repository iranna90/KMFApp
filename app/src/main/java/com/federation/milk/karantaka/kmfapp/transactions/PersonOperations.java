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
        EditText personName = (EditText) findViewById(R.id.transaction_user);
        EditText numberOfLitersView = (EditText) findViewById(R.id.number_of_liters);
        Integer numberOfLiters = Integer.valueOf(numberOfLitersView.getText().toString());
        if (numberOfLiters != null && numberOfLiters <= 0) {
            Toast.makeText(this, "Number of listers should be greater then 0", Toast.LENGTH_SHORT).show();
            return;
        }

        TransactionPutEntity transactionEntity = new TransactionPutEntity(
                numberOfLiters,
                personName.getText().toString(),
                calculateAmount(numberOfLiters),
                Type.DEPOSITED
        );

        HttpResponse response = createTransaction(personId.getText().toString(), transactionEntity);
        if (response.getStatusLine().getStatusCode() == 200) {
            TransactionEntity entity = HttpUtils.extractEntityFromResponse(response, TransactionEntity.class);
            doneAndGoBack(view, entity);
        } else {
            String message = format("Unable to store transaction, status code is : %s", response.getStatusLine().getStatusCode());
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    private int calculateAmount(int numberOfLiters) {
        int pricePerLiter = ((MyApplication) getApplication()).getPricePerLiter();
        return numberOfLiters * pricePerLiter;
    }

    public void onClickStorePayment(View view) throws IOException {
        TextView personId = (TextView) findViewById(R.id.aadhar_id);
        EditText personName = (EditText) findViewById(R.id.payment_user);
        EditText amountPaid = (EditText) findViewById(R.id.amount_paid);
        TransactionPutEntity entity = new TransactionPutEntity(
                0,
                personName.getText().toString(),
                Integer.valueOf(amountPaid.getText().toString()),
                Type.PAID
        );
        HttpResponse response = createTransaction(personId.getText().toString(), entity);
        if (response.getStatusLine().getStatusCode() == 200) {
            TransactionEntity transactionEntity = HttpUtils.extractEntityFromResponse(response, TransactionEntity.class);
            doneAndGoBack(view, transactionEntity);
        } else {
            String message = format("Unable to store transaction, status code is : %s", response.getStatusLine().getStatusCode());
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    private HttpResponse createTransaction(String personId, TransactionPutEntity entity) throws IOException {
        String dairyId = ((MyApplication) getApplication()).getDairyId();
        return HttpUtils.post(format("%s/persons/%s/transactions", dairyId, personId), entity);
    }


    public void onClickCancelAction(View view) {
        doneAndGoBack(view, null);
    }

    private void doneAndGoBack(View view, TransactionEntity entity) {
        Intent goingBack = new Intent();
        goingBack.putExtra("transaction", entity);
        setResult(RESULT_OK, goingBack);
        finish();
    }

    public class TransactionPutEntity {
        @JsonProperty("numberOfliters")
        private final int numberOfliters;
        @JsonProperty("personName")
        private final String personName;
        @JsonProperty("amount")
        private final int amount;
        @JsonProperty("transactionType")
        private final Type type;

        private TransactionPutEntity(int numberOfliters, String personName, int amount, Type type) {
            this.numberOfliters = numberOfliters;
            this.personName = personName;
            this.amount = amount;
            this.type = type;
        }

        public int getNumberOfliters() {
            return numberOfliters;
        }

        public String getPersonName() {
            return personName;
        }

        public int getAmount() {
            return amount;
        }

        public Type getType() {
            return type;
        }

        @Override
        public String toString() {
            return "TransactionPutEntity{" +
                    "numberOfliters=" + numberOfliters +
                    ", personName='" + personName + '\'' +
                    ", amount=" + amount +
                    ", type=" + type +
                    '}';
        }
    }
}
