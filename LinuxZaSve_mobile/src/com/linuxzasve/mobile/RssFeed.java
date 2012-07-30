package com.linuxzasve.mobile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Dohvaca Linux za sve RSS feed, generira i odrzava listu postova.
 *
 */
public class RssFeed {
	
	private List<LzsRssPost> listaPostova;
	
	/**
	 * Funkcija vraca listu postova
	 * 
	 * @return listu postova
	 */
	public List<LzsRssPost> getPosts(){
		return listaPostova;
	}
	
	/**
	 * Funkcija za dani URL rss feeda dohvaca taj feed i generira listu
	 * koja sadrzi sve postove u tom feedu
	 * 
	 * @param rss_feed URL rss feeda
	 */
	public  RssFeed (String rss_feed){
		try {
			URL linuxZaSveRssFeed = new URL(rss_feed);
			InputStream in = linuxZaSveRssFeed.openStream();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
			listaPostova = new ArrayList<LzsRssPost>();

			DocumentBuilder db = dbf.newDocumentBuilder();

			Document dom = db.parse(in);
		
			org.w3c.dom.Element docEle = dom.getDocumentElement();
				
			NodeList nl = docEle.getElementsByTagName("item");
			if(nl != null && nl.getLength() > 0) {
				for(int i = 0 ; i < nl.getLength();i++) {
					org.w3c.dom.Element el = (org.w3c.dom.Element) nl.item(i);

					LzsRssPost e = getRssItem(el);

					listaPostova.add(e);
				}
			}
		}
		catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}
		catch(SAXException se) {
			se.printStackTrace();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	/**
	 * Funkcija u danom XML elementu trazi vrijednost za dani tag.
	 * @param element
	 * @param tagName
	 * 
	 * @return vraca vrijednost taga u zadanom elemntu
	 */
	public static String getTextValue(org.w3c.dom.Element element, String tag) {
		String textVal = null;
		NodeList nl = (element).getElementsByTagName(tag);
		if(nl != null && nl.getLength() > 0) {
			org.w3c.dom.Element el = (org.w3c.dom.Element)nl.item(0);
			textVal = ((Node) el).getFirstChild().getNodeValue();
		}

		return textVal;
	}
	
	/**
	 * Iz danog XML elementa kreira linux za sve rss post.
	 * 
	 * @param element
	 * @return vraca linux za sve rss post
	 */
	public static LzsRssPost getRssItem(org.w3c.dom.Element element) {
		String title = getTextValue(element,"title");
		String desc = getTextValue(element,"description");
		String date = getTextValue(element,"pubDate");
		String link = getTextValue(element,"link");
		
		String creator = getTextValue(element,"dc:creator");
		String content = getTextValue(element,"content:encoded");
		String commentRss = getTextValue(element,"wfw:commentRss");
		String no_comments = getTextValue(element, "slash:comments");
		String origLink = getTextValue(element, "feedburner:origLink");
		
		LzsRssPost e = new LzsRssPost(title, desc, link, date, creator, content,
				commentRss, no_comments, origLink);

		return e;
	}
	
	/**
	 * Funkcija vraca listu koja sadrzi naslove svih clanaka u feedu.
	 * 
	 * @return listu koja sadrzi naslove svih clanaka u feedu
	 */
	public List<String> getTitleList(){
		List<String> naslovi = new ArrayList<String>();
		
		Iterator<LzsRssPost> it=listaPostova.iterator();
		while(it.hasNext()){	
			LzsRssPost value=(LzsRssPost)it.next();
			naslovi.add(value.getTitle());
		}
		
		return naslovi;
	}
	
	/**
	 * Funkcija vraca listu koja sadrzi datume objave svih clanaka u feedu.
	 * 
	 * @return listu koja sadrzi datume objave svih clanaka u feedu
	 */
	public List<String> getDateList(){
		List<String> naslovi = new ArrayList<String>();
		
		Iterator<LzsRssPost> it=listaPostova.iterator();
		while(it.hasNext()){
			LzsRssPost value=(LzsRssPost)it.next();
			naslovi.add(value.getPublishDate());
		}
		
		return naslovi;
	}
}
