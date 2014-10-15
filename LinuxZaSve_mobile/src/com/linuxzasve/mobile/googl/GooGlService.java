package com.linuxzasve.mobile.googl;

import com.linuxzasve.mobile.googl.model.GooGlRequest;
import com.linuxzasve.mobile.googl.model.GooGlResponse;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

interface GooGlService {

    @POST("/urlshortener/v1/url")
    void shortenUrl(@Body GooGlRequest gooGlRequestBody, Callback<GooGlResponse> callback);
}
