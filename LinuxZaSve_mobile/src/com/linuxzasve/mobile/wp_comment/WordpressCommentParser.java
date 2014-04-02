package com.linuxzasve.mobile.wp_comment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WordpressCommentParser {
	private final String pageUrl;
	// private String fullPageContent = "";

	private final List<Komentar> listaKomentara;

	private static final String AUTHOR_PATTERN = "<cite class=\"fn\">(.*)</cite>";
	private static final String AUTHOR_WITH_URL_PATTERN = "rel=\"external nofollow\" class=\"url\">(.*?)</a>";
	private static final String THUMBNAIL_PATTERN = "src=\"(.*?)\"";
	private static final String PUBLISHED_TIME_PATTERN = ">(.*)</a>";
	private static final String COMMENT_POST_ID_PATTERN = "<input type=\"hidden\" name=\"comment_post_ID\" value=\"(.*?)\" />";

	private static final String AKSIMET_PATTERN = "<input type=\"hidden\" id=\"akismet_comment_nonce\" name=\"akismet_comment_nonce\" value=\"(.*?)\" />";

	public WordpressCommentParser(final String url) throws IOException {
		listaKomentara = new ArrayList<Komentar>();
		this.pageUrl = url;

		Document doc = Jsoup.connect(pageUrl).get();

		Elements komentari = doc.select(".comment-body");

		String akismet = parseInput(AKSIMET_PATTERN, doc.select("#commentform").toString());
		String postId = parseInput(COMMENT_POST_ID_PATTERN, doc.select("#commentform").toString());

		if (komentari.size() == 0) {
			Komentar komentar = new Komentar();
			komentar.setCreator("");
			komentar.setThumbnailUrl("");
			komentar.setPublishDate("");
			komentar.setContent("Još nitko nije komentirao ovaj članak.");
			komentar.setAkismetCommentNounce(akismet);
			komentar.setCommentPostId(postId);
			listaKomentara.add(0, komentar);
		}

		for (Element e : komentari) {
			Komentar komentar = new Komentar();
			komentar.setCreator(parseInput(AUTHOR_PATTERN, e.select(".comment-author").toString()).contains("</a>")? parseInput(AUTHOR_WITH_URL_PATTERN, e.select(".comment-author").toString()) : parseInput(AUTHOR_PATTERN, e.select(".comment-author")
					.toString()));
			komentar.setThumbnailUrl(parseInput(THUMBNAIL_PATTERN, e.select(".comment-author").toString()));
			komentar.setPublishDate(parseInput(PUBLISHED_TIME_PATTERN, e.select(".comment-meta").toString()));
			String textKomentara = e.select("p").toString();
			komentar.setContent(textKomentara.substring(3, textKomentara.length() - 4));

			komentar.setAkismetCommentNounce(akismet);
			komentar.setCommentPostId(postId);

			listaKomentara.add(0, komentar);
		}
	}

	/**
	 * Funkcija vraca listu postova
	 *
	 * @return listu postova
	 */
	public List<Komentar> getPosts() {
		return listaKomentara;
	}

	public String parseInput(final String pattern, final String input) {
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(input);
		if (m.find()) {
			return m.group(1);
		}
		return "";
	}
}
