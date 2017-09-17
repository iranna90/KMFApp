package com.federation.milk.karantaka.kmfapp.services;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;

public class HttpUtils {
    private static final String BASE_URL = "http://192.168.178.178:1234/kmf/dairies/";

    private static final HttpClient client = HttpClientBuilder.create().build();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T get(String url, Class<T> responseType) throws IOException {
        HttpGet request = new HttpGet(getAbsoluteUrl(url));
        HttpResponse httpResponse = client.execute(request);
        return extractEntityFromResponse(httpResponse, responseType);
    }

    private static <T> T extractEntityFromResponse(final HttpResponse response, final Class<T> clazz) throws IOException {
        return objectMapper.readValue(response.getEntity().getContent(), clazz);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

}
