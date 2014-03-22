package com.linuxzasve.mobile.rest.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.Html;


public class Post {
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
	private CustomFiels custom_fields;
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
		return ClanakRemoveHardcodedDim(content);
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

	public CustomFiels getCustom_fields() {
		return custom_fields;
	}

	public void setCustom_fields(final CustomFiels custom_fields) {
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

	private String ClanakRemoveHardcodedDim(final String clanak) {
		String filtriraniClanak = clanak;

		List<Pattern> uzorciZaIzbaciti = new ArrayList<Pattern>();

		uzorciZaIzbaciti.add(Pattern.compile("width=\"\\d+\""));
		uzorciZaIzbaciti.add(Pattern.compile("height=\"\\d+\""));
		uzorciZaIzbaciti.add(Pattern.compile("\"width: \\d+px\""));

		Iterator<Pattern> it = uzorciZaIzbaciti.iterator();
		while (it.hasNext()) {
			Pattern uzorak = it.next();
			Matcher htmlClanka = uzorak.matcher(filtriraniClanak);

			while (htmlClanka.find()) {
				int poc = htmlClanka.start();
				int kraj = htmlClanka.end();

				filtriraniClanak = filtriraniClanak.substring(0, poc) + filtriraniClanak.substring(kraj);
				htmlClanka = uzorak.matcher(filtriraniClanak);
			}

		}
		return filtriraniClanak;
	}

	public String getDate(final String format) {
		Date date;
		String result = "";
		try {

			date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(this.date);

			SimpleDateFormat date_format = new SimpleDateFormat(format);
			result = date_format.format(date);

		}
		catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}
}
