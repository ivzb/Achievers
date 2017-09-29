package com.achievers.ui.categories;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.achievers.ui.base.BasePresenter;
import com.achievers.ui.base.BaseView;
import com.achievers.data.entities.Category;

public class CategoriesContract {

    public interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showCategories(Cursor cursor);

        void showNoCategories();

        void showParent(Category parent);

        void showLoadingCategoriesError(String message);

        void showLoadingParentError();

        boolean isActive();
    }

    public interface Presenter extends BasePresenter {

        interface OpenAchievementCallback {
            void onOpen(Integer categoryId);
        }

        void start();

        void result(int requestCode, int resultCode);

        void loadCategories(Long parentId);

        void openCategory(@NonNull Category requestedCategory);

//        OpenAchievementCallback getOpenAchievementCallback();

        boolean navigateToPreviousCategory();
    }
}