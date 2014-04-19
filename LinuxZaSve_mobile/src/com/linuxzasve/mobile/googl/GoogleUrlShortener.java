package com.linuxzasve.mobile.googl;

import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;

import android.content.Context;

import com.google.gson.Gson;
import com.linuxzasve.mobile.googl.model.GooGlRequest;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 *
 * @author dejan
 *
 */
public class GoogleUrlShortener {

	private static final String postUrl = "https://www.googleapis.com/urlshortener/v1/url";
	private static AsyncHttpClient client = new AsyncHttpClient();

	/**
	 *
	 * @param context
	 * @param dugiUrl
	 * @param handler
	 */
	public static void ShortenUrl(final Context context, final String dugiUrl, final AsyncHttpResponseHandler handler) {

		GooGlRequest r = new GooGlRequest();
		r.setLongUrl(dugiUrl);

		Gson gson = new Gson();

		try {
			post(context, postUrl, new StringEntity(gson.toJson(r)), handler);
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @param context
	 * @param url
	 * @param entity
	 * @param responseHandler
	 */
	private static void post(final Context context, final String url, final HttpEntity entity, final AsyncHttpResponseHandler responseHandler) {
		client.post(context, url, entity, "application/json", responseHandler);
	}
}
