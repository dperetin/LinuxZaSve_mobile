package com.linuxzasve.mobile.rest.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.Html;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Post implements Parcelable {
    private int id;
    private String type;
    private String slug;
    private String url;
    private String status;
    private String title;
    private String title_plain;
    private String content;
    private String excerpt;
    private String date;
    private String modified;
    private List<Category> categories;
    private List<Tag> tags;
    private Author author;
    private List<Comment> comments;
    private List<Attachment> attachments;
    private int comment_count;
    private String comment_status;
    private String thumbnail;
    private CustomFields custom_fields;
    private String thumbnail_size;
    private Thumbnail thumbnail_images;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(final String slug) {
        this.slug = slug;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getTitle() {
        return Html.fromHtml(title).toString();
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getTitle_plain() {
        return title_plain;
    }

    public void setTitle_plain(final String title_plain) {
        this.title_plain = title_plain;
    }

    public String getContent() {
        return removeHardcodedDimensions(content);
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(final String excerpt) {
        this.excerpt = excerpt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(final String modified) {
        this.modified = modified;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(final List<Category> categories) {
        this.categories = categories;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(final List<Tag> tags) {
        this.tags = tags;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(final Author author) {
        this.author = author;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(final List<Comment> comments) {
        this.comments = comments;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(final List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(final int comment_count) {
        this.comment_count = comment_count;
    }

    public String getComment_status() {
        return comment_status;
    }

    public void setComment_status(final String comment_status) {
        this.comment_status = comment_status;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(final String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public CustomFields getCustom_fields() {
        return custom_fields;
    }

    public void setCustom_fields(final CustomFields custom_fields) {
        this.custom_fields = custom_fields;
    }

    public String getThumbnail_size() {
        return thumbnail_size;
    }

    public void setThumbnail_size(final String thumbnail_size) {
        this.thumbnail_size = thumbnail_size;
    }

    public Thumbnail getThumbnail_images() {
        return thumbnail_images;
    }

    public void setThumbnail_images(final Thumbnail thumbnail_images) {
        this.thumbnail_images = thumbnail_images;
    }

    public Post() {
    }

    private String removeHardcodedDimensions(final String content) {
        String filteredContent = content;

        List<Pattern> patternsToRemove = new ArrayList<Pattern>();

        patternsToRemove.add(Pattern.compile("width=\"\\d+\""));
        patternsToRemove.add(Pattern.compile("height=\"\\d+\""));
        patternsToRemove.add(Pattern.compile("\"width: \\d+px\""));

        for (Pattern pattern : patternsToRemove) {
            Matcher articleHtml = pattern.matcher(filteredContent);

            while (articleHtml.find()) {
                int startIndex = articleHtml.start();
                int endIndex = articleHtml.end();

                filteredContent = filteredContent.substring(0, startIndex) + filteredContent.substring(endIndex);
                articleHtml = pattern.matcher(filteredContent);
            }

        }
        return filteredContent;
    }

    public String getDate(final String format) {
        Date date;
        String result;
        try {

            date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(this.date);

            SimpleDateFormat date_format = new SimpleDateFormat(format);
            result = date_format.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    // parcelable

    public Post(Parcel in) {
        id = in.readInt();
        type = in.readString();
        slug = in.readString();
        url = in.readString();
        status = in.readString();
        title = in.readString();
        title_plain = in.readString();
        content = in.readString();
        excerpt = in.readString();
        date = in.readString();
        modified = in.readString();
        in.readList(categories, Category.class.getClassLoader());
        in.readList(tags, Tag.class.getClassLoader());
        author = in.readParcelable(Author.class.getClassLoader());
        in.readList(comments, Comment.class.getClassLoader());
        in.readList(attachments, Attachment.class.getClassLoader());
        comment_count = in.readInt();
        comment_status = in.readString();
        thumbnail = in.readString();
        custom_fields = in.readParcelable(CustomFields.class.getClassLoader());
        thumbnail_size = in.readString();
        thumbnail_images = in.readParcelable(Thumbnail.class.getClassLoader());
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeString(type);
        parcel.writeString(slug);
        parcel.writeString(url);
        parcel.writeString(status);
        parcel.writeString(title);
        parcel.writeString(title_plain);
        parcel.writeString(content);
        parcel.writeString(excerpt);
        parcel.writeString(date);
        parcel.writeString(modified);
        parcel.writeList(categories);
        parcel.writeList(tags);
        parcel.writeParcelable(author, flags);
        parcel.writeList(comments);
        parcel.writeList(attachments);
        parcel.writeInt(comment_count);
        parcel.writeString(comment_status);
        parcel.writeString(thumbnail);
        parcel.writeParcelable(custom_fields, flags);
        parcel.writeString(thumbnail_size);
        parcel.writeParcelable(thumbnail_images, flags);
    }
}
