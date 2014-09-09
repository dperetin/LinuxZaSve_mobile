package com.linuxzasve.mobile.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.linuxzasve.mobile.R;
import com.linuxzasve.mobile.db.LzsDbContract;
import com.linuxzasve.mobile.db.LzsDbHelper;
import com.linuxzasve.mobile.rest.LzsRestGateway;
import com.linuxzasve.mobile.rest.response.SubmitCommentResponseHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment displays list of articles. Extends <b>SherlockFragment</b> for compatibility with android versions older
 * than 3.0. After instantiation articleListFragmentType argument must be passed.
 *
 * @author dejan
 */
public class CommentEditFragment extends Fragment {

    private CommentEditFragmentListener commentEditFragmentListener;

    private Integer post_id;

    // names used in previous posts, used for name autocomplete
    private String[] usedNames;

    // emails used in previous posts, used for email autocomplete
    private String[] usedEmails;

    @Override
    public void onResume() {
        super.onResume();

        commentEditFragmentListener.setCommentEditFragmentActionBarTitle();
    }

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);

        try {
            commentEditFragmentListener = (CommentEditFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement CommentEditFragmentListener");
        }
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        post_id = getArguments().getInt(ArticleDisplayFragment.BUNDLE_POST_ID);

        usedNames = getUsedNames();
        usedEmails = getUsedEmails();

        // This fragment participates in options menu creation, so it needs to announce it.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {

        // inflating fragment layout
        return inflater.inflate(R.layout.novi_komentar, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, usedNames);
        AutoCompleteTextView et1 = (AutoCompleteTextView) getActivity().findViewById(
                R.id.novi_komentar_name);
        et1.setAdapter(adapter);

        ArrayAdapter<String> adapterEmails = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, usedEmails);
        AutoCompleteTextView et2 = (AutoCompleteTextView) getActivity().findViewById(
                R.id.novi_komentar_email);
        et2.setAdapter(adapterEmails);
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
            inflater.inflate(R.menu.novi_komentar, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                commentEditFragmentListener.onCommentEditFragmentUpNavPressed();
                return true;

            case R.id.menu_send:
                AutoCompleteTextView et1 = (AutoCompleteTextView) getActivity().findViewById(
                        R.id.novi_komentar_name);
                String name = et1.getText().toString();

                AutoCompleteTextView et2 = (AutoCompleteTextView) getActivity().findViewById(
                        R.id.novi_komentar_email);
                String email = et2.getText().toString();

                EditText et4 = (EditText) getActivity().findViewById(R.id.novi_komentar_tekst);
                String text = et4.getText().toString();

                if ((email == null) || (name == null) || email.equals("") || name.equals("")) {
                    Toast toast = Toast.makeText(getActivity(), "Nisu popunjena sva obavezna polja.", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    LzsRestGateway g = new LzsRestGateway();
                    g.submitComment(post_id, name, email, text,
                                    new SubmitCommentResponseHandler(name, email, usedNames,
                                                                     usedEmails, getActivity()));
                    commentEditFragmentListener.onSendCommentButtonPressed();
                }

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private String[] getUsedNames() {
        String[] allNames;
        LzsDbHelper mDbHelper = new LzsDbHelper(getActivity());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {LzsDbContract.NewCommentName._ID, LzsDbContract.NewCommentName.COLUMN_NAME_IME};

        List<String> popisImena = new ArrayList<String>();

        Cursor c = db.query(LzsDbContract.NewCommentName.TABLE_NAME, projection, null, null, null, null, null);

        while (c.moveToNext()) {
            popisImena.add(c.getString(c.getColumnIndex(LzsDbContract.NewCommentName.COLUMN_NAME_IME)));
        }
        allNames = new String[popisImena.size()];
        allNames = popisImena.toArray(allNames);

        return allNames;
    }

    /**
     * Populates email autocomplete field
     */
    private String[] getUsedEmails() {
        String[] allEmails;
        LzsDbHelper mDbHelper = new LzsDbHelper(getActivity());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {LzsDbContract.NewCommentEmail._ID, LzsDbContract.NewCommentEmail.COLUMN_NAME_EMAIL};

        List<String> popisImena = new ArrayList<String>();

        Cursor c = db.query(LzsDbContract.NewCommentEmail.TABLE_NAME, projection, null, null, null, null, null);

        while (c.moveToNext()) {
            popisImena.add(c.getString(c.getColumnIndex(LzsDbContract.NewCommentEmail.COLUMN_NAME_EMAIL)));
        }
        allEmails = new String[popisImena.size()];
        allEmails = popisImena.toArray(allEmails);

        return allEmails;
    }

    public interface CommentEditFragmentListener {
        public void onCommentEditFragmentUpNavPressed();
        public void setCommentEditFragmentActionBarTitle();
        public void onSendCommentButtonPressed();
    }
}
