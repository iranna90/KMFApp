package com.federation.milk.karantaka.kmfapp.transactions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.federation.milk.karantaka.kmfapp.R;

/**
 * Created by iranna.patil on 10/09/2017.
 */

public class AddPaymentFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_payment, container, false);
    }
}