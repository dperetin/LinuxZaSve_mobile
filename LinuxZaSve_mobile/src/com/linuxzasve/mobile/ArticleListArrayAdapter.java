package com.linuxzasve.mobile;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.linuxzasve.mobile.rest.model.Post;
import com.linuxzasve.mobile.timthumb.TimThumb;

public class ArticleListArrayAdapter extends ArrayAdapter<Post> {
	// private final Context context;

	private final List<Post> articleList;
	private final Context context;

	static class ViewHolder {
		TextView neki_tekst;
		TextView datum;
		TextView autor;
		TextView broj_komentara;
		ImageView thumbnail;
		boolean isSet = false;
	}

	public ArticleListArrayAdapter(final Context context, final List<Post> naslovi) {
		super(context, R.layout.novosti_redak, naslovi);
		// this.context = context;
		articleList = naslovi;
		this.context = context;
	}

	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.novosti_redak, parent, false);
			holder = new ViewHolder();

			holder.neki_tekst = (TextView)convertView.findViewById(R.id.neki_tekst);
			holder.datum = (TextView)convertView.findViewById(R.id.datum);
			holder.autor = (TextView)convertView.findViewById(R.id.autor);
			holder.broj_komentara = (TextView)convertView.findViewById(R.id.broj_komentara);
			holder.thumbnail = (ImageView)convertView.findViewById(R.id.thumbnail);

			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder)convertView.getTag();
		}
		holder.neki_tekst.setText(articleList.get(position).getTitle());
		holder.datum.setText(articleList.get(position).getDate("dd.MM.yyyy"));
		holder.autor.setText(articleList.get(position).getAuthor().getNickname());
		holder.broj_komentara.setText(Integer.toString(articleList.get(position).getComment_count()));

		if ((articleList.get(position).getThumbnail_images() != null) && (articleList.get(position).getThumbnail_images().getFull() != null) && (articleList.get(position).getThumbnail_images().getFull().getUrl() != null)) {
			String thumbnailUrl = TimThumb.generateTimThumbUrl(articleList.get(position).getThumbnail_images().getFull().getUrl(), 256, 256, 1);
			UrlImageViewHelper.setUrlDrawable(holder.thumbnail, thumbnailUrl);
		}

		return convertView;
	}
}
