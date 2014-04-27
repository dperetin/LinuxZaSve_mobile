package com.linuxzasve.mobile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linuxzasve.mobile.emote.EmoticonDrawables;
import com.linuxzasve.mobile.rest.model.Comment;

public class CommentListArrayAdapter extends ArrayAdapter<Comment> {
    private final Context context;
    private final List<Comment> values;

    public CommentListArrayAdapter(final Context context, final List<Comment> list) {
        super(context, R.layout.komentar_redak, list);
        this.context = context;

        values = new ArrayList<Comment>();
        List<Comment> tmp = new ArrayList<Comment>();

        // get all comments without parents
        for (Comment c : list) {
            if ("0".equals(c.getParent())) {
                tmp.add(c);
            }
        }

        // set all children
        for (Comment c : list) {
            if (!"0".equals(c.getParent())) {
                insertChild(tmp, c);
            }
        }

        // sort it all
        sort(tmp);

        // unroll
        unroll(tmp, 0);

    }

    private void sort(final List<Comment> tmp) {
        if (tmp == null) {
            return;
        }
        Collections.sort(tmp, new Comparator<Comment>() {

            @Override
            public int compare(final Comment e1, final Comment e2) {
                return e1.getDate().compareTo(e2.getDate());
            }

        });

        for (Comment c : tmp) {
            sort(c.getChildren());
        }
    }

    private void unroll(final List<Comment> tmp, final int depth) {
        if (tmp == null) {
            return;
        }
        for (Comment c : tmp) {
            c.setDepth(depth);
            values.add(c);

            unroll(c.getChildren(), depth + 1);
        }
    }

    private void insertChild(final List<Comment> list, final Comment comment) {
        if (list == null) {
            return;
        }

        for (Comment c : list) {
            if (comment.getParent().equals(String.valueOf(c.getId()))) {
                c.addChild(comment);
            } else {
                insertChild(c.getChildren(), comment);
            }
        }
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.komentar_redak, parent, false);
        TextView view = (TextView) rowView.findViewById(R.id.crta);
        view.setWidth((position + 1) * 12);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        params.setMargins((values.get(position).getDepth() + 1) * 12, 0, 0, 0);
        view.setLayoutParams(params);

        TextView neki_tekst = (TextView) rowView.findViewById(R.id.tekst_komentar);
        neki_tekst.setMovementMethod(LinkMovementMethod.getInstance());
        TextView datum = (TextView) rowView.findViewById(R.id.datum_komentar);
        TextView autor = (TextView) rowView.findViewById(R.id.autor_komentar);

        datum.setText(values.get(position).getDate());
        autor.setText(values.get(position).getName());

        neki_tekst.setText(Html.fromHtml(values.get(position).getContent(), new ImageGetter() {
            @Override
            public Drawable getDrawable(final String source) {

				/*
                 * Dohvacam ID slike. Ako nema takovg smajla, vracam null
				 */
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

        return rowView;
    }
}
