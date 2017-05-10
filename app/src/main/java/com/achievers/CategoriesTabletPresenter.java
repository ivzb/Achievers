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

import java.util.Stack;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Presenter for the tablet screen that can act as a Tasks Presenter and a Task Detail Presenter.
 */
public class CategoriesTabletPresenter implements CategoriesContract.Presenter, AchievementsContract.Presenter {

    @NonNull
    private final CategoriesPresenter mCategoriesPresenter;
    @Nullable
    private AchievementsPresenter mAchievementsPresenter;

    public CategoriesTabletPresenter(@NonNull CategoriesPresenter categoriesPresenter) {
        this.mCategoriesPresenter = checkNotNull(categoriesPresenter);
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
    public void loadCategories(Integer parentId, boolean forceUpdate) {
        this.mCategoriesPresenter.loadCategories(parentId, forceUpdate);
    }

    @Override
    public void openCategoryDetails(@NonNull final Category requestedCategory, OpenAchievementCallback callback) {
        checkNotNull(requestedCategory, "requestedCategory cannot be null!");

        this.mCategoriesPresenter.openCategoryDetails(requestedCategory, new OpenAchievementCallback() {
            @Override
            public void onOpen(Integer categoryId) {
                mAchievementsPresenter.loadAchievements(requestedCategory, true);
            }
        });
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