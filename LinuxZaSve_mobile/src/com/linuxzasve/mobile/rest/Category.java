package com.linuxzasve.mobile.rest;

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


	public void setId(int id) {
		this.id = id;
	}


	public String getSlug() {
		return slug;
	}


	public void setSlug(String slug) {
		this.slug = slug;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getParent() {
		return parent;
	}


	public void setParent(int parent) {
		this.parent = parent;
	}


	public int getPost_count() {
		return post_count;
	}


	public void setPost_count(int post_count) {
		this.post_count = post_count;
	}


	public Category() {}
}
