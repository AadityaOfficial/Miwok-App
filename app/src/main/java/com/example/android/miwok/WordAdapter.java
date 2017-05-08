package com.example.android.miwok;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by aadit on 4/1/2017.
 */

public class WordAdapter extends ArrayAdapter<Word> {
    private int colorId;

    public WordAdapter(Activity context, ArrayList<Word> words, int colorId) {
        super(context, 0, words);
        this.colorId = colorId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        Word newWords = getItem(position);
        TextView miwokTranslation = (TextView) listItemView.findViewById(R.id.miwok_text_view);
        miwokTranslation.setText(newWords.getMiwokTranslation());

        TextView defaultTranslation = (TextView) listItemView.findViewById(R.id.default_text_view);
        defaultTranslation.setText(newWords.getDefaultTranslation());

        ImageView imageIcon = (ImageView) listItemView.findViewById(R.id.image_Icon);

        if (newWords.hasImage()) {
            imageIcon.setImageResource(newWords.getImageResourceId());
            imageIcon.setVisibility(View.VISIBLE);
        } else {
            imageIcon.setVisibility(View.GONE);

        }
        View textContainer = listItemView.findViewById(R.id.text_container);
        int color= ContextCompat.getColor(getContext(),colorId);
        textContainer.setBackgroundColor(color);
        return listItemView;
    }
}
