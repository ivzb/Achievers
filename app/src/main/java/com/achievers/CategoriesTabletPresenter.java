/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.achievers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.achievers.Achievements.AchievementsContract;
import com.achievers.Achievements.AchievementsPresenter;
import com.achievers.Categories.CategoriesContract;
import com.achievers.Categories.CategoriesFilterType;
import com.achievers.Categories.CategoriesPresenter;
import com.achievers.data.Achievement;
import com.achievers.data.Category;
import com.achievers.data.source.AchievementsRepository;
import com.achievers.data.source.CategoriesDataSource;
import com.achievers.data.source.CategoriesRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Presenter for the tablet screen that can act as a Tasks Presenter and a Task Detail Presenter.
 */
public class CategoriesTabletPresenter implements CategoriesContract.Presenter, AchievementsContract.Presenter {

    @NonNull
    private final CategoriesRepository mCategoriesRepository;
    @NonNull
    private final CategoriesPresenter mCategoriesPresenter;

    @NonNull
    private AchievementsRepository mAchievementsRepository;
    @Nullable
    private AchievementsPresenter mAchievementsPresenter;

    public CategoriesTabletPresenter(@NonNull CategoriesRepository categoriesRepository,
                                @NonNull CategoriesPresenter categoriesPresenter) {
        mCategoriesRepository = checkNotNull(categoriesRepository);
        mCategoriesPresenter = checkNotNull(categoriesPresenter);
    }

    @Nullable
    public AchievementsRepository getAchievementsRepository() {
        return this.mAchievementsRepository;
    }

    public void setAchievementsRepository(AchievementsRepository achievementsRepository) {
        this.mAchievementsRepository = achievementsRepository;
    }

    @Nullable
    public AchievementsPresenter getAchievementsPresenter() {
        return mAchievementsPresenter;
    }

    public void setAchievementsPresenter(AchievementsPresenter achievementsPresenter) {
        mAchievementsPresenter = achievementsPresenter;
    }

    /* CategoriesContract.Presenter methods can be called with or without a detail pane */

    @Override
    public void result(int requestCode, int resultCode) {
        // todo: consider removing this code
        this.mCategoriesPresenter.result(requestCode, resultCode);
    }

    @Override
    public void loadCategories(Integer parentId, boolean forceUpdate, OpenCategoryCallback callback) {
        this.mCategoriesPresenter.loadCategories(parentId, forceUpdate, callback);
    }

    @Override
    public void openCategoryDetails(@NonNull Category requestedCategory) {
        checkNotNull(requestedCategory, "requestedCategory cannot be null!");

        this.loadCategories(requestedCategory.getId(), true, new OpenCategoryCallback() {
            @Override
            public void onCategory(Integer parentId) {
                // todo: maybe add CategoryId to stack here?

            }

            @Override
            public void onAchievement(Integer categoryId) {
                if (categoryId != null) {
                    mCategoriesRepository.getCategory(categoryId, new CategoriesDataSource.GetCategoryCallback() {
                        @Override
                        public void onLoaded(Category category) {
                            mAchievementsPresenter.loadAchievements(category, true);
                        }

                        @Override
                        public void onDataNotAvailable() {
                            // .showLoadingParentError();
                        }
                    });
                }
            }
        });

        // saving first parent as -1 because stack cant handle nulls
        //mCategoriesNavigationState.add(requestedCategory.getParent() == null || requestedCategory.getParent().getId() == null ? -1 : requestedCategory.getParent().getId());
//        this.mCategoriesPresenter.openCategoryDetails(requestedCategory);
    }

    @Override
    public void setFiltering(CategoriesFilterType requestType) {
        this.mCategoriesPresenter.setFiltering(requestType);
    }

    @Override
    public CategoriesFilterType getFiltering() {
        return this.mCategoriesPresenter.getFiltering();
    }

    @Override
    public boolean navigateToPreviousCategory() {
        return this.mCategoriesPresenter.navigateToPreviousCategory();
    }

    /* CategoriesContract.Presenter methods */

    @Override
    public void loadAchievements(Category category, boolean forceUpdate) {
        this.mAchievementsPresenter.loadAchievements(category, forceUpdate);
    }

    @Override
    public void openAchievementDetails(@NonNull Achievement requestedAchievement) {
        this.mAchievementsPresenter.openAchievementDetails(requestedAchievement);
    }
}