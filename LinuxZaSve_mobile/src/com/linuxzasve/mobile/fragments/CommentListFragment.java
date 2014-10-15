package com.linuxzasve.mobile.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
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
import com.linuxzasve.mobile.adapters.CommentListArrayAdapter;
import com.linuxzasve.mobile.rest.LzsRestGateway;
import com.linuxzasve.mobile.rest.model.LzsRestResponse;
import com.linuxzasve.mobile.rest.model.Post;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Fragment displays list of articles. Extends <b>SherlockFragment</b> for compatibility with android versions older
 * than 3.0. After instantiation articleListFragmentType argument must be passed.
 *
 * @author dejan
 */
public class CommentListFragment extends Fragment {
    private ListView listView;
    private LinearLayout commentProgressLayout;

    private Post post;
    private MenuItem refresh;
    private CommentListFragmentListener commentListFragmentListener;
    private Context context;

    @Override
    public void onResume() {
        super.onResume();

        commentListFragmentListener.setCommentListFragmentActionBarTitle(post.getTitle());
    }

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);

        context = activity;

        try {
            commentListFragmentListener = (CommentListFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement CommentListFragmentListener");
        }
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        post = getArguments().getParcelable(ArticleDisplayFragment.BUNDLE_POST);

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

            g.getCommentsForPost(post.getId(), new Callback<LzsRestResponse>() {
                @Override
                public void success(LzsRestResponse lzsRestResponse, Response response) {
                        commentProgressLayout.setVisibility(View.GONE);
                        if (refresh != null) {
                            refresh.setActionView(null);
                        }
                        CommentListArrayAdapter adapter = new CommentListArrayAdapter(context, lzsRestResponse.getPost().getComments());

                        listView.setAdapter(adapter);
                }

                @Override
                public void failure(RetrofitError error) {
                    commentProgressLayout.setVisibility(View.GONE);

                    if (refresh != null) {
                        refresh.setActionView(null);
                    }

                    Toast toast =
                            Toast.makeText(getActivity(), R.string.comment_fetch_fail, Toast.LENGTH_LONG);
                    toast.show();
                }
            });
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
                commentListFragmentListener.onNewCommentPressed(post);

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public interface CommentListFragmentListener {
        public void onCommentListFragmentUpNavPressed();
        public void setCommentListFragmentActionBarTitle(String title);
        public void onNewCommentPressed(Post post);
    }
}
