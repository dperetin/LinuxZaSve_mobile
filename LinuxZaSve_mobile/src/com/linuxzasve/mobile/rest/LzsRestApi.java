package com.linuxzasve.mobile.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class LzsRestApi {

	String baseUrl = "http://www.linuxzasve.com/api/";

	/* number of posts to fetch */
	Integer numberOfPosts = Integer.valueOf(10);

	/* post id to fetch */
	Integer postId = null;

	/* list of basic fields to include */
	String[] include = { "author", "url", "content", "title", "date", "author", "comment_count", "thumbnail" };

	/**
	 *
	 * @return
	 * @throws IOException
	 */
	public String getRecentPosts() throws IOException {
		/* prepare the query string */
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		nameValuePairs.add(new BasicNameValuePair("include", generateInclude(include)));

		/* generate request url */
		String url = generateRequestString(baseUrl, "get_recent_posts/", nameValuePairs);

		/* submit query, get json data */
		String jsonResult = getContent(url);

		return jsonResult;
	}

	/**
	 *
	 * @param search
	 * @return
	 * @throws IOException
	 */
	public String getSearchResult(final String search) throws IOException {
		/* prepare the query string */
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		nameValuePairs.add(new BasicNameValuePair("include", generateInclude(include)));
		nameValuePairs.add(new BasicNameValuePair("search", search));

		/* generate request url */
		String url = generateRequestString(baseUrl, "get_search_results/", nameValuePairs);

		/* submit query, get json data */
		String jsonResult = getContent(url);

		return jsonResult;
	}

	private String getContent(final String url) throws IOException {

		HttpResponse response = null;
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet();
			request.setURI(new URI(url));
			response = client.execute(request);
		}
		catch (URISyntaxException e) {
			e.printStackTrace();
		}
		catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getStringFromInputStream(response.getEntity().getContent());

	}

	private static String getStringFromInputStream(final InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (br != null) {
				try {
					br.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}

	/**
	 * Comma separated values
	 *
	 * @param includes
	 * @return
	 */
	private String generateInclude(final String includes[]) {
		String result = "";
		int i;
		for (i = 0; i < (includes.length - 1); i++) {
			result += (includes[i] + ",");
		}
		result += includes[i];
		return result;

	}

	/**
	 * Method generates url on for fetching content
	 *
	 * @param baseUrl
	 *            base url, must begin with http:// and end with /
	 * @param method
	 *            name of the method to be used, must end with /
	 * @param parameters
	 * @return
	 */
	private String generateRequestString(final String baseUrl, final String method, final List<NameValuePair> parameters) {
		String url = "";

		/* add base url */
		url += baseUrl;

		/* add method */
		url += method;

		/* generate query string */
		String queryString = URLEncodedUtils.format(parameters, "UTF-8");
		url += ("?" + queryString);

		return url;
	}
}
