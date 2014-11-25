package com.linuxzasve.mobile.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.linuxzasve.mobile.R;
import com.linuxzasve.mobile.rest.model.Post;
import com.linuxzasve.mobile.timthumb.TimThumb;
import com.squareup.picasso.Picasso;

public class ArticleListArrayAdapter extends ArrayAdapter<Post> {

    private final List<Post> articleList;
    private final Context context;

    static class ViewHolder {
        TextView articleTitle;
        TextView publishDate;
        TextView commentCount;
        ImageView thumbnail;
    }

    public ArticleListArrayAdapter(final Context context, final List<Post> articleList) {
        super(context, R.layout.article_list_item, articleList);
        this.articleList = articleList;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.article_list_item, parent, false);
            holder = new ViewHolder();

            holder.articleTitle = (TextView) convertView.findViewById(R.id.article_title);
            holder.publishDate = (TextView) convertView.findViewById(R.id.publish_date);
            holder.commentCount = (TextView) convertView.findViewById(R.id.comment_count);
            holder.thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.articleTitle.setText(articleList.get(position).getTitle());
        holder.publishDate.setText(articleList.get(position).getDate("dd.MM.yyyy"));
        holder.commentCount.setText(Integer.toString(articleList.get(position).getComment_count()));

        if ((articleList.get(position).getThumbnail_images() != null) && (articleList.get(position).getThumbnail_images().getFull() != null) && (articleList.get(position).getThumbnail_images().getFull().getUrl() != null)) {
            String thumbnailUrl = TimThumb.generateTimThumbUrl(articleList.get(position).getThumbnail_images().getFull().getUrl(), 384, 512, 1);
            Picasso.with(context).load(thumbnailUrl).into(holder.thumbnail);
        }

        return convertView;
    }
}
