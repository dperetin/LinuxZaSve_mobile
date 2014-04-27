package com.linuxzasve.mobile.rest;

import com.linuxzasve.mobile.Constants;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class LzsRestClient {

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(final String url, final RequestParams params, final AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(final String url, final RequestParams params, final AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(final String relativeUrl) {
        return Constants.BASE_REST_URL + relativeUrl;
    }
}