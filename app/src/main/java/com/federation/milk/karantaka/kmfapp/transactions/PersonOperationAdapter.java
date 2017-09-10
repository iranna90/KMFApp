package com.federation.milk.karantaka.kmfapp.transactions;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by iranna.patil on 10/09/2017.
 */

public class PersonOperationAdapter extends FragmentPagerAdapter {

    public PersonOperationAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new AddTransactionFragment();
            case 1:
                return new AddPaymentFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Add Transaction";
            case 1:
                return "Add Payment";
        }
        return null;
    }
}
