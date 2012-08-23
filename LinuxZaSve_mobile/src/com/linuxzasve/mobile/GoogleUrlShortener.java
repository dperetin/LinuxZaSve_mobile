package com.linuxzasve.mobile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Skracuje url koristeci Google URL shortener (goo.gl)
 *
 */
public class GoogleUrlShortener {
	
	/**
	 * Funkcija skracuje url dugiUrl
	 *
	 * @return skraceni URL, oblika http://goo.gl/...
	 */
	public static String ShortenUrl(String dugiUrl) {

		// Saljem POST na https://www.googleapis.com/urlshortener/v1/url
		String postUrl = "https://www.googleapis.com/urlshortener/v1/url";
		
		// string koji treba skratiti se salje kao JSON, npr:
		// {"longUrl": "http://www.google.com/"}
		String jsonLongUrl = "{\"longUrl\":\"" + dugiUrl + "\"}";

		String skraceniUrl = "";

		try
		{
			URLConnection conn = new URL(postUrl).openConnection();
			conn.setDoOutput(true);

			// Content-Type: application/json
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(jsonLongUrl);
			wr.flush();

			BufferedReader rd =  new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;

			while ((line = rd.readLine()) != null) {
				// "id": "http://goo.gl/fbsS",
				Pattern p = Pattern.compile("id\": \"(.*)\",");
				Matcher m = p.matcher(line);
				if (m.find()) {
					skraceniUrl = m.group(1);
					break;
				}
			}

			wr.close();
			rd.close();
		}
		catch (MalformedURLException ex)
		{

		}
		catch (IOException ex)
		{

		}

		return skraceniUrl;
	}
}
