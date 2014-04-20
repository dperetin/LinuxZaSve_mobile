package com.linuxzasve.mobile.rest.model;

public class Category {
	private int id;
	private String slug;
	private String title;
	private String description;
	private int parent;
	private int post_count;

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

	public int getParent() {
		return parent;
	}

	public void setParent(final int parent) {
		this.parent = parent;
	}

	public int getPost_count() {
		return post_count;
	}

	public void setPost_count(final int post_count) {
		this.post_count = post_count;
	}

	public Category() {
	}
}
