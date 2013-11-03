package com.linuxzasve.mobile.emote;

import java.util.HashMap;

import com.linuxzasve.mobile.R;

public class EmoticonDrawables {

	private static String ROOT_URL = "http://www.linuxzasve.com/wp-includes/images/smilies/";

	private static String SMILE_IMAGE_NAME = "icon_smile.gif";
	private static String BIGGRIN_IMAGE_NAME = "icon_biggrin.gif";
	private static String SAD_IMAGE_NAME = "icon_sad.gif";
	private static String SURPRISED_IMAGE_NAME = "icon_surprised.gif";
	private static String EEK_IMAGE_NAME = "icon_eek.gif";
	private static String MAD_IMAGE_NAME = "icon_mad.gif";
	private static String RAZZ_IMAGE_NAME = "icon_razz.gif";
	private static String NEUTRAL_IMAGE_NAME = "icon_neutral.gif";
	private static String WINK_IMAGE_NAME = "icon_wink.gif";
	private static String LOL_IMAGE_NAME = "icon_lol.gif";
	private static String REDFACE_IMAGE_NAME = "icon_redface.gif";
	private static String CRY_IMAGE_NAME = "icon_cry.gif";
	private static String EVIL_IMAGE_NAME = "icon_evil.gif";
	private static String TWISTED_IMAGE_NAME = "icon_twisted.gif";
	private static String ROLLEYES_IMAGE_NAME = "icon_rolleyes.gif";
	private static String EXCLAIM_IMAGE_NAME = "icon_exclaim.gif";
	private static String QUESTION_IMAGE_NAME = "icon_question.gif";
	private static String IDEA_IMAGE_NAME = "icon_idea.gif";
	private static String ARROW_IMAGE_NAME = "icon_arrow.gif";
	private static String MRGREEN_IMAGE_NAME = "icon_mrgreen.gif";
	private static String COOL_IMAGE_NAME = "icon_cool.gif";
	private static String CONFUSED_IMAGE_NAME = "icon_confused.gif";

	private static Integer SMILE_DRAWABLE = Integer.valueOf(R.drawable.icon_smile);
	private static Integer BIGGRIN_DRAWABLE = Integer.valueOf(R.drawable.icon_biggrin);
	private static Integer SAD_DRAWABLE = Integer.valueOf(R.drawable.icon_sad);
	private static Integer SURPRISED_DRAWABLE = Integer.valueOf(R.drawable.icon_surprised);
	private static Integer EEK_DRAWABLE = Integer.valueOf(R.drawable.icon_eek);
	private static Integer MAD_DRAWABLE = Integer.valueOf(R.drawable.icon_mad);
	private static Integer RAZZ_DRAWABLE = Integer.valueOf(R.drawable.icon_razz);
	private static Integer NEUTRAL_DRAWABLE = Integer.valueOf(R.drawable.icon_neutral);
	private static Integer WINK_DRAWABLE = Integer.valueOf(R.drawable.icon_wink);
	private static Integer LOL_DRAWABLE = Integer.valueOf(R.drawable.icon_lol);
	private static Integer REDFACE_DRAWABLE = Integer.valueOf(R.drawable.icon_redface);
	private static Integer CRY_DRAWABLE = Integer.valueOf(R.drawable.icon_cry);
	private static Integer EVIL_DRAWABLE = Integer.valueOf(R.drawable.icon_evil);
	private static Integer TWISTED_DRAWABLE = Integer.valueOf(R.drawable.icon_twisted);
	private static Integer ROLLEYES_DRAWABLE = Integer.valueOf(R.drawable.icon_rolleyes);
	private static Integer EXCLAIM_DRAWABLE = Integer.valueOf(R.drawable.icon_exclaim);
	private static Integer QUESTION_DRAWABLE = Integer.valueOf(R.drawable.icon_question);
	private static Integer IDEA_DRAWABLE = Integer.valueOf(R.drawable.icon_idea);
	private static Integer ARROW_DRAWABLE = Integer.valueOf(R.drawable.icon_arrow);
	private static Integer MRGREEN_DRAWABLE = Integer.valueOf(R.drawable.icon_mrgreen);
	private static Integer COOL_DRAWABLE = Integer.valueOf(R.drawable.icon_cool);
	private static Integer CONFUSED_DRAWABLE = Integer.valueOf(R.drawable.icon_confused);

	private static HashMap<String, Integer> emoticons;

	static {
		emoticons = new HashMap<String, Integer>();

		emoticons.put(ROOT_URL + SMILE_IMAGE_NAME, SMILE_DRAWABLE);
		emoticons.put(ROOT_URL + BIGGRIN_IMAGE_NAME, BIGGRIN_DRAWABLE);
		emoticons.put(ROOT_URL + SAD_IMAGE_NAME, SAD_DRAWABLE);
		emoticons.put(ROOT_URL + SURPRISED_IMAGE_NAME, SURPRISED_DRAWABLE);
		emoticons.put(ROOT_URL + EEK_IMAGE_NAME, EEK_DRAWABLE);
		emoticons.put(ROOT_URL + MAD_IMAGE_NAME, MAD_DRAWABLE);
		emoticons.put(ROOT_URL + RAZZ_IMAGE_NAME, RAZZ_DRAWABLE);
		emoticons.put(ROOT_URL + NEUTRAL_IMAGE_NAME, NEUTRAL_DRAWABLE);
		emoticons.put(ROOT_URL + WINK_IMAGE_NAME, WINK_DRAWABLE);
		emoticons.put(ROOT_URL + LOL_IMAGE_NAME, LOL_DRAWABLE);
		emoticons.put(ROOT_URL + REDFACE_IMAGE_NAME, REDFACE_DRAWABLE);
		emoticons.put(ROOT_URL + CRY_IMAGE_NAME, CRY_DRAWABLE);
		emoticons.put(ROOT_URL + EVIL_IMAGE_NAME, EVIL_DRAWABLE);
		emoticons.put(ROOT_URL + TWISTED_IMAGE_NAME, TWISTED_DRAWABLE);
		emoticons.put(ROOT_URL + ROLLEYES_IMAGE_NAME, ROLLEYES_DRAWABLE);
		emoticons.put(ROOT_URL + EXCLAIM_IMAGE_NAME, EXCLAIM_DRAWABLE);
		emoticons.put(ROOT_URL + QUESTION_IMAGE_NAME, QUESTION_DRAWABLE);
		emoticons.put(ROOT_URL + IDEA_IMAGE_NAME, IDEA_DRAWABLE);
		emoticons.put(ROOT_URL + ARROW_IMAGE_NAME, ARROW_DRAWABLE);
		emoticons.put(ROOT_URL + MRGREEN_IMAGE_NAME, MRGREEN_DRAWABLE);
		emoticons.put(ROOT_URL + COOL_IMAGE_NAME, COOL_DRAWABLE);
		emoticons.put(ROOT_URL + CONFUSED_IMAGE_NAME, CONFUSED_DRAWABLE);
	}

	public static Integer getDrawableId(final String path) {
		if (emoticons.containsKey(path)) {
			return emoticons.get(path);
		}
		return null;
	}
}
