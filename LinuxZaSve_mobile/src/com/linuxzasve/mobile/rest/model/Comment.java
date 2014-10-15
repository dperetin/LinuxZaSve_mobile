package com.linuxzasve.mobile.rest.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Comment implements Parcelable {
    private int id;
    private String name;
    private String url;
    private String date;
    private String content;
    private String parent;
    private List<Comment> children;
    private int depth;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(final String parent) {
        this.parent = parent;
    }

    public List<Comment> getChildren() {
        return children;
    }

    public void setChildren(final List<Comment> children) {
        this.children = children;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(final int depth) {
        this.depth = depth;
    }

    public Comment() {
    }

    public void addChild(final Comment comment) {
        if (children == null) {
            children = new ArrayList<Comment>();
        }

        children.add(comment);
    }


    // parcelable

    public Comment(Parcel in) {
        id = in.readInt();
        name = in.readString();
        url = in.readString();
        date = in.readString();
        content = in.readString();
        parent = in.readString();
        in.readList(children, Comment.class.getClassLoader());


    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(url);
        parcel.writeString(date);
        parcel.writeString(content);
        parcel.writeString(parent);
        parcel.writeList(children);
        parcel.writeInt(depth);
    }
}
