package com.achievers.ui._base.activities;

import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;

import com.achievers.R;
import com.achievers.ui._base.AbstractActivity;
import com.facebook.drawee.view.SimpleDraweeView;

public abstract class CollapsingToolbarActivity extends AbstractActivity {

    protected void initCollapsingToolbar(Uri imageUri, String title) {
        // todo: add default image while loading

        SimpleDraweeView image = findViewById(R.id.image);
        image.setImageURI(imageUri);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(title);
    }
}
