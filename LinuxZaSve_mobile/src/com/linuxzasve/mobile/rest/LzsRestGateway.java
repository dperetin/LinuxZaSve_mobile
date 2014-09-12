package com.linuxzasve.mobile.rest;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Class holds methods for working with WordPress REST plugin.
 */
public class LzsRestGateway {

    public static final String[] DEFAULT_INCLUDE = {"author", "url", "title", "date", "comment_count", "thumbnail", "content"};
    public static final String[] COMMENT_INCLUDE = {"comments"};

    public static final String GET_RECENT_POSTS_URI = "get_recent_posts/";
    public static final String GET_POST_URI = "get_post/";
    public static final String GET_NONCE = "get_nonce/";
    public static final String GET_SEARCH_RESULTS_URI = "get_search_results/";
    public static final String SUBMIT_COMMENT = "respond.submit_comment/";

    public void getRecentPosts(final AsyncHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("include", generateInclude(DEFAULT_INCLUDE));

        LzsRestClient.get(GET_RECENT_POSTS_URI, params, responseHandler);
    }

    public void getSearchResult(final String search, final AsyncHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("include", generateInclude(DEFAULT_INCLUDE));
        params.put("search", search);

        LzsRestClient.get(GET_SEARCH_RESULTS_URI, params, responseHandler);
    }

    public void getCommentsForPost(final Integer post_id, final AsyncHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("include", generateInclude(COMMENT_INCLUDE));
        params.put("post_id", post_id.toString());

        LzsRestClient.get(GET_POST_URI, params, responseHandler);
    }

    public void getNonceForPost(final AsyncHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("method", "create_post");
        params.put("controller", "posts");

        LzsRestClient.get(GET_NONCE, params, responseHandler);
    }

    public void submitComment(final Integer postId, String name, String email, String content, final AsyncHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("post_id", postId.toString());
        params.put("name", name);
        params.put("email", email);
        params.put("content", content);

        LzsRestClient.post(SUBMIT_COMMENT, params, responseHandler);
    }

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
