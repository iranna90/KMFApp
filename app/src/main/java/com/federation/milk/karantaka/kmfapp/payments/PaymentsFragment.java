package com.federation.milk.karantaka.kmfapp.payments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.federation.milk.karantaka.kmfapp.R;

/**
 * Created by iranna.patil on 09/09/2017.
 */

public class PaymentsFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.payments_fragment, container, false);
    }
}
