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

package com.achievers.ui.categories;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.achievers.R;
import com.achievers.ui.achievements.AchievementsFragment;
import com.achievers.ui.achievements.AchievementsPresenter;
import com.achievers.ui.achievements.AchievementsViewModel;
import com.achievers.data.source.achievements.AchievementsDataSource;
import com.achievers.data.source.achievements.AchievementsRemoteDataSource;
import com.achievers.data.source.categories.CategoriesDataSource;
import com.achievers.data.source.categories.remote.CategoriesRemoteDataSource;
import com.achievers.util.ActivityUtils;

import static com.achievers.util.ActivityUtils.isTablet;
import static com.google.common.base.Preconditions.checkNotNull;

///**
// * Class that creates fragments (MVP views) and makes the necessary connections between them.
// */
//public class CategoriesMvpController {
//
//    private final FragmentActivity mFragmentActivity;
//
//    // Null category ID means there's no category selected (or in phone mode)
//    @Nullable private final Integer mCategoryId;
//
//    private CategoriesTabletPresenter mCategoriesTabletPresenter;
//    private CategoriesPresenter mCategoriesPresenter;
//
//    // Force factory method, prevent direct instantiation:
//    private CategoriesMvpController(
//            @NonNull FragmentActivity fragmentActivity,
//            @Nullable Integer categoryId) {
//
//        this.mFragmentActivity = fragmentActivity;
//        this.mCategoryId = categoryId;
//    }
//
//    /**
//     * Creates a controller for a category view for phones or tablets.
//     * @param fragmentActivity the context activity
//     * @return a CategoriesMvpController
//     */
//    public static CategoriesMvpController createCategoriesView(
//            @NonNull FragmentActivity fragmentActivity,
//            @Nullable Integer categoryId) {
//
//        checkNotNull(fragmentActivity);
//
//        CategoriesMvpController categoriesMvpController =
//                new CategoriesMvpController(fragmentActivity, categoryId);
//
//        categoriesMvpController.initCategoriesView();
//        return categoriesMvpController;
//    }
//
//    private void initCategoriesView() {
//        if (isTablet(mFragmentActivity)) {
//            createTabletElements();
//        } else {
//            createPhoneElements();
//        }
//    }
//
//    private void createPhoneElements() {
//        CategoriesFragment categoriesFragment = findOrCreateCategoriesFragment(R.id.contentFrame);
//        mCategoriesPresenter = createListPresenter(categoriesFragment);
//        categoriesFragment.setPresenter(mCategoriesPresenter);
//    }
//
//    private void createTabletElements() {
//        // Fragment 1: List
//        CategoriesFragment categoriesFragment = findOrCreateCategoriesFragment(R.id.contentFrame_list);
//        this.mCategoriesPresenter = createListPresenter(categoriesFragment);
//
//        // Fragment 2: Detail
//        AchievementsFragment achievementsFragment = findOrCreateCategoryDetailFragmentForTablet();
//        AchievementsPresenter achievementsPresenter = createDetailPresenter(achievementsFragment);
//
//        // Fragments connect to their presenters through a tablet presenter:
////        mCategoriesTabletPresenter = new CategoriesTabletPresenter(mCategoriesPresenter);
////        categoriesFragment.setPresenter(mCategoriesTabletPresenter);
////        achievementsFragment.setPresenter(mCategoriesTabletPresenter);
////        mCategoriesTabletPresenter.setAchievementsPresenter(achievementsPresenter);
//    }
//
//    @NonNull
//    private CategoriesPresenter createListPresenter(CategoriesFragment categoriesFragment) {
//        CategoriesDataSource categoriesDataSource = this.createCategoriesDataSource();
//
//        this.mCategoriesPresenter = new CategoriesPresenter(
//                this.mFragmentActivity,
//                mFragmentActivity.getSupportLoaderManager(),
//                categoriesDataSource,
//                categoriesFragment);
//
//        CategoriesViewModel categoriesViewModel = new CategoriesViewModel(this.mFragmentActivity, mCategoriesPresenter);
//        categoriesFragment.setViewModel(categoriesViewModel);
//
//        return this.mCategoriesPresenter;
//    }
//
//    @NonNull
//    private AchievementsPresenter createDetailPresenter(AchievementsFragment achievementsFragment) {
//        AchievementsDataSource achievementsDataSource = this.createAchievementsDataSource();
//
//        AchievementsPresenter achievementsPresenter = new AchievementsPresenter(
//                achievementsDataSource,
//                achievementsFragment);
//
//        AchievementsViewModel achievementsViewModel = new AchievementsViewModel(
//                this.mFragmentActivity);
//
//        achievementsFragment.setViewModel(achievementsViewModel);
//
//        return achievementsPresenter;
//    }
//
//    @NonNull
//    private CategoriesDataSource createCategoriesDataSource() {
//        return CategoriesRemoteDataSource.getInstance();
//    }
//
//    @NonNull
//    private AchievementsDataSource createAchievementsDataSource() {
//        return AchievementsRemoteDataSource.getInstance();
//    }
//
//    @NonNull
//    private CategoriesFragment findOrCreateCategoriesFragment(@IdRes int fragmentId) {
//        CategoriesFragment categoriesFragment =
//                (CategoriesFragment) getFragmentById(fragmentId);
//        if (categoriesFragment == null) {
//            // Create the fragment
//            categoriesFragment = CategoriesFragment.newInstance();
//            ActivityUtils.addFragmentToActivity(
//                    getSupportFragmentManager(), categoriesFragment, fragmentId);
//        }
//
//        return categoriesFragment;
//    }
//
//    @NonNull
//    private AchievementsFragment findOrCreateCategoryDetailFragmentForTablet() {
//        AchievementsFragment achievementsFragment =
//                (AchievementsFragment) getFragmentById(R.id.contentFrame_detail);
//
//        if (achievementsFragment == null) {
//            // Create the fragment
//            achievementsFragment = AchievementsFragment.newInstance();
//            ActivityUtils.addFragmentToActivity(
//                    getSupportFragmentManager(), achievementsFragment, R.id.contentFrame_detail);
//        }
//
//        return achievementsFragment;
//    }
//
//    private Fragment getFragmentById(int fragmentId) {
//        return getSupportFragmentManager().findFragmentById(fragmentId);
//    }
//
//    public void setFiltering(CategoriesFilterType filtering) {
//        mCategoriesPresenter.setFiltering(filtering);
//    }
//
//    public CategoriesFilterType getFiltering() {
//        return mCategoriesPresenter.getFiltering();
//    }
//
//    public Integer getCategoryId() {
//        return this.mCategoryId;
//    }
//
//    private FragmentManager getSupportFragmentManager() {
//        return mFragmentActivity.getSupportFragmentManager();
//    }
//
////    public boolean navigateToPreviousCategory() {
////        if (isTablet(mFragmentActivity)) {
////            return this.mCategoriesTabletPresenter.navigateToPreviousCategory();
////        }
////
////        return this.mCategoriesPresenter.navigateToPreviousCategory();
////    }
//}
