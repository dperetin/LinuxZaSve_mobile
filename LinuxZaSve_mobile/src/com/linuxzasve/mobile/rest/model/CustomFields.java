package com.linuxzasve.mobile.rest.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class CustomFields implements Parcelable {
    private List<String> views;

    public List<String> getViews() {
        return views;
    }

    public void setViews(final List<String> views) {
        this.views = views;
    }

    public CustomFields() {
    }

    // parcelable

    public CustomFields(Parcel in) {
        in.readStringList(views);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public CustomFields createFromParcel(Parcel in) {
            return new CustomFields(in);
        }

        public CustomFields[] newArray(int size) {
            return new CustomFields[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeStringList(views);
    }
}
