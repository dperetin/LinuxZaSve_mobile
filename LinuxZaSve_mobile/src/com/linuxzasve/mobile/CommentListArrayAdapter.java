package com.linuxzasve.mobile;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.linuxzasve.mobile.emote.EmoticonDrawables;
import com.linuxzasve.mobile.rest.model.Comment;
import com.linuxzasve.mobile.rest.model.Post;
import com.linuxzasve.mobile.wp_comment.Komentar;

public class CommentListArrayAdapter extends ArrayAdapter<Comment> {
	private final Context context;
	private final List<Comment> values;

	public CommentListArrayAdapter(final Context context, final List<Comment> list) {
		super(context, R.layout.komentar_redak, list);
		this.context = context;
		this.values = list;

//		post_id = list.get(0).getCommentPostId();
//		akismet = list.get(0).getAkismetCommentNounce();

	}

	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.komentar_redak, parent, false);
		TextView view = (TextView)rowView.findViewById(R.id.crta);
		view.setWidth((position + 1) * 12);
		
		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)view.getLayoutParams();
		params.setMargins((position + 1) * 12, 0, 0, 0); //substitute parameters for left, top, right, bottom
		view.setLayoutParams(params);
		
		TextView neki_tekst = (TextView)rowView.findViewById(R.id.tekst_komentar);
		neki_tekst.setMovementMethod(LinkMovementMethod.getInstance());
		TextView datum = (TextView)rowView.findViewById(R.id.datum_komentar);
		TextView autor = (TextView)rowView.findViewById(R.id.autor_komentar);
//		ImageView thumbnail = (ImageView)rowView.findViewById(R.id.komentarThumbnail);
		// datum.setText(values.get(values.size() - position - 1).datumDdmmyyy());
		datum.setText(values.get(values.size() - position - 1).getDate());
		autor.setText(values.get(values.size() - position - 1).getName());

		// neki_tekst.setText(Html.fromHtml(values.get(values.size() - position - 1).getContent()));

		neki_tekst.setText(Html.fromHtml(values.get(values.size() - position - 1).getContent(), new ImageGetter() {
			@Override
			public Drawable getDrawable(final String source) {

				/* Dohvacam ID slike. Ako nema takovg smajla, vracam null */
				Integer id = EmoticonDrawables.getDrawableId(source);

				if (id == null) {
					return null;
				}

				Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id);

				Drawable d = new BitmapDrawable(context.getResources(), bitmap);
				int dWidth = d.getIntrinsicWidth();
				int dHeight = d.getIntrinsicHeight();

				d.setBounds(0, -dHeight, dWidth, 0);
				return d;
			}
		}, null));

//		UrlImageViewHelper.setUrlDrawable(thumbnail, values.get(values.size() - position - 1).getThumbnail());

		return rowView;
	}
}
