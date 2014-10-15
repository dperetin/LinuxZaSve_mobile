package com.linuxzasve.mobile.rest;

import com.linuxzasve.mobile.rest.model.LzsRestResponse;

import retrofit.Callback;
import retrofit.RestAdapter;

/**
 * Class holds methods for working with WordPress REST plugin.
 */
public class LzsRestGateway {

    public static final String BASE_REST_URL = "http://www.linuxzasve.com/api";

    public static final String[] DEFAULT_INCLUDE = {"author", "url", "title", "date", "comment_count", "thumbnail", "content"};
    public static final String[] COMMENT_INCLUDE = {"comments"};

    public void getRecentPosts(final Callback<LzsRestResponse> callback) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_REST_URL)
                .build();

        LzsRestService service = restAdapter.create(LzsRestService.class);

        service.getRecentPosts(generateInclude(DEFAULT_INCLUDE), callback);
    }

    public void getSearchResult(final String search, final Callback<LzsRestResponse> callback) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_REST_URL)
                .build();

        LzsRestService service = restAdapter.create(LzsRestService.class);

        service.getSearchResult(search, generateInclude(DEFAULT_INCLUDE), callback);
    }

    public void getCommentsForPost(final Integer postId, final Callback<LzsRestResponse> callback) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_REST_URL)
                .build();

        LzsRestService service = restAdapter.create(LzsRestService.class);

        service.getCommentsForPost(postId, generateInclude(COMMENT_INCLUDE), callback);
    }

    public void submitComment(final Integer postId, final String name, final String email, final String content, final Callback<LzsRestResponse> callback) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_REST_URL)
                .build();

        LzsRestService service = restAdapter.create(LzsRestService.class);

        service.submitComment(postId.toString(), name, email, content, callback);
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
