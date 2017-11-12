package com.achievers.ui;

import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.achievers.R;
import com.achievers.data.entities.Category;
import com.achievers.data.entities.Involvement;
import com.achievers.ui._base.contracts.BaseSelectableAdapter;
import com.achievers.ui.categories.CategoriesContract;
import com.achievers.utils.ui.FreskoCircleProgressBarDrawable;
import com.achievers.utils.ui.ScrollChildSwipeRefreshLayout;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;

public class AdapterSetters {

    // Fresco
    @BindingAdapter({ "bind:url", "bind:resources" })
    public static void loadImage(SimpleDraweeView view, String imageUrl, Resources resources) {
        if (imageUrl != null) {
            Uri uri = Uri.parse(imageUrl);
            loadImage(view, uri, resources);
        }
    }

    @BindingAdapter({ "bind:uri", "bind:resources" })
    public static void loadImage(SimpleDraweeView view, Uri uri, Resources resources) {
        if (uri != null) {
            view.setImageURI(uri);

            GenericDraweeHierarchy hierarchy = view.getHierarchy();
            hierarchy.setFadeDuration(250);

            Drawable bunny = ResourcesCompat.getDrawable(resources, R.drawable.bunny, null);
            hierarchy.setPlaceholderImage(bunny);
            hierarchy.setProgressBarImage(new FreskoCircleProgressBarDrawable());
        }
    }

    @BindingAdapter({ "bind:url" })
    public static void loadImage(SimpleDraweeView view, String imageUrl) {
        if (imageUrl != null) {
            Uri uri = Uri.parse(imageUrl);
            view.setImageURI(uri);

            GenericDraweeHierarchy hierarchy = view.getHierarchy();
            hierarchy.setFadeDuration(250);
        }
    }

    // Binding Drawable to ImageView
    @BindingAdapter("android:src")
    public static void setImageDrawable(ImageView view, Drawable drawable) {
        view.setImageDrawable(drawable);
    }

    @BindingAdapter("android:src")
    public static void setImageResource(ImageView imageView, int resource){
        imageView.setImageResource(resource);
    }

    @BindingAdapter({ "bind:adapter", "bind:layoutManager" })
    public static void setInvolvements(
            RecyclerView view,
            final BaseSelectableAdapter<Involvement> adapter,
            final RecyclerView.LayoutManager layoutManager) {

        if (adapter == null || layoutManager == null) return;

        view.setAdapter((RecyclerView.Adapter) adapter);
        view.setLayoutManager(layoutManager);
    }

    /**
     * Reloads the data when the pull-to-refresh is triggered.
     * <p>
     * Creates the {@code android:onRefresh} for a {@link SwipeRefreshLayout}
     * that takes a {@link CategoriesContract.Presenter}.
     */
    @BindingAdapter({ "adapter:onRefresh", "adapter:category" })
    public static void setSwipeRefreshLayoutOnCategoryRefreshListener(ScrollChildSwipeRefreshLayout view, final CategoriesContract.Presenter presenter, final Category parent) {
        view.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Integer parentId = parent != null ? parent.getId() : null;
                // todo: refresh accurate parent
                presenter.loadCategories(null);
            }
        });
    }
}