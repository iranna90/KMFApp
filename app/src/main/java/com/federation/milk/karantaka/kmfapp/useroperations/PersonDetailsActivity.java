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

import com.federation.milk.karantaka.kmfapp.R;
import com.federation.milk.karantaka.kmfapp.transactions.PersonOperations;
import com.federation.milk.karantaka.kmfapp.transactions.TransactionEntity;
import com.federation.milk.karantaka.kmfapp.transactions.TransactionListAdapter;
import com.federation.milk.karantaka.kmfapp.transactions.Type;

import java.util.Date;

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
        String personId = activityThatCalled.getExtras().getString("personId");
        final TextView viewById = (TextView) findViewById(R.id.aadhar_id_value);
        viewById.setText(personId);

        // floating add action
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_transaction_action);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String personId = String.valueOf(viewById.getText());
                Intent addTransaction = new Intent(context, PersonOperations.class);
                addTransaction.putExtra("personId", personId);
                Log.d("Forwarding to intent", personId);
                startActivity(addTransaction);
            }
        });

        // list view
        String[] persons = {"iranna", "viswanath", "abhinash", "arun", "pradhan", "kashi", "dinesh", "end of list"};
        TransactionEntity[] entities = new TransactionEntity[16];
        for (int i = 0; i < 8; i++) {
            entities[i] = new TransactionEntity(10 + (9 * i), new Date(), i, persons[i], Type.DEPOSITED);
        }

        for (int i = 8; i < 16; i++) {
            entities[i] = new TransactionEntity(10 + (19 * i), new Date(), 0, persons[i - 8], Type.PAID);
        }
        ListView listView = (ListView) findViewById(R.id.transaction_list);
        final ListAdapter adapter = new TransactionListAdapter(context, entities);
        listView.setAdapter(adapter);
    }

    public void backToMainActivity(View view) {
        setResult(RESULT_OK);
        finish();
    }
}
