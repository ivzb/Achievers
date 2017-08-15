package com.achievers.ui.achievement.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.achievers.R;
import com.achievers.data.models.UploadEvidenceItem;

import java.util.List;

public class UploadEvidenceDialogSimpleAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<UploadEvidenceItem> items;
    private boolean isGrid;

    public UploadEvidenceDialogSimpleAdapter(Context context, List<UploadEvidenceItem> items, boolean isGrid) {
        layoutInflater = LayoutInflater.from(context);
        this.items = items;
        this.isGrid = isGrid;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public UploadEvidenceItem getItem(int position) {
        return this.items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = this.layoutInflater.inflate(R.layout.evidence_upload_dialog_item, parent, false);
        }

        TextView itemTitle = (TextView) convertView.findViewById(R.id.text_view);
        ImageView itemImage = (ImageView) convertView.findViewById(R.id.image_view);

        UploadEvidenceItem currentItem = getItem(position);

        itemTitle.setText(currentItem.getTitle());
        itemImage.setImageDrawable(currentItem.getImage());

        return convertView;
    }
}