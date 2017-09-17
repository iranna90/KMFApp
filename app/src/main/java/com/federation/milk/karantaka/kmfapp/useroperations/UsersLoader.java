package com.federation.milk.karantaka.kmfapp.useroperations;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by iranna.patil on 18/09/2017.
 */

public class UsersLoader extends AsyncTask<Void, Void, Void> {

    private final Context context;
    private final PersonsFragment personsFragment;

    public UsersLoader(Context context, PersonsFragment personsFragment) {
        this.context = context;
        this.personsFragment = personsFragment;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }
}
