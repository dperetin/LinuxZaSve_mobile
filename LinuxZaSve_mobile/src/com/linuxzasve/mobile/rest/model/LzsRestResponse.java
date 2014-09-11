package com.linuxzasve.mobile.rest.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class LzsRestResponse implements Parcelable {
    private String status;
    private int count;
    private int count_total;
    private int pages;
    private ArrayList<Post> posts;
    private Post post;
    private String nonce;

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(final int count) {
        this.count = count;
    }

    public int getCount_total() {
        return count_total;
    }

    public void setCount_total(final int count_total) {
        this.count_total = count_total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(final int pages) {
        this.pages = pages;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(final ArrayList<Post> posts) {
        this.posts = posts;
    }


    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    LzsRestResponse() {
    }

    // parcelable

    public LzsRestResponse(Parcel in) {
        status = in.readString();
        count = in.readInt();
        count_total = in.readInt();
        pages = in.readInt();
        in.readList(posts, Post.class.getClassLoader());
        post = in.readParcelable(Post.class.getClassLoader());
        nonce = in.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public LzsRestResponse createFromParcel(Parcel in) {
            return new LzsRestResponse(in);
        }

        public LzsRestResponse[] newArray(int size) {
            return new LzsRestResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(status);
        parcel.writeInt(count);
        parcel.writeInt(count_total);
        parcel.writeInt(pages);
        parcel.writeList(posts);
        parcel.writeParcelable(post, flags);
        parcel.writeString(nonce);
    }

}
