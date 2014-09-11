package com.linuxzasve.mobile.rest.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Tag implements Parcelable {
    private int id;
    private String slug;
    private String title;
    private String description;
    private String post_count;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(final String slug) {
        this.slug = slug;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getPost_count() {
        return post_count;
    }

    public void setPost_count(final String post_count) {
        this.post_count = post_count;
    }

    public Tag() {
    }

    // parcelable

    public Tag(Parcel in) {
        id = in.readInt();
        slug = in.readString();
        title = in.readString();
        description = in.readString();
        post_count = in.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Tag createFromParcel(Parcel in) {
            return new Tag(in);
        }

        public Tag[] newArray(int size) {
            return new Tag[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeString(slug);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(post_count);
    }
}
