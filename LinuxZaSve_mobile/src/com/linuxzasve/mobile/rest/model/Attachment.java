package com.linuxzasve.mobile.rest.model;


public class Attachment {
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
	};
}
