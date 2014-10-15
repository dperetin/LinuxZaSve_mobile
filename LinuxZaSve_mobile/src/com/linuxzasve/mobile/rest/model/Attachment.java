package com.linuxzasve.mobile.rest.model;


import android.os.Parcel;
import android.os.Parcelable;

public class Attachment implements Parcelable {
    private int id;
    private String url;
    private String slug;
    private String title;
    private String description;
    private String caption;
    private int parent;
    private String mime_type;
    private Image images;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
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

    public String getCaption() {
        return caption;
    }

    public void setCaption(final String caption) {
        this.caption = caption;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(final int parent) {
        this.parent = parent;
    }

    public String getMime_type() {
        return mime_type;
    }

    public void setMime_type(final String mime_type) {
        this.mime_type = mime_type;
    }

    public Image getImages() {
        return images;
    }

    public void setImages(final Image images) {
        this.images = images;
    }

    public Attachment() {
    }

    // parcelable

    public Attachment(Parcel in) {
        id = in.readInt();
        url = in.readString();
        slug = in.readString();
        title = in.readString();
        description = in.readString();
        caption = in.readString();
        parent = in.readInt();
        mime_type = in.readString();
        images = in.readParcelable(Image.class.getClassLoader());
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Attachment createFromParcel(Parcel in) {
            return new Attachment(in);
        }

        public Attachment[] newArray(int size) {
            return new Attachment[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeString(url);
        parcel.writeString(slug);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(caption);
        parcel.writeInt(parent);
        parcel.writeString(mime_type);
        parcel.writeParcelable(images, flags);
    }
}
