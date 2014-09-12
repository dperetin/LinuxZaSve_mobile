package com.linuxzasve.mobile.rest.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Thumbnail implements Parcelable {
    private DetailedImage full;
    private DetailedImage thumbnail;
    private DetailedImage medium;
    private DetailedImage large;
    private DetailedImage wptouch_new_thumbnail;

    public DetailedImage getFull() {
        return full;
    }

    public void setFull(final DetailedImage full) {
        this.full = full;
    }

    public DetailedImage getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(final DetailedImage thumbnail) {
        this.thumbnail = thumbnail;
    }

    public DetailedImage getMedium() {
        return medium;
    }

    public void setMedium(final DetailedImage medium) {
        this.medium = medium;
    }

    public DetailedImage getLarge() {
        return large;
    }

    public void setLarge(final DetailedImage large) {
        this.large = large;
    }

    public DetailedImage getWptouch_new_thumbnail() {
        return wptouch_new_thumbnail;
    }

    public void setWptouch_new_thumbnail(final DetailedImage wptouch_new_thumbnail) {
        this.wptouch_new_thumbnail = wptouch_new_thumbnail;
    }

    public Thumbnail() {
    }

    // parcelable

    public Thumbnail(Parcel in) {
        full = in.readParcelable(DetailedImage.class.getClassLoader());
        thumbnail = in.readParcelable(DetailedImage.class.getClassLoader());
        medium = in.readParcelable(DetailedImage.class.getClassLoader());
        large = in.readParcelable(DetailedImage.class.getClassLoader());
        wptouch_new_thumbnail = in.readParcelable(DetailedImage.class.getClassLoader());
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Thumbnail createFromParcel(Parcel in) {
            return new Thumbnail(in);
        }

        public Thumbnail[] newArray(int size) {
            return new Thumbnail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeParcelable(full, flags);
        parcel.writeParcelable(thumbnail, flags);
        parcel.writeParcelable(medium, flags);
        parcel.writeParcelable(large, flags);
        parcel.writeParcelable(wptouch_new_thumbnail, flags);
    }
}
