package com.federation.milk.karantaka.kmfapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.federation.milk.karantaka.kmfapp.payments.PaymentsFragment;
import com.federation.milk.karantaka.kmfapp.transactions.TransactionsFragment;
import com.federation.milk.karantaka.kmfapp.useroperations.PersonsFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                PersonsFragment personsFragment = new PersonsFragment();
                personsFragment.setContext(context);
                return personsFragment;
            case 1:
                return new TransactionsFragment();
            case 2:
                return new PaymentsFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Persons";
            case 1:
                return "Transactions";
            case 2:
                return "Payments";
        }
        return null;
    }
}