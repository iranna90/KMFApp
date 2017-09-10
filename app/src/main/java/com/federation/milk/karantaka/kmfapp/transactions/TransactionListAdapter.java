package com.federation.milk.karantaka.kmfapp.transactions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.federation.milk.karantaka.kmfapp.R;

/**
 * Created by iranna.patil on 09/09/2017.
 */

public class TransactionListAdapter extends ArrayAdapter<TransactionEntity> {

    public TransactionListAdapter(Context context, TransactionEntity[] values) {
        super(context, R.layout.transaction_row_layout, values);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater theInflater = LayoutInflater.from(getContext());
        View theView = theInflater.inflate(R.layout.transaction_row_layout, parent, false);
        TransactionEntity transactionEntity = getItem(position);
        TextView theTextView = (TextView) theView.findViewById(R.id.transaction_item);
        String item;
        if (transactionEntity.getType().equals(Type.PAID)) {
            item = "Amount " +
                    transactionEntity.getAmount() +
                    " paid to " +
                    transactionEntity.getPersonName() +
                    " on " +
                    transactionEntity.getDate();
        } else {
            item = "Amount " +
                    transactionEntity.getAmount() +
                    "(" +
                    transactionEntity.getNumberOfLiters() +
                    "liters) deposited by user " +
                    transactionEntity.getPersonName() +
                    " on " +
                    transactionEntity.getDate();
        }
        theTextView.setText(item);
        return theView;

    }
}