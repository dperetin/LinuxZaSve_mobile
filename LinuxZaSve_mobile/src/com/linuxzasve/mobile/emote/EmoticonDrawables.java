package com.linuxzasve.mobile.emote;

import com.linuxzasve.mobile.R;

import java.util.HashMap;

public class EmoticonDrawables {

    /* root of the path that will be found in comment content */
    private static final String ROOT_URL = "http://www.linuxzasve.com/wp-includes/images/smilies/";

    /* emoticon gifs that will be found in comment content */
    private static final String SMILE_IMAGE_NAME = "icon_smile.gif";
    private static final String BIGGRIN_IMAGE_NAME = "icon_biggrin.gif";
    private static final String SAD_IMAGE_NAME = "icon_sad.gif";
    private static final String SURPRISED_IMAGE_NAME = "icon_surprised.gif";
    private static final String EEK_IMAGE_NAME = "icon_eek.gif";
    private static final String MAD_IMAGE_NAME = "icon_mad.gif";
    private static final String RAZZ_IMAGE_NAME = "icon_razz.gif";
    private static final String NEUTRAL_IMAGE_NAME = "icon_neutral.gif";
    private static final String WINK_IMAGE_NAME = "icon_wink.gif";
    private static final String LOL_IMAGE_NAME = "icon_lol.gif";
    private static final String CRY_IMAGE_NAME = "icon_cry.gif";
    private static final String EVIL_IMAGE_NAME = "icon_evil.gif";
    private static final String TWISTED_IMAGE_NAME = "icon_twisted.gif";
    private static final String ROLLEYES_IMAGE_NAME = "icon_rolleyes.gif";
    private static final String EXCLAIM_IMAGE_NAME = "icon_exclaim.gif";
    private static final String QUESTION_IMAGE_NAME = "icon_question.gif";
    private static final String IDEA_IMAGE_NAME = "icon_idea.gif";
    private static final String ARROW_IMAGE_NAME = "icon_arrow.gif";
    private static final String MRGREEN_IMAGE_NAME = "icon_mrgreen.gif";
    private static final String COOL_IMAGE_NAME = "icon_cool.gif";
    private static final String CONFUSED_IMAGE_NAME = "icon_confused.gif";

    private static final Integer SMILE_DRAWABLE = R.drawable.smiley;
    private static final Integer BIGGRIN_DRAWABLE = R.drawable.smiley;
    private static final Integer SAD_DRAWABLE = R.drawable.sad;
    private static final Integer SURPRISED_DRAWABLE = R.drawable.shocked;
    private static final Integer EEK_DRAWABLE = R.drawable.shocked;
    private static final Integer MAD_DRAWABLE = R.drawable.angry;
    private static final Integer RAZZ_DRAWABLE = R.drawable.tongue;
    private static final Integer NEUTRAL_DRAWABLE = R.drawable.neutral;
    private static final Integer WINK_DRAWABLE = R.drawable.wink;
    private static final Integer LOL_DRAWABLE = R.drawable.happy;
    private static final Integer CRY_DRAWABLE = R.drawable.sad;
    private static final Integer EVIL_DRAWABLE = R.drawable.evil;
    private static final Integer TWISTED_DRAWABLE = R.drawable.evil;
    private static final Integer ROLLEYES_DRAWABLE = R.drawable.wondering;
    private static final Integer EXCLAIM_DRAWABLE = R.drawable.notification;
    private static final Integer QUESTION_DRAWABLE = R.drawable.question;
    private static final Integer IDEA_DRAWABLE = R.drawable.info;
    private static final Integer ARROW_DRAWABLE = R.drawable.arrow_right;
    private static final Integer MRGREEN_DRAWABLE = R.drawable.grin;
    private static final Integer COOL_DRAWABLE = R.drawable.cool;
    private static final Integer CONFUSED_DRAWABLE = R.drawable.confused;

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
