package com.linuxzasve.mobile.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.linuxzasve.mobile.db.Comment;
import com.linuxzasve.mobile.rest.LzsRestGateway;
import com.linuxzasve.mobile.rest.response.SubmitCommentResponseHandler;

import java.util.HashSet;
import java.util.Set;

public class CommentEditFragment extends Fragment {

    private CommentEditFragmentListener commentEditFragmentListener;
    private Integer postId;
    private String[] usedNames;
    private String[] usedEmails;

    private AutoCompleteTextView authorEmailAutocomplete;
    private AutoCompleteTextView authorNameAutocomplete;

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

        postId = getArguments().getInt(ArticleDisplayFragment.BUNDLE_POST_ID);

        usedNames = getUsedNames();
        usedEmails = getUsedEmails();

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {

        return inflater.inflate(R.layout.comment_edit_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, usedNames);
        authorNameAutocomplete = (AutoCompleteTextView) getActivity().findViewById(
                R.id.comment_author_name);
        authorNameAutocomplete.setAdapter(adapter);

        ArrayAdapter<String> adapterEmails = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, usedEmails);
        authorEmailAutocomplete = (AutoCompleteTextView) getActivity().findViewById(
                R.id.comment_author_email);
        authorEmailAutocomplete.setAdapter(adapterEmails);
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
            inflater.inflate(R.menu.comment_edit_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                commentEditFragmentListener.onCommentEditFragmentUpNavPressed();
                return true;

            case R.id.menu_send:

                String name = authorNameAutocomplete.getText().toString();
                String email = authorEmailAutocomplete.getText().toString();

                EditText et4 = (EditText) getActivity().findViewById(R.id.comment_text);
                String text = et4.getText().toString();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email)) {
                    Toast toast = Toast.makeText(getActivity(), R.string.mandatory_fields_empty, Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    LzsRestGateway g = new LzsRestGateway();
                    g.submitComment(postId, name, email, text,
                                    new SubmitCommentResponseHandler(name, email, getActivity()));
                    commentEditFragmentListener.onSendCommentButtonPressed();
                }

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private String[] getUsedNames() {
        Set<String> allNames = new HashSet<String>();

        for (Comment c : Comment.all()) {
            allNames.add(c.name);
        }

        return allNames.toArray(new String[allNames.size()]);
    }

    private String[] getUsedEmails() {
        Set<String> allEmails = new HashSet<String>();

        for (Comment c : Comment.all()) {
            allEmails.add(c.email);
        }

        return allEmails.toArray(new String[allEmails.size()]);
    }

    public interface CommentEditFragmentListener {
        public void onCommentEditFragmentUpNavPressed();
        public void setCommentEditFragmentActionBarTitle();
        public void onSendCommentButtonPressed();
    }
}
