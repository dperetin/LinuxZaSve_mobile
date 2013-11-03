package com.linuxzasve.mobile.timthumb;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

public class TimThumb {

	static String baseUrl = "http://www.linuxzasve.com/wp-content/themes/bigfoot/includes/timthumb.php";

	public static String generateTimThumbUrl(final String imageUrl, final int height, final int width, final int mode) {
		String url = baseUrl;

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
		nameValuePairs.add(new BasicNameValuePair("src", imageUrl));
		nameValuePairs.add(new BasicNameValuePair("h", String.valueOf(height)));
		nameValuePairs.add(new BasicNameValuePair("w", String.valueOf(width)));
		nameValuePairs.add(new BasicNameValuePair("zc", String.valueOf(mode)));

		String queryString = URLEncodedUtils.format(nameValuePairs, "UTF-8");
		url += ("?" + queryString);

		return url;
	}
}
