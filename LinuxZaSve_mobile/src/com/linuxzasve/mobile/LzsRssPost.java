package com.linuxzasve.mobile;

/**
 * Klasa predstavlja jedan Linux za sve post dobiven iz informacija
 * dostupnih na RSS feedu.
 */
public class LzsRssPost {
	
	private String title;
	private String description;
	private String link;
	private String publishDate;
	private String creator;
	private String content;
	private String commentRss;
	private String no_comments;
	private String origLink;
	/**
	 * Defaultni konstruktor
	 */
	public LzsRssPost(){
		
	}
	
	/**
	 * Konstruktor
	 * 
	 * @param title Naslov clanka
	 * @param description Opis clanka
	 * @param link URL feeda clanka
	 * @param publishDate Datum objave
	 * @param creator Osoba koja je objavila clanak
	 * @param content Sadrzaj clanka
	 * @param commentRss URL feeda komentara
	 * @param no_comments Broj komentara
	 */
	public LzsRssPost(String title,
					  String description,
					  String link,
					  String publishDate,
					  String creator,
					  String content,
					  String commentRss,
					  String no_comments,
					  String origLink
					  ){
		
		this.title = title;
		this.description = description;
		this.link = link;
		this.publishDate = publishDate;
		this.creator = creator;
		this.content = content;
		this.commentRss = commentRss;
		this.no_comments = no_comments;
		this.origLink = origLink;
	}
	
	/**
	 * Funkcija vraca string koji sadrzi kratak opis clanka
	 * 
	 * @return kratak opis clanka
	 */
	public String getDescription(){
		return description;
	}
	
	/**
	 * Funkcija vraca URL feeda clanka.
	 * @return URL feeda clanka
	 */
	public String getLink(){
		return link;
	}
	
	/**
	 * Funkcija vraca string koji sadrzi datum objave kako je zapisano u feed
	 * 
	 * @return datum objave u originalnom obliku
	 */
	public String getPublishDate(){
		return publishDate;
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
	 * Funkcija koja vraca naslov clanka
	 * 
	 * @return naslov clanka
	 */
	public String getTitle(){
		return title;
	}
	
	/**
	 * Funkcija koja vraca ime osobe koja je objavila clanak
	 * 
	 * @return ime osobe koja je objavila clanak
	 */
	public String getCreator(){
		return creator;
	}
	
	/**
	 * Funkcija koja vraca sadrzaj clanka
	 * 
	 * @return sadrzaj clanka
	 */
	public String getContent(){
		return content;
	}
	
	/**
	 * Funkcija koja vraca URL feeda komentara
	 * 
	 * @return URL feeda komentara
	 */
	public String getCommentRss(){
		return commentRss;
	}
	
	/**
	 * Funkcija koja vraca broj komentara na dani clanak
	 * 
	 * @return broj komentara na dani clanak
	 */
	public String getNoComments(){
		return no_comments;
	}
	
	/**
	 * Funkcija vraca originalni URL do clanka
	 * 
	 * @return originalni URL do clanka
	 */
	public String getOrigLink(){
		return origLink;
	}
}
