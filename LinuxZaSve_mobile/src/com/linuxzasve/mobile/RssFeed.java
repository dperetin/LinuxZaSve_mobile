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

public class RssFeed {
	
	List<LzsRssPost> lista_postova;
	
	public List<LzsRssPost> getPosts()
	{
		return lista_postova;
	}
	
	public  RssFeed (String rss_stream_url)
	{
		try {
			URL oracle = new URL(rss_stream_url);
			InputStream in = oracle.openStream();
        	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
        	List<LzsRssPost> myEmpls=new ArrayList<LzsRssPost>();

			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			//parse using builder to get DOM representation of the XML file
			Document dom = db.parse(in);
		
			//get the root element
			org.w3c.dom.Element docEle = dom.getDocumentElement();
				
			NodeList nl = docEle.getElementsByTagName("item");
			if(nl != null && nl.getLength() > 0) {
				for(int i = 0 ; i < nl.getLength();i++) {

					//get the employee element
					org.w3c.dom.Element el = (org.w3c.dom.Element) nl.item(i);

					//get the Employee object
					LzsRssPost e = getRssItem(el);

					//add it to list
					myEmpls.add(e);
				}
			}
			
			this.lista_postova = myEmpls;
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
		
		

	};
	
	public static String getTextValue(org.w3c.dom.Element el2, String tagName) {
		String textVal = null;
		NodeList nl = (el2).getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			org.w3c.dom.Element el = (org.w3c.dom.Element)nl.item(0);
			textVal = ((Node) el).getFirstChild().getNodeValue();
		}

		return textVal;
	}
	
	public static LzsRssPost getRssItem(org.w3c.dom.Element el) {

		//for each <employee> element get text or int values of
		//name ,id, age and name
		String title = getTextValue(el,"title");
		String desc = getTextValue(el,"description");
		String date = getTextValue(el,"pubDate");
		String link = getTextValue(el,"link");
		
		String creator = getTextValue(el,"dc:creator");
		String content = getTextValue(el,"content:encoded");
		String commentRss = getTextValue(el,"wfw:commentRss");
		String no_comments = getTextValue(el,"slash:comments");
		String origLink = getTextValue(el,"feedburner:origLink");
		String thumb_url = getTextValue(el,"thumb_url");


		
		LzsRssPost e = new LzsRssPost(title, desc, link, date, creator, content, commentRss, no_comments, origLink, thumb_url);

		return e;
	}
	
	public List<String> getTitleList(){
		List<String> naslovi = new ArrayList<String>();
		
		Iterator<LzsRssPost> it=lista_postova.iterator();
		while(it.hasNext())
        {	
			LzsRssPost value=(LzsRssPost)it.next();
			//naslovi.add(value.getPublishDate().substring(0, 16) + " " + value.getCreator() + "\n" + value.getTitle() + '\n' + value.getThumbUrl());
			naslovi.add(value.getTitle());
        }
		
		return naslovi;
	}
	
	public List<String> getDateList(){
		List<String> naslovi = new ArrayList<String>();
		
		Iterator<LzsRssPost> it=lista_postova.iterator();
		while(it.hasNext())
        {	
			LzsRssPost value=(LzsRssPost)it.next();
			//naslovi.add(value.getPublishDate().substring(0, 16) + " " + value.getCreator() + "\n" + value.getTitle() + '\n' + value.getThumbUrl());
			naslovi.add(value.getPublishDate());
        }
		
		return naslovi;
	}
	
	public String getContentByTitle(String title){
		Iterator<LzsRssPost> it=lista_postova.iterator();
		while(it.hasNext())
        {	
			LzsRssPost value=(LzsRssPost)it.next();
			if (value.getTitle().equals(title)) {
				return value.getContent();
			}
        }
		return "";
	}
	
	public String getCommentsByTitle(String title){
		Iterator<LzsRssPost> it=lista_postova.iterator();
		while(it.hasNext())
        {	
			LzsRssPost value=(LzsRssPost)it.next();
			if (value.getTitle().equals(title)) {
				return value.getCommentRss();
			}
        }
		return "";
	}
}


