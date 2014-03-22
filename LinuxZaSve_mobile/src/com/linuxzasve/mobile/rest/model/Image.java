package com.linuxzasve.mobile.rest.model;


public class Image {
	private DetailedImage full;
	private DetailedImage thumbnail;
	private DetailedImage medium;
	private DetailedImage large;
	private DetailedImage wptouch_new_thumbnail;

	public DetailedImage getFull() {
		return full;
	}

	public void setFull(final DetailedImage full) {
		this.full = full;
	}

	public DetailedImage getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(final DetailedImage thumbnail) {
		this.thumbnail = thumbnail;
	}

	public DetailedImage getMedium() {
		return medium;
	}

	public void setMedium(final DetailedImage medium) {
		this.medium = medium;
	}

	public DetailedImage getLarge() {
		return large;
	}

	public void setLarge(final DetailedImage large) {
		this.large = large;
	}

	public DetailedImage getWptouch_new_thumbnail() {
		return wptouch_new_thumbnail;
	}

	public void setWptouch_new_thumbnail(final DetailedImage wptouch_new_thumbnail) {
		this.wptouch_new_thumbnail = wptouch_new_thumbnail;
	}

	public Image() {
	};
}
