package com.linuxzasve.mobile.rest.model;

import java.util.List;


public class LzsRestResponse {
	private String status;
	private int count;
	private int count_total;
	private int pages;
	private List<Post> posts;
	private Post post;
	private String nonce;

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public int getCount() {
		return count;
	}

	public void setCount(final int count) {
		this.count = count;
	}

	public int getCount_total() {
		return count_total;
	}

	public void setCount_total(final int count_total) {
		this.count_total = count_total;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(final int pages) {
		this.pages = pages;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(final List<Post> posts) {
		this.posts = posts;
	}

	
	
	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	LzsRestResponse() {
	}
	

}
