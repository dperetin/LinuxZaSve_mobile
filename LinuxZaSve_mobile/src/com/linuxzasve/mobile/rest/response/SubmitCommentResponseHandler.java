package com.linuxzasve.mobile.rest.response;

import org.apache.http.Header;
import android.content.Context;
import android.widget.Toast;
import com.google.gson.Gson;
import com.linuxzasve.mobile.R;
import com.linuxzasve.mobile.db.Comment;
import com.linuxzasve.mobile.rest.model.LzsRestResponse;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.UnsupportedEncodingException;

public class SubmitCommentResponseHandler extends AsyncHttpResponseHandler {

    private Context context;

    private String name;
    private String email;

    public SubmitCommentResponseHandler(final String name, final String email, final Context context) {
        super();

        this.name = name;
        this.email = email;
        this.context = context;
    }

    @Override
    public void onSuccess(final int statusCode, final Header[] headers, final byte[] bytes) {

        String responseBody = null;

        try {
            responseBody = new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        LzsRestResponse response = gson.fromJson(responseBody, LzsRestResponse.class);

        if ("ok".equals(response.getStatus())) {

            Toast toast = Toast.makeText(context, R.string.comment_sent, Toast.LENGTH_LONG);
            toast.show();

            Comment comment = new Comment(name, email);
            comment.save();
        } else {

            Toast toast = Toast.makeText(context, R.string.comment_send_failed, Toast.LENGTH_LONG);
            toast.show();
        }

    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        Toast toast = Toast.makeText(context, R.string.comment_send_failed, Toast.LENGTH_LONG);
        toast.show();
    }
}
