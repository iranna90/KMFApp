package com.federation.milk.karantaka.kmfapp.token;

import android.util.Base64;

import com.federation.milk.karantaka.kmfapp.services.HttpUtils;

import java.io.IOException;

import cz.msebera.android.httpclient.HttpResponse;

public class TokenRetriever {

    public Token getToken(final String dairyId, final String userName, final String password) {
        String value = appendSeparator(dairyId) + appendSeparator(userName) + encode(password);
        final byte[] data = value.getBytes();
        try {
            HttpResponse response = HttpUtils.post("http://localhost:9765/identity/token", data);
            final Token token = HttpUtils.extractEntityFromResponse(response, Token.class);
            return token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String encode(final String password) {
        return Base64.encodeToString(password.getBytes(), Base64.DEFAULT);
    }

    private static String appendSeparator(final String value) {
        return value + ":";
    }
}
