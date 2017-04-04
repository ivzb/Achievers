package com.achievers.Categories;

import static com.google.common.base.Preconditions.checkNotNull;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.achievers.R;
import com.achievers.data.Category;
import com.achievers.databinding.CategoriesFragBinding;
import com.achievers.util.ScrollChildSwipeRefreshLayout;

import java.util.List;

/**
 * Display a screen with categories
 */
public class CategoriesFragment extends Fragment implements CategoriesContract.View {

    private CategoriesContract.Presenter mPresenter;
    private CategoriesFragBinding mViewDataBinding;
    private CategoriesViewModel mCategoriesViewModel;

    public CategoriesFragment() {
        // Requires empty public constructor
    }

    public static CategoriesFragment newInstance() {
        return new CategoriesFragment();
    }

    @Override
    public void setPresenter(@NonNull CategoriesContract.Presenter presenter) {
        this.mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.mPresenter.start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.mPresenter.result(requestCode, resultCode);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.categories_frag, container, false);

        this.mViewDataBinding = CategoriesFragBinding.bind(view);
        this.mViewDataBinding.setCategories(mCategoriesViewModel);
        this.mViewDataBinding.setActionHandler(mPresenter);

        setHasOptionsMenu(true);
        setRetainInstance(true);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab_add_category);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // not implemented yet
            }
        });

        // Set up progress indicator
        final ScrollChildSwipeRefreshLayout swipeRefreshLayout = mViewDataBinding.refreshLayout;
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );
        // Set the scrolling view in the custom SwipeRefreshLayout.
        swipeRefreshLayout.setScrollUpChild(mViewDataBinding.rvCategories);

        return mViewDataBinding.getRoot();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_clear:
                // todo
                break;
            case R.id.menu_filter:
                showFilteringPopUpMenu();
                break;
            case R.id.menu_refresh:
                mPresenter.loadCategories(null, true);
                break;
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.categories_fragment_menu, menu);
    }

    public void setViewModel(CategoriesViewModel viewModel) {
        mCategoriesViewModel = viewModel;
    }

    private void showFilteringPopUpMenu() {
        PopupMenu popup = new PopupMenu(getContext(), getActivity().findViewById(R.id.menu_filter));
        popup.getMenuInflater().inflate(R.menu.filter_categories, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.completed:
                        mPresenter.setFiltering(CategoriesFilterType.COMPLETED);
                        break;
                    case R.id.notCompleted:
                        mPresenter.setFiltering(CategoriesFilterType.NOT_COMPLETED);
                        break;
                    default:
                        mPresenter.setFiltering(CategoriesFilterType.ALL_CATEGORIES);
                        break;
                }
                mPresenter.loadCategories(null, false);
                return true;
            }
        });

        popup.show();
    }

    @Override
    public void setLoadingIndicator(final boolean active) {

        if (getView() == null) {
            return;
        }
        final SwipeRefreshLayout srl =
                (SwipeRefreshLayout) getView().findViewById(R.id.refresh_layout);

        // Make sure setRefreshing() is called after the layout is done with everything else.
        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(active);
            }
        });
    }

    public void showCategories(List<Category> categories) {
        CategoriesAdapter adapter = new CategoriesAdapter(categories, mPresenter);
        mCategoriesViewModel.setAdapter(adapter);
        mCategoriesViewModel.setCategoriesListSize(categories.size());
    }

    @Override
    public void showCategoryDetailsUi(int categoryId) {
        // in it's own Activity, since it makes more sense that way and it gives us the flexibility
        // to show some Intent stubbing.
//         todo: check if Categories or Achievements should be displayed
//        Intent intent = new Intent(getContext(), CateogryDetailActivity.class);
//        intent.putExtra(CategoryDetailActivity.EXTRA_TASK_ID, categoryId);
//        startActivity(intent);
    }

    @Override
    public void showLoadingCategoriesError() {
        showMessage(getString(R.string.loading_categories_error));
    }

    private void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}