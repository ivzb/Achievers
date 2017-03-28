package com.achievers.Categories;

import android.support.annotation.NonNull;

import com.achievers.BasePresenter;
import com.achievers.BaseView;
import com.achievers.data.Category;

import java.util.List;

class CategoriesContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showCategories(List<Category> categories);

        void showCategoryDetailsUi(int categoryId);

        void showLoadingCategoriesError();

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void start();

        void result(int requestCode, int resultCode);

        void loadCategories(boolean forceUpdate);

        void openCategoryDetails(@NonNull Category requestedCategory);

        void setFiltering(CategoriesFilterType requestType);

        CategoriesFilterType getFiltering();
    }
}