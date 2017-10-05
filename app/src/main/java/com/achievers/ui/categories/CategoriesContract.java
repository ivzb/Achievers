package com.achievers.ui.categories;

import android.database.Cursor;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import com.achievers.data.entities.Category;
import com.achievers.ui._base.contracts.BasePresenter;
import com.achievers.ui._base.contracts.BaseView;
import com.achievers.ui._base.contracts.BaseViewModel;

public class CategoriesContract {

    public interface View<DB extends ViewDataBinding>
            extends BaseView<Presenter, ViewModel, DB> {

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

    public interface ViewModel extends BaseViewModel {

        String getNoCategoriesLabel();

        boolean isLoading();

        void setLoading(boolean isLoading);

        Drawable getNoCategoriesIconRes();

        CategoriesAdapter getAdapter();
        void setAdapter(CategoriesAdapter adapter);

        Cursor getCursor();
        void setCursor(Cursor cursor);

        Category getParent();
        void setParent(Category parent);

        boolean isNotEmpty();

        void setCategoriesListSize(int categoriesListSize);
    }
}