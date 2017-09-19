package com.federation.milk.karantaka.kmfapp.useroperations;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.federation.milk.karantaka.kmfapp.MyApplication;
import com.federation.milk.karantaka.kmfapp.R;
import com.federation.milk.karantaka.kmfapp.services.HttpUtils;
import com.federation.milk.karantaka.kmfapp.transactions.PersonOperations;
import com.federation.milk.karantaka.kmfapp.transactions.TransactionEntity;
import com.federation.milk.karantaka.kmfapp.transactions.TransactionListAdapter;
import com.federation.milk.karantaka.kmfapp.transactions.UserTransactions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.lang.String.format;

public class PersonDetailsActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.person_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.person_toolbar);
        setSupportActionBar(toolbar);


        Intent activityThatCalled = getIntent();
        final UserEntity user = (UserEntity) activityThatCalled.getSerializableExtra("user");
        ((TextView) findViewById(R.id.first_name)).setText(user.getFirstName());
        ((TextView) findViewById(R.id.last_name)).setText(user.getLastName());
        ((TextView) findViewById(R.id.aadhar_id)).setText(user.getPersonId());
        ((TextView) findViewById(R.id.amount)).setText(String.valueOf(user.getAmount()));

        // floating add action
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_transaction_action);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addTransaction = new Intent(context, PersonOperations.class);
                addTransaction.putExtra("user", user);
                Log.d("Forwarding to intent", user.toString());
                startActivity(addTransaction);
            }
        });

        ExecutorService services = Executors.newFixedThreadPool(2);
        Future<UserTransactions> tran = services.submit(new GetTransactions(user.getPersonId()));
        Future<UserTransactions> paym = services.submit(new GetPayments(user.getPersonId()));
        List<TransactionEntity> transactions = new ArrayList<>();
        try {
            //fix it at server and shouuld return empty list
            if (tran.get() != null) {
                transactions.addAll(tran.get().getTransactions());
            }
            if (paym.get() != null) {
                transactions.addAll(paym.get().getTransactions());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.d("Transactions are ", transactions.toString());
        ListView listView = (ListView) findViewById(R.id.transaction_list);
        TransactionEntity[] values = new TransactionEntity[]{};
        values = transactions.toArray(values);
        Log.d("value s are", values.toString());
        final ListAdapter adapter = new TransactionListAdapter(context, values);
        listView.setAdapter(adapter);
    }

    private class GetTransactions implements Callable<UserTransactions> {

        private final String personId;

        private GetTransactions(String personId) {
            this.personId = personId;
        }

        @Override
        public UserTransactions call() throws Exception {
            String dairyId = ((MyApplication) getApplication()).getDairyId();
            return HttpUtils.get(
                    format("%s/persons/%s/transactions", dairyId, personId),
                    UserTransactions.class
            );
        }
    }

    private class GetPayments implements Callable<UserTransactions> {

        private final String personId;

        private GetPayments(String personId) {
            this.personId = personId;
        }

        @Override
        public UserTransactions call() throws Exception {
            String dairyId = ((MyApplication) getApplication()).getDairyId();
            return HttpUtils.get(
                    format("%s/persons/%s/payments", dairyId, personId),
                    UserTransactions.class
            );
        }
    }
}
