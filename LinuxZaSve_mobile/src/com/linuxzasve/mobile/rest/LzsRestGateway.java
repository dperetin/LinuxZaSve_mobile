package com.linuxzasve.mobile.rest;

import com.linuxzasve.mobile.Constants;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class LzsRestGateway {

	String[] defaultInclude = { "author", "url", "content", "title", "date", "comment_count", "thumbnail" };
	String[] commentInclude = {"comments"};


	public void getRecentPosts(final AsyncHttpResponseHandler responseHandler) {
		RequestParams params = new RequestParams();
		params.put("include", generateInclude(defaultInclude));

		LzsRestClient.get(Constants.GET_RECENT_POSTS_URI, params , responseHandler);
	}

	public void getSearchResult(final String search, final AsyncHttpResponseHandler responseHandler) {
		RequestParams params = new RequestParams();
		params.put("include", generateInclude(defaultInclude));
		params.put("search", search);

		LzsRestClient.get(Constants.GET_SEARCH_RESULTS_URI, params , responseHandler);
	}
	
	public void getCommentsForPost(final Integer post_id, final AsyncHttpResponseHandler responseHandler) {
		RequestParams params = new RequestParams();
		params.put("include", generateInclude(commentInclude));
		params.put("post_id", post_id.toString());

		LzsRestClient.get(Constants.GET_POST_URI, params, responseHandler);
	}
	
	public void getNonceForPost(final AsyncHttpResponseHandler responseHandler) {
		RequestParams params = new RequestParams();
		params.put("method", "create_post");
		params.put("controller", "posts");

		LzsRestClient.get(Constants.GET_NONCE, params, responseHandler);
	}
	
	public void submitComment(final String postId, String name, String email, String content, final AsyncHttpResponseHandler responseHandler) {
		RequestParams params = new RequestParams();
		params.put("post_id", postId);
		params.put("name", name);
		params.put("email", email);
		params.put("content", content);

		LzsRestClient.post(Constants.SUBMIT_COMMENT, params, responseHandler);
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
