package com.linuxzasve.mobile.rest.model;

public class Comment {
	private int id;
	private String name;
	private String url;
	private String date;
	private String content;
	private String parent;

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

	public Comment() {
	};
}
