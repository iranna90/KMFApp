package com.federation.milk.karantaka.kmfapp.useroperations;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.federation.milk.karantaka.kmfapp.MyApplication;
import com.federation.milk.karantaka.kmfapp.R;
import com.federation.milk.karantaka.kmfapp.services.HttpUtils;
import com.federation.milk.karantaka.kmfapp.transactions.PersonOperations;
import com.federation.milk.karantaka.kmfapp.transactions.TransactionEntity;
import com.federation.milk.karantaka.kmfapp.transactions.TransactionListAdapter;
import com.federation.milk.karantaka.kmfapp.transactions.Type;
import com.federation.milk.karantaka.kmfapp.transactions.UserTransactions;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static java.lang.String.format;

public class PersonDetailsActivity extends AppCompatActivity {

    private static final int LIMIT = 15;
    private int previousTotal = 0;
    private boolean loading = true;
    private int threshold = 5;

    private ListView transactionListView;

    private ArrayAdapter<TransactionEntity> transactionListAdapter;

    private Context context;
    private TextView amountView;
    private UserEntity user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.person_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.person_toolbar);
        setSupportActionBar(toolbar);


        Intent activityThatCalled = getIntent();
        user = (UserEntity) activityThatCalled.getSerializableExtra("user");
        ((TextView) findViewById(R.id.first_name)).setText(user.getFirstName());
        ((TextView) findViewById(R.id.last_name)).setText(user.getLastName());
        ((TextView) findViewById(R.id.aadhar_id)).setText(user.getPersonId());
        amountView = ((TextView) findViewById(R.id.amount));
        amountView.setText(String.valueOf(user.getAmount()));

        // floating add action
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_transaction_action);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addTransaction = new Intent(context, PersonOperations.class);
                addTransaction.putExtra("user", user);
                Log.d("Forwarding to intent", user.toString());
                startActivityForResult(addTransaction, 4);
            }
        });

        List<TransactionEntity> transactions = null;
        try {
            transactions = getTransactions(0, user.getPersonId()).getTransactions();
        } catch (IOException e) {
            e.printStackTrace();
            transactions = Collections.emptyList();
        }
        transactionListView = (ListView) findViewById(R.id.transaction_list);
        transactionListAdapter = new TransactionListAdapter(context, transactions);
        transactionListView.setAdapter(transactionListAdapter);
        transactionListView.setOnScrollListener(new TransactionScrolListener(user.getPersonId()));
    }


    public UserTransactions getTransactions(final int offset, final String personId) throws IOException {
        String dairyId = ((MyApplication) getApplication()).getDairyId();
        return HttpUtils.get(
                format("%s/persons/%s/transactions?limit=%s&offset=%s", dairyId, personId, LIMIT, offset),
                UserTransactions.class
        );
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1 & requestCode == 4 & data != null) {
            TransactionEntity entity = (TransactionEntity) data.getSerializableExtra("transaction");
            transactionListAdapter.insert(entity, 0);
            transactionListAdapter.notifyDataSetChanged();
            long changed = entity.getType() == Type.PAID ? (-entity.getAmount()) : entity.getAmount();
            long newAmount = Long.valueOf(amountView.getText().toString()) + changed;
            user = user.clone(newAmount);
            amountView.setText(String.valueOf(newAmount));
        }
    }

    private class TransactionScrolListener implements AbsListView.OnScrollListener {
        private String personId;

        public TransactionScrolListener(String personId) {
            this.personId = personId;
        }

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
                try {
                    loadMore(totalItemCount);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Error loading next transaction elements", Toast.LENGTH_SHORT).show();
                }
            }
        }

        // Called when the user is nearing the end of the ListView
        // and the ListView is ready to add more items.
        public void loadMore(int totalItemCount) throws IOException {
            UserTransactions transactions = getTransactions(totalItemCount, personId);
            if (transactions == null) {
                return;
            }
            transactionListAdapter.addAll(transactions.getTransactions());
            transactionListAdapter.notifyDataSetChanged();
        }
    }
}
