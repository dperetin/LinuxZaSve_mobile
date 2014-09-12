package com.linuxzasve.mobile.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.linuxzasve.mobile.ActivityHelper;
import com.linuxzasve.mobile.R;
import com.linuxzasve.mobile.rest.LzsRestGateway;
import com.linuxzasve.mobile.rest.response.GetPostCommentsResponseHandler;

/**
 * Fragment displays list of articles. Extends <b>SherlockFragment</b> for compatibility with android versions older
 * than 3.0. After instantiation articleListFragmentType argument must be passed.
 *
 * @author dejan
 */
public class CommentListFragment extends Fragment {
    private ListView listView;
    private LinearLayout commentProgressLayout;
    private Integer postId;
    private String postTitle;
    private MenuItem refresh;
    private CommentListFragmentListener commentListFragmentListener;

    @Override
    public void onResume() {
        super.onResume();

        commentListFragmentListener.setCommentListFragmentActionBarTitle(postTitle);
    }

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);

        try {
            commentListFragmentListener = (CommentListFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement CommentListFragmentListener");
        }
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        postId = getArguments().getInt(ArticleDisplayFragment.BUNDLE_POST_ID);
        postTitle = getArguments().getString(ArticleDisplayFragment.BUNDLE_POST_TITLE);

        // This fragment participates in options menu creation, so it needs to announce it.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {

        // inflating fragment layout
        return inflater.inflate(R.layout.comment_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView = (ListView) getActivity().findViewById(R.id.commentsList);
        commentProgressLayout = (LinearLayout) getActivity().findViewById(R.id.commentsProgressLayout);

        commentProgressLayout.setVisibility(View.VISIBLE);

        fetchComments();
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
            inflater.inflate(R.menu.comment_list_menu, menu);

            refresh = menu.findItem(R.id.menu_refresh_item);


        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * Instantiates AsyncTask class which fetches new posts and executes it.
     */
    public void fetchComments() {
        if (ActivityHelper.isOnline(getActivity())) {
            LzsRestGateway g = new LzsRestGateway();
            GetPostCommentsResponseHandler responseHandler = new GetPostCommentsResponseHandler(getActivity(), listView, refresh, commentProgressLayout);

            g.getCommentsForPost(postId, responseHandler);
        } else {
            Toast toast = Toast.makeText(getActivity(), R.string.network_not_available, Toast.LENGTH_LONG);
            toast.show();
            if (commentProgressLayout != null) {
                commentProgressLayout.setVisibility(View.GONE);
            }

            if (refresh != null) {
                refresh.setActionView(null);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                commentListFragmentListener.onCommentListFragmentUpNavPressed();
                return true;

            case R.id.menu_refresh_item:
                refresh.setActionView(R.layout.actionbar_indeterminate_progress);
                fetchComments();
                return true;

            case R.id.menu_new_comment:
                commentListFragmentListener.onNewCommentPressed(postId);

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public interface CommentListFragmentListener {
        public void onCommentListFragmentUpNavPressed();
        public void setCommentListFragmentActionBarTitle(String title);
        public void onNewCommentPressed(Integer postId);
    }
}
