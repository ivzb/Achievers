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

        void showAchievements(Category category, List<Achievement> achievements);

        void showParent(Category parent);

        void showCategoryDetailsUi(int categoryId);

        void showLoadingCategoriesError();

        void showLoadingAchievementsError();

        void showLoadingParentError();

        boolean isActive();
    }

    public interface Presenter extends BasePresenter {

        void start();

        void result(int requestCode, int resultCode);

        void loadCategories(Integer parentId, boolean forceUpdate);

        void openCategoryDetails(@NonNull Category requestedCategory);

        void loadAchievements(Integer categoryId, boolean forceUpdate);

        void openAchievementDetails(@NonNull Achievement requestedAchievement);

        void setFiltering(CategoriesFilterType requestType);

        CategoriesFilterType getFiltering();
    }
}