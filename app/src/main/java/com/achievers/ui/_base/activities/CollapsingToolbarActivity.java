package com.achievers.ui._base.activities;

import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;

import com.achievers.R;
import com.achievers.ui._base.AbstractActivity;
import com.facebook.drawee.view.SimpleDraweeView;

public abstract class CollapsingToolbarActivity extends AbstractActivity {

    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private SimpleDraweeView mSimpleDraweeView;

    protected void initCollapsingToolbar(int imageResource, String title) {
        mCollapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        mSimpleDraweeView = findViewById(R.id.image);

        setCollapsingToolbarImage(imageResource);
        setCollapsingToolbarTitle(title);
    }

    protected void setCollapsingToolbarImage(int imageResource) {
        mSimpleDraweeView.setImageResource(imageResource);
    }

    protected void setCollapsingToolbarImage(Uri imageUri) {
        mSimpleDraweeView.setImageURI(imageUri);
    }

    protected void setCollapsingToolbarTitle(String title) {
        mCollapsingToolbarLayout.setTitle(title);
    }
}
