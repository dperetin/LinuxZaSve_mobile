package com.linuxzasve.mobile.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.google.gson.Gson;
import com.linuxzasve.mobile.R;
import com.linuxzasve.mobile.googl.GoogleUrlShortener;
import com.linuxzasve.mobile.googl.model.GooGlResponse;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.apache.http.Header;

import java.io.UnsupportedEncodingException;

/**
 * This fragment is responsible for fetching and displaying list of articles
 *
 * @author dperetin
 */
public class ArticleDisplayFragment extends Fragment {

  private ArticleDisplayFragmentListener articleFragmentListener;

  // bundle parameter names
  public final static String BUNDLE_POST_TITLE = "post_title";
  public final static String BUNDLE_POST_CONTENT = "post_content";
  public final static String BUNDLE_POST_ORIGINAL_LINK = "original_link";
  public final static String BUNDLE_POST_ID = "post_id";

  private String postTitle;
  private String postContent;
  private String originalLink;
  private Integer postId;

  @Override
  public void onResume() {
    super.onResume();

    articleFragmentListener.setUpArticleDisplayFragmentActionBar(postTitle);
  }

  @Override
  public void onAttach(final Activity activity) {
    super.onAttach(activity);

    try {
      articleFragmentListener = (ArticleDisplayFragmentListener) activity;
    } catch (ClassCastException e) {
      throw new ClassCastException(
          activity.toString() + " must implement ArticleDisplayFragmentListener");
    }
  }

  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setHasOptionsMenu(true);

    postTitle = getArguments().getString(BUNDLE_POST_TITLE);
    postContent = getArguments().getString(BUNDLE_POST_CONTENT);
    originalLink = getArguments().getString(BUNDLE_POST_ORIGINAL_LINK);
    postId = getArguments().getInt(BUNDLE_POST_ID);
  }

  @Override
  public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
      final Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);

    View rootView = inflater.inflate(R.layout.article_display_fragment, container, false);

    String articleWebViewContent = "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" />"
        + "<h1>"
        + postTitle
        + "</h1>"
        + postContent;

    WebView postWebView = (WebView) rootView.findViewById(R.id.article_content);
    WebSettings settings = postWebView.getSettings();
    settings.setDefaultTextEncodingName("utf-8");

    postWebView.loadDataWithBaseURL("file:///android_asset/", articleWebViewContent, "text/html", "UTF-8", null);
    return rootView;
  }

  @Override
  public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
    inflater.inflate(R.menu.article_display_menu, menu);

    super.onCreateOptionsMenu(menu, inflater);
  }

  @Override
  public boolean onOptionsItemSelected(final MenuItem item) {

    switch (item.getItemId()) {

      case android.R.id.home:
        articleFragmentListener.onArticleDisplayFragmentUpNavPressed();
        return true;

      case R.id.menu_comment_list:

        articleFragmentListener.onCommentsListButtonPressed(postId, postTitle);

        return true;

      case R.id.menu_share:
        GoogleUrlShortener.ShortenUrl(getActivity(), originalLink, new AsyncHttpResponseHandler() {

          @Override
          public void onSuccess(final int statusCode, final Header[] headers,
              final byte[] bytes) {

              String responseBody = null;

              try {
                  responseBody = new String(bytes, "UTF-8");
              } catch (UnsupportedEncodingException e) {
                  e.printStackTrace();
              }

              Gson gson = new Gson();

            String shortenedUrl = gson.fromJson(responseBody, GooGlResponse.class).getId();

            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");

            String shareTitle = "LZS | " + postTitle;

            String shareText = "Linux za sve | " + postTitle + " " + shortenedUrl;

            share.putExtra(Intent.EXTRA_TEXT, shareText);
            share.putExtra(Intent.EXTRA_SUBJECT, shareTitle);
            startActivity(Intent.createChooser(share, getResources().getString(R.string.share_article)));
          }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

        articleFragmentListener.onShareButtonPressed();

        return true;

      default:
        return super.onOptionsItemSelected(item);
    }
  }

  public interface ArticleDisplayFragmentListener {
    public void onArticleDisplayFragmentUpNavPressed();

    /**
     * All changes to action bar should be done here. That includes title and subtitle change,
     * home button behavior and visibility.
     *
     * @param title action bar title
     */
    public void setUpArticleDisplayFragmentActionBar(String title);

    public void onShareButtonPressed();

    public void onCommentsListButtonPressed(Integer postId, String postTitle);
  }
}
