package com.linuxzasve.mobile;

public class LzsRssPost {
	
	private String title;
	private String description;
	private String link;
	private String publishDate;
	private String creator;
	private String content;
	private String commentRss;
	private String origLink;
	private String no_comments;
	private String thumb_url;
	
	public LzsRssPost(){
		
	}
	
	/* konstruktor */
	public LzsRssPost(String title,    			// naslov
					  String description,       // opis
					  String link,              // url feeda clanka
					  String publishDate,       // datum objave
					  String creator,	        // objavio
					  String content,           // sadrzaj
					  String commentRss,        // feed komentara
					  String no_comments,       // broj komentara
					  String origLink,          // originalni url
					  String thumb_url
					  ){
		
		this.title = title;
		this.description = description;
		this.link = link;
		this.publishDate = publishDate;
		this.creator = creator;
		this.content = "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" />" + content;
		this.commentRss = commentRss;
		this.no_comments = no_comments;
		this.origLink = origLink;
		this.thumb_url = thumb_url;
		
		
	}
	
	public String getDescription(){
		return description;
	}
	
	public String getLink(){
		return link;
	}
	
	public String getPublishDate(){
		return publishDate;
	}
	
	public String hrvatskiDatum(){
		
		String mjesec = publishDate.substring(8, 11);
		
		String idx = "0";
		
		if (mjesec.equals("Jan")) idx = "01"; 
		if (mjesec.equals("Feb")) idx = "02"; 
		if (mjesec.equals("Mar"))idx = "03"; 
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
	
	public String getTitle(){
		return title;
	}
	
	public String getCreator(){
		return creator;
	}
	
	public String getContent(){
		return content;
	}
	
	public String getCommentRss(){
		return commentRss;
	}
	
	public String getNoComments(){
		return no_comments;
	}
	
	public String getOrigLink(){
		return origLink;
	}
	public String getThumbUrl(){
		return thumb_url;
	}
}



