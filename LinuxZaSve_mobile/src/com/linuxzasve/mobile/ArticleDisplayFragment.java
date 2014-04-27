package com.linuxzasve.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.google.gson.Gson;
import com.linuxzasve.mobile.googl.GoogleUrlShortener;
import com.linuxzasve.mobile.googl.model.GooGlResponse;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.apache.http.Header;

public class ArticleDisplayFragment extends SherlockFragment {

    private WebView clanak;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This fragment participates in options menu creation, so it needs to announce it.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.clanak);
        View rootView = inflater.inflate(R.layout.clanak, container, false);

        Intent intent = getActivity().getIntent();
        String message = intent.getStringExtra("naslov");
        String body = intent.getStringExtra("sadrzaj");
        String sadrzaj = "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" />" + "<h1>" + message + "</h1>" + body;

        clanak = (WebView) rootView.findViewById(R.id.sadrzaj_clanka);
        WebSettings settings = clanak.getSettings();
        settings.setDefaultTextEncodingName("utf-8");

        clanak.loadDataWithBaseURL("file:///android_asset/", sadrzaj, "text/html", "UTF-8", null);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.clanak, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        Intent intent2 = getActivity().getIntent();
        String komentari = intent2.getStringExtra("komentari");
        String origLink = intent2.getStringExtra("origLink");
        String naslov = intent2.getStringExtra("naslov");
        Integer post_id = intent2.getIntExtra("post_id", 0);

        switch (item.getItemId()) {

            case android.R.id.home:
                getActivity().onBackPressed();
                return true;

            case R.id.menu_pokazi_komentare:
                Intent intent = new Intent(getActivity(), ListaKomentara.class);

                intent.putExtra("komentari", komentari);
                intent.putExtra("naslov", naslov);
                intent.putExtra("post_id", post_id);
                startActivity(intent);
                return true;

            case R.id.menu_share:
                GoogleUrlShortener.ShortenUrl(getActivity(), origLink, new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(final int statusCode, final Header[] headers, final String responseBody) {
                        Gson gson = new Gson();

                        String skraceniUrl = gson.fromJson(responseBody, GooGlResponse.class).getId();

                        Intent intent2 = getActivity().getIntent();
                        String naslov = intent2.getStringExtra("naslov");
                        Intent share = new Intent(Intent.ACTION_SEND);
                        share.setType("text/plain");

                        // kreiram naslov za dijeljenje
                        String shareNaslov = "LZS | " + naslov;

                        // kreiram tekst za dijeljenje
                        String shareText = "Linux za sve | " + naslov + " " + skraceniUrl;

                        share.putExtra(Intent.EXTRA_TEXT, shareText);
                        share.putExtra(Intent.EXTRA_SUBJECT, shareNaslov);
                        startActivity(Intent.createChooser(share, "Podijeli članak!"));
                    }

                });

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
