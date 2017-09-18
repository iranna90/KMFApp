package com.federation.milk.karantaka.kmfapp.transactions;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.federation.milk.karantaka.kmfapp.R;

import java.text.SimpleDateFormat;

/**
 * Created by iranna.patil on 09/09/2017.
 */

public class TransactionListAdapter extends ArrayAdapter<TransactionEntity> {

    private final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    public TransactionListAdapter(Context context, TransactionEntity[] values) {
        super(context, R.layout.transaction_row_layout, values);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater theInflater = LayoutInflater.from(getContext());
        View transactionRowView = theInflater.inflate(R.layout.transaction_row_layout, parent, false);
        TransactionEntity transactionEntity = getItem(position);
        ((TextView) transactionRowView.findViewById(R.id.person_name)).setText(transactionEntity.getPersonName());
        ((TextView) transactionRowView.findViewById(R.id.date)).setText(formatter.format(transactionEntity.getDate()));
        final String amountPrefix = transactionEntity.getType() == Type.PAID ? "-" : "+";
        ((TextView) transactionRowView.findViewById(R.id.amount)).setText(amountPrefix + String.valueOf(transactionEntity.getAmount()));
        return transactionRowView;

    }
}