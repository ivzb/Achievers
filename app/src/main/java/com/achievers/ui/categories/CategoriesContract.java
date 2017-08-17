package com.achievers.ui.categories;

import android.support.annotation.NonNull;

import com.achievers.ui.base.BaseView;
import com.achievers.entities.Category;

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

        interface OpenAchievementCallback {
            void onOpen(Integer categoryId);
        }

        void result(int requestCode, int resultCode);

        void loadCategories(Integer parentId, boolean forceUpdate);

        void openCategoryDetails(@NonNull Category requestedCategory, OpenAchievementCallback callback);

        void setFiltering(CategoriesFilterType requestType);

        CategoriesFilterType getFiltering();

        OpenAchievementCallback getOpenAchievementCallback();

        boolean navigateToPreviousCategory();
    }
}