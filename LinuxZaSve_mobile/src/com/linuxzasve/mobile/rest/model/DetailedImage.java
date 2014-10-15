package com.linuxzasve.mobile.rest.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DetailedImage implements Parcelable {
    private String url;
    private int width;
    private int height;

    public DetailedImage() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(final int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(final int height) {
        this.height = height;
    }

    // parcelable

    public DetailedImage(Parcel in) {
        url = in.readString();
        width = in.readInt();
        height = in.readInt();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public DetailedImage createFromParcel(Parcel in) {
            return new DetailedImage(in);
        }

        public DetailedImage[] newArray(int size) {
            return new DetailedImage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(url);
        parcel.writeInt(width);
        parcel.writeInt(height);
    }
}
