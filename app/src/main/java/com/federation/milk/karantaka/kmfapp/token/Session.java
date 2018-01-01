package com.federation.milk.karantaka.kmfapp.token;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {

    private static final String SHARED_PREF_NAME = "kmf";
    private static final String TOKEN_LOC = "token";
    private static final String REFRESH_TOKEN_LOC = "refreshToken";
    final SharedPreferences preferences;
    final SharedPreferences.Editor editor;
    final Context context;

    public Session(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void storeTokenDetails(final Token token) {
        editor.putString(TOKEN_LOC, token.getToken());
        editor.putString(REFRESH_TOKEN_LOC, token.getRefreshToken());
        editor.commit();
    }

    public String getToken() {
        return preferences.getString(TOKEN_LOC, null);
    }

    public String getRefreshToken() {
        return preferences.getString(REFRESH_TOKEN_LOC, null);
    }
}
