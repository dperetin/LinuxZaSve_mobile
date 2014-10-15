package com.linuxzasve.mobile.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.linuxzasve.mobile.R;
import com.linuxzasve.mobile.googl.GoogleUrlShortener;
import com.linuxzasve.mobile.googl.model.GooGlResponse;
import com.linuxzasve.mobile.rest.model.Post;
import com.linuxzasve.mobile.timthumb.TimThumb;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * This fragment is responsible for fetching and displaying list of articles
 *
 * @author dperetin
 */
public class ArticleDisplayFragment extends Fragment {

    private ArticleDisplayFragmentListener articleFragmentListener;

    // bundle parameter names
    public final static String BUNDLE_POST = "post";

    private Post post;
    private ImageView thumbnail;
    private TextView articleTitle;
    private TextView articleAuthor;
    private TextView articlePublishDate;
    private Context context;

    @Override
    public void onResume() {
        super.onResume();

        articleFragmentListener.setUpArticleDisplayFragmentActionBar(post.getTitle());
    }

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);

        context = activity;

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

        post = getArguments().getParcelable(BUNDLE_POST);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        View rootView = inflater.inflate(R.layout.article_display_fragment, container, false);

        String articleWebViewContent = post.getContent();

        WebView postWebView = (WebView) rootView.findViewById(R.id.article_content);
        WebSettings settings = postWebView.getSettings();
        settings.setDefaultTextEncodingName("utf-8");
        settings.setJavaScriptEnabled(true);

        postWebView.loadDataWithBaseURL("file:///android_asset/", articleWebViewContent, "text/html", "UTF-8", null);

        thumbnail = (ImageView) rootView.findViewById(R.id.thumbnail);

        if (post.getThumbnail_images() != null) {
            String thumbnailUrl = TimThumb.generateTimThumbUrl(post.getThumbnail_images().getFull().getUrl(), 320, 512, 1);
            UrlImageViewHelper.setUrlDrawable(thumbnail, thumbnailUrl);
        }

        articleTitle = (TextView) rootView.findViewById(R.id.article_title);
        articleTitle.setText(post.getTitle());

        articleAuthor = (TextView) rootView.findViewById(R.id.article_author);
        articleAuthor.setText(post.getAuthor().getName());

        articlePublishDate = (TextView) rootView.findViewById(R.id.article_publish_date);
        articlePublishDate.setText(post.getDate("dd.MM.yyyy"));

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

                articleFragmentListener.onCommentsListButtonPressed(post);

                return true;

            case R.id.menu_share:

                GoogleUrlShortener shortener = new GoogleUrlShortener();

                shortener.shortenUrl(post.getUrl(), new Callback<GooGlResponse>() {
                    @Override
                    public void success(GooGlResponse gooGlResponse, Response response) {


                            String shortenedUrl = gooGlResponse.getId();

                            Intent share = new Intent(Intent.ACTION_SEND);
                            share.setType("text/plain");

                            String shareTitle = "LZS | " + post.getTitle();

                            String shareText = "Linux za sve | " + post.getTitle() + " " + shortenedUrl;

                            share.putExtra(Intent.EXTRA_TEXT, shareText);
                            share.putExtra(Intent.EXTRA_SUBJECT, shareTitle);
                            context.startActivity(Intent.createChooser(share, context.getResources().getString(R.string.share_article)));
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast toast =
                                Toast.makeText(getActivity(), R.string.article_share_fail, Toast.LENGTH_LONG);
                        toast.show();
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

        public void onCommentsListButtonPressed(Post post);
    }
}
