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

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.achievers.Achievements.AchievementsPresenter;
import com.achievers.Categories.CategoriesFilterType;
import com.achievers.Categories.CategoriesFragment;
import com.achievers.Categories.CategoriesPresenter;
import com.achievers.util.ActivityUtils;

import static com.achievers.util.ActivityUtils.isTablet;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Class that creates fragments (MVP views) and makes the necessary connections between them.
 */
public class CategoriesMvpController {

    private final FragmentActivity mFragmentActivity;

    // Null category ID means there's no category selected (or in phone mode)
    @Nullable private final Integer mCategoryId;

    private CategoriesTabletPresenter mCategoriesTabletPresenter;

    private CategoriesPresenter mCategoriesPresenter;

    // Force factory method, prevent direct instantiation:
    private CategoriesMvpController(
            @NonNull FragmentActivity fragmentActivity, @Nullable  int cateogryId) {
        mFragmentActivity = fragmentActivity;
        mCategoryId = cateogryId;
    }

    /**
     * Creates a controller for a category view for phones or tablets.
     * @param fragmentActivity the context activity
     * @return a CategoriesMvpController
     */
    public static CategoriesMvpController createCategoriesView(
            @NonNull FragmentActivity fragmentActivity, @Nullable Integer categoryId) {
        checkNotNull(fragmentActivity);

        CategoriesMvpController categoriesMvpController =
                new CategoriesMvpController(fragmentActivity, categoryId);

        categoriesMvpController.initCategoriesView();
        return categoriesMvpController;
    }

    private void initCategoriesView() {
        if (isTablet(mFragmentActivity)) {
            createTabletElements();
        } else {
            createPhoneElements();
        }
    }

    private void createPhoneElements() {
        CategoriesFragment categoriesFragment = findOrCreateCategoriesFragment(R.id.contentFrame);
        mCategoriesPresenter = createListPresenter(categoriesFragment);
        categoriesFragment.setPresenter(mCategoriesPresenter);
    }

    private void createTabletElements() {
        // Fragment 1: List
        CategoriesFragment categoriesFragment = findOrCreateCategoriesFragment(R.id.contentFrame_list);
        mCategoriesPresenter = createListPresenter(categoriesFragment);

        // Fragment 2: Detail
        AchievementsFragment achievementsFragment = findOrCreateCategoryDetailFragmentForTablet();
        AchievementsPresenter achievementsPresenter = createDetailPresenter(achievementsFragment);

        // Fragments connect to their presenters through a tablet presenter:
        mCategoriesTabletPresenter = new CategoriesTabletPresenter(
                Injection.provideCategoryRepository(mFragmentActivity),
                mCategoriesPresenter);

        categoriesFragment.setPresenter(mCategoriesTabletPresenter);
        achievementsFragment.setPresenter(mCategoriesTabletPresenter);
        mCategoriesTabletPresenter.setAchievementsPresenter(achievementsPresenter);
    }

    @NonNull
    private AchievementsPresenter createDetailPresenter(AchievementsFragment achievementsFragment) {
        return new AchievementsPresenter(
                mCategoryId,
                Injection.provideTasksRepository(mFragmentActivity.getApplicationContext()),
                achievementsFragment);
    }

    private CategoriesPresenter createListPresenter(CategoriesFragment categoriesFragment) {
        this.mCategoriesPresenter = new CategoriesPresenter(
                Injection.provideTasksRepository(mFragmentActivity.getApplicationContext()),
                categoriesFragment);

        return mCategoriesPresenter;
    }

    @NonNull
    private CategoriesFragment findOrCreateCategoriesFragment(@IdRes int fragmentId) {
        CategoriesFragment categoriesFragment =
                (CategoriesFragment) getFragmentById(fragmentId);
        if (categoriesFragment == null) {
            // Create the fragment
            categoriesFragment = CategoriesFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), categoriesFragment, fragmentId);
        }

        return categoriesFragment;
    }

    @NonNull
    private AchievementsFragment findOrCreateCategoryDetailFragmentForTablet() {
        AchievementsFragment achievementsFragment =
                (AchievementsFragment) getFragmentById(R.id.contentFrame_detail);

        if (achievementsFragment == null) {
            // Create the fragment
            achievementsFragment = AchievementsFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), achievementsFragment, R.id.contentFrame_detail);
        }

        return achievementsFragment;
    }

    private Fragment getFragmentById(int contentFrame_detail) {
        return getSupportFragmentManager().findFragmentById(contentFrame_detail);
    }

    public void setFiltering(CategoriesFilterType filtering) {
        mCategoriesPresenter.setFiltering(filtering);
    }

    public CategoriesFilterType getFiltering() {
        return mCategoriesPresenter.getFiltering();
    }

    public Integer getCategoryId() {
        return this.mCategoryId;
    }

    private FragmentManager getSupportFragmentManager() {
        return mFragmentActivity.getSupportFragmentManager();
    }
}
