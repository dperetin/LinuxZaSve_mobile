package com.linuxzasve.mobile.rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


	public void setId(int id) {
		this.id = id;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getSlug() {
		return slug;
	}


	public void setSlug(String slug) {
		this.slug = slug;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getTitle_plain() {
		return title_plain;
	}


	public void setTitle_plain(String title_plain) {
		this.title_plain = title_plain;
	}


	public String getContent() {
		return ClanakRemoveHardcodedDim(content);
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getExcerpt() {
		return excerpt;
	}


	public void setExcerpt(String excerpt) {
		this.excerpt = excerpt;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public String getModified() {
		return modified;
	}


	public void setModified(String modified) {
		this.modified = modified;
	}


	public List<Category> getCategories() {
		return categories;
	}


	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}


	public List<Tag> getTags() {
		return tags;
	}


	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}


	public Author getAuthor() {
		return author;
	}


	public void setAuthor(Author author) {
		this.author = author;
	}


	public List<Comment> getComments() {
		return comments;
	}


	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}


	public List<Attachment> getAttachments() {
		return attachments;
	}


	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}


	public int getComment_count() {
		return comment_count;
	}


	public void setComment_count(int comment_count) {
		this.comment_count = comment_count;
	}


	public String getComment_status() {
		return comment_status;
	}


	public void setComment_status(String comment_status) {
		this.comment_status = comment_status;
	}


	public String getThumbnail() {
		return thumbnail;
	}


	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}


	public CustomFiels getCustom_fields() {
		return custom_fields;
	}


	public void setCustom_fields(CustomFiels custom_fields) {
		this.custom_fields = custom_fields;
	}


	public String getThumbnail_size() {
		return thumbnail_size;
	}


	public void setThumbnail_size(String thumbnail_size) {
		this.thumbnail_size = thumbnail_size;
	}


	public Thumbnail getThumbnail_images() {
		return thumbnail_images;
	}


	public void setThumbnail_images(Thumbnail thumbnail_images) {
		this.thumbnail_images = thumbnail_images;
	}


	public Post() {}
	
	private String ClanakRemoveHardcodedDim(String clanak) {
		String filtriraniClanak = clanak;
		
		List<Pattern> uzorciZaIzbaciti = new ArrayList<Pattern>();
		
		uzorciZaIzbaciti.add(Pattern.compile("width=\"\\d+\""));
		uzorciZaIzbaciti.add(Pattern.compile("height=\"\\d+\""));
		uzorciZaIzbaciti.add(Pattern.compile("\"width: \\d+px\""));
		
		Iterator<Pattern> it = uzorciZaIzbaciti.iterator();
		while(it.hasNext()){
			Pattern uzorak = it.next();
			Matcher htmlClanka = uzorak.matcher(filtriraniClanak);
			
			while (htmlClanka.find()) {
				int poc = htmlClanka.start();
				int kraj = htmlClanka.end();

				filtriraniClanak = filtriraniClanak.substring(0, poc) + 
						filtriraniClanak.substring(kraj);
				htmlClanka = uzorak.matcher(filtriraniClanak);
			}
			
		}
		return filtriraniClanak;
	}
}
