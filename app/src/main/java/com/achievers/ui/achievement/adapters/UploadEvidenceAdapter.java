package com.achievers.ui.achievement.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.achievers.R;
import com.achievers.utils.ui.multimedia.MultimediaType;

public class UploadEvidenceAdapter extends BaseAdapter {

    private LayoutInflater mLayoutInflater;
    private MultimediaType[] mItems;

    public UploadEvidenceAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mItems = MultimediaType.values();
    }

    @Override
    public int getCount() {
        return mItems.length;
    }

    @Override
    public MultimediaType getItem(int position) {
        return mItems[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.evidence_upload_dialog_item, parent, false);
        }

        MultimediaType currentItem = getItem(position);

        TextView title = view.findViewById(R.id.text_view);
        ImageView image = view.findViewById(R.id.image_view);

        title.setText(currentItem.name());
        image.setImageResource(currentItem.getDrawable());

        return view;
    }
}