package com.linuxzasve.mobile.rest;

import com.linuxzasve.mobile.Constants;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class LzsRestGateway {

	String[] include = { "author", "url", "content", "title", "date", "comment_count", "thumbnail" };


	public void getRecentPosts(final AsyncHttpResponseHandler responseHandler) {
		RequestParams params = new RequestParams();
		params.put("include", generateInclude(include));

		LzsRestClient.get(Constants.GET_RECENT_POSTS_URI, params , responseHandler);
	}

	public void getSearchResult(final String search, final AsyncHttpResponseHandler responseHandler) {
		RequestParams params = new RequestParams();
		params.put("include", generateInclude(include));
		params.put("search", search);

		LzsRestClient.get(Constants.GET_SEARCH_RESULTS_URI, params , responseHandler);
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
}
