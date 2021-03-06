package com.achievers.ui;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.achievers.DefaultConfig;
import com.achievers.data.entities.Involvement;
import com.achievers.ui._base._contracts.action_handlers.BasePictureLoadActionHandler;
import com.achievers.ui._base._contracts.adapters.BaseSelectableAdapter;
import com.achievers.utils.ui.FreskoCircleProgressBarDrawable;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;

public class AdapterSetters {

    // Fresco
    @BindingAdapter({ "url" })
    public static void loadImage(SimpleDraweeView view, String imageUrl) {
        if (imageUrl != null) {
            Uri uri = Uri.parse(imageUrl);
            loadImage(view, uri);
        }
    }

    @BindingAdapter({ "uri" })
    public static void loadImage(SimpleDraweeView view, Uri uri) {
        if (uri != null) {
            view.setImageURI(uri);

            GenericDraweeHierarchy hierarchy = view.getHierarchy();
            hierarchy.setFadeDuration(250);

            hierarchy.setPlaceholderImage(DefaultConfig.PlaceholderImage);
            hierarchy.setProgressBarImage(new FreskoCircleProgressBarDrawable());
        }
    }

    // Binding Drawable to ImageView
    @BindingAdapter({ "src" })
    public static void setImageDrawable(ImageView view, Drawable drawable) {
        view.setImageDrawable(drawable);
    }

    @BindingAdapter({ "src" })
    public static void setImageResource(ImageView view, int resource){
        view.setImageResource(resource);
    }

    @BindingAdapter({ "adapter", "layoutManager" })
    public static void setInvolvements(
            RecyclerView view,
            final BaseSelectableAdapter<Involvement> adapter,
            final RecyclerView.LayoutManager layoutManager) {

        if (adapter == null || layoutManager == null) return;

        view.setAdapter((RecyclerView.Adapter) adapter);
        view.setLayoutManager(layoutManager);
    }

    @BindingAdapter({ "uri", "actionHandler" })
    public static void setPicture(
            SimpleDraweeView view,
            final Uri uri,
            final BasePictureLoadActionHandler actionHandler) {

        if (uri == null || actionHandler == null) return;

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setControllerListener(new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFinalImageSet(String id, @javax.annotation.Nullable ImageInfo imageInfo, @javax.annotation.Nullable Animatable animatable) {
                        actionHandler.pictureLoaded();
                    }
                })
                .build();

        view.setController(controller);
    }
}