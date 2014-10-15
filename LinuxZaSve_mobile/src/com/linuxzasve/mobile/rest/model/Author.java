package com.linuxzasve.mobile.rest.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Author implements Parcelable {
    private int id;
    private String slug;
    private String name;
    private String first_name;
    private String last_name;
    private String nickname;
    private String url;
    private String description;

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

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(final String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(final String last_name) {
        this.last_name = last_name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(final String nickname) {
        this.nickname = nickname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Author() {
    }

    // parcelable

    public Author(Parcel in) {
        id = in.readInt();
        slug = in.readString();
        name = in.readString();
        first_name = in.readString();
        last_name = in.readString();
        nickname = in.readString();
        url = in.readString();
        description = in.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Author createFromParcel(Parcel in) {
            return new Author(in);
        }

        public Author[] newArray(int size) {
            return new Author[size];
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
        parcel.writeString(name);
        parcel.writeString(first_name);
        parcel.writeString(last_name);
        parcel.writeString(nickname);
        parcel.writeString(url);
        parcel.writeString(description);
    }
}
