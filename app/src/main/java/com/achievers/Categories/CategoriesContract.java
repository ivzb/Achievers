package com.achievers.Categories;

import android.support.annotation.NonNull;

import com.achievers.BasePresenter;
import com.achievers.BaseView;
import com.achievers.data.Achievement;
import com.achievers.data.Category;

import java.util.List;

public class CategoriesContract {

    public interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showCategories(List<Category> categories);

        void showParent(Category parent);

        void showLoadingCategoriesError();

        void showLoadingParentError();

        boolean isActive();
    }

    public interface Presenter {

        interface OpenCategoryCallback {

            void onCategory(Integer parentId);

            void onAchievement(Integer categoryId);
        }

        void result(int requestCode, int resultCode);

        void loadCategories(Integer parentId, boolean forceUpdate, OpenCategoryCallback callback);

        void openCategoryDetails(@NonNull Category requestedCategory);

        void setFiltering(CategoriesFilterType requestType);

        CategoriesFilterType getFiltering();

        boolean navigateToPreviousCategory();
    }
}