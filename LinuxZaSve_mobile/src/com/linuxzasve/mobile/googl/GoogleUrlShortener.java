package com.linuxzasve.mobile.googl;

import com.linuxzasve.mobile.googl.model.GooGlRequest;
import com.linuxzasve.mobile.googl.model.GooGlResponse;

import retrofit.Callback;
import retrofit.RestAdapter;

/**
 * Class is used to shorten URL via goo.gl service.
 *
 * @author dejan
 */
public class GoogleUrlShortener {

    private static final String BASE_URL = "https://www.googleapis.com";

    /**
     * Method shortens url
     *
     * @param longUrl  long url to be shortened
     * @param callback response handler
     */
    public void shortenUrl(final String longUrl, final Callback<GooGlResponse> callback) {
        GooGlRequest r = new GooGlRequest();
        r.setLongUrl(longUrl);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .build();

        GooGlService service = restAdapter.create(GooGlService.class);

        service.shortenUrl(r, callback);
    }
}
