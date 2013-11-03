package com.linuxzasve.mobile.wp_comment;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spannable;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.util.Log;

/**
 * Klasa predstavlja jedan Linux za sve post dobiven iz informacija
 * dostupnih na RSS feedu.
 */
public class Komentar {
	
//	private String title;
//	private String description;
//	private String link;
	private String publishDate;
	private String creator;
	private String content;
//	private String commentRss;
//	private String no_comments;
//	private String origLink;
	private String thumbnail;
	private String akismetCommentNonce;
	private String commentPostId;
	
	/**
	 * Defaultni konstruktor
	 */
	public Komentar(){
		
	}
	
	/**
	 * Funkcija vraca string koji sadrzi datum objave kako je zapisano u feed
	 * 
	 * @return datum objave u originalnom obliku
	 */
	public String getPublishDate(){
		return publishDate;
	}
	
	public void setPublishDate(String publishDate){
		this.publishDate = publishDate;
	}
	
	public void setCommentPostId(String id) {
		commentPostId = id;
	}
	
	public String getCommentPostId() {
		return commentPostId;
	}
	
	public void setAkismetCommentNounce(String id) {
		akismetCommentNonce = id;
	}
	
	public String getAkismetCommentNounce() {
		return akismetCommentNonce;
	}
	
	/**
	 * Funkcija vraca datum objave u obliku dd.mm.yyyy (npr. 25.12.2012)
	 * 
	 * @return datum objave u obliku dd.mm.yyyy (npr. 25.12.2012)
	 */
	public String datumDdmmyyy(){
		
		String mjesec = publishDate.substring(8, 11);
		
		String idx = "0";
		
		if (mjesec.equals("Jan")) idx = "01"; 
		if (mjesec.equals("Feb")) idx = "02"; 
		if (mjesec.equals("Mar")) idx = "03"; 
		if (mjesec.equals("Apr")) idx = "04"; 
		if (mjesec.equals("May")) idx = "05"; 
		if (mjesec.equals("Jun")) idx = "06"; 
		if (mjesec.equals("Jul")) idx = "07"; 
		if (mjesec.equals("Aug")) idx = "08"; 
		if (mjesec.equals("Sep")) idx = "09"; 
		if (mjesec.equals("Oct")) idx = "10"; 
		if (mjesec.equals("Nov")) idx = "11"; 
		if (mjesec.equals("Dec")) idx = "12";
		
		return  publishDate.substring(5, 7) + "." +
				idx + "." +
				publishDate.substring(12, 16);
	}
	
	/**
	 * Funkcija koja vraca ime osobe koja je objavila clanak
	 * 
	 * @return ime osobe koja je objavila clanak
	 */
	public String getCreator(){
		return creator;
	}
	
	
	public void setCreator(String creator){
		this.creator = creator;
	}
	
	/**
	 * Funkcija koja vraca sadrzaj clanka
	 * 
	 * @return sadrzaj clanka
	 */
	public String getContent(){
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public void setThumbnailUrl(String url) {
		this.thumbnail = url;
	}

	public String getThumbnail() {
		return this.thumbnail;
	}
}
