package com.linuxzasve.mobile.rest;

import com.linuxzasve.mobile.rest.model.LzsRestResponse;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

interface LzsRestService {
    @GET("/get_recent_posts")
    void getRecentPosts(@Query("include") String include, Callback<LzsRestResponse> callback);

    @GET("/get_search_results")
    void getSearchResult(@Query("search") String search, @Query("include") String include, Callback<LzsRestResponse> callback);

    @GET("/get_post")
    void getCommentsForPost(@Query("post_id") Integer postId, @Query("include") String include, Callback<LzsRestResponse> callback);

    @FormUrlEncoded
    @POST("/respond.submit_comment")
    void submitComment(@Field("post_id") String postId, @Field("name") String name, @Field("email") String email, @Field("content") String content, Callback<LzsRestResponse> callback);

}
