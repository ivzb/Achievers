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

import com.achievers.Achievements.AchievementsFragment;
import com.achievers.Achievements.AchievementsPresenter;
import com.achievers.Achievements.AchievementsViewModel;
import com.achievers.Categories.CategoriesFilterType;
import com.achievers.Categories.CategoriesFragment;
import com.achievers.Categories.CategoriesPresenter;
import com.achievers.Categories.CategoriesViewModel;
import com.achievers.data.source.AchievementsRepository;
import com.achievers.data.source.CategoriesRepository;
import com.achievers.data.source.local.AchievementsLocalDataSource;
import com.achievers.data.source.local.CategoriesLocalDataSource;
import com.achievers.data.source.remote.AchievementsRemoteDataSource;
import com.achievers.data.source.remote.CategoriesRemoteDataSource;
import com.achievers.util.ActivityUtils;

import io.realm.Realm;

import static com.achievers.util.ActivityUtils.isTablet;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Class that creates fragments (MVP views) and makes the necessary connections between them.
 */
public class CategoriesMvpController {

    private final FragmentActivity mFragmentActivity;
    private final Realm mRealm;

    // Null category ID means there's no category selected (or in phone mode)
    @Nullable private final Integer mCategoryId;

    private CategoriesTabletPresenter mCategoriesTabletPresenter;
    private CategoriesPresenter mCategoriesPresenter;

    // Force factory method, prevent direct instantiation:
    private CategoriesMvpController(
            @NonNull FragmentActivity fragmentActivity, @NonNull Realm realm, @Nullable Integer categoryId) {
        this.mFragmentActivity = fragmentActivity;
        this.mRealm = realm;
        this.mCategoryId = categoryId;
    }

    /**
     * Creates a controller for a category view for phones or tablets.
     * @param fragmentActivity the context activity
     * @return a CategoriesMvpController
     */
    public static CategoriesMvpController createCategoriesView(
            @NonNull FragmentActivity fragmentActivity, @NonNull Realm realm, @Nullable Integer categoryId) {
        checkNotNull(fragmentActivity);
        checkNotNull(realm);

        CategoriesMvpController categoriesMvpController =
                new CategoriesMvpController(fragmentActivity, realm, categoryId);

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
        this.mCategoriesPresenter = createListPresenter(categoriesFragment);

        // Fragment 2: Detail
        AchievementsFragment achievementsFragment = findOrCreateCategoryDetailFragmentForTablet();
        AchievementsPresenter achievementsPresenter = createDetailPresenter(achievementsFragment);

        // Fragments connect to their presenters through a tablet presenter:
        mCategoriesTabletPresenter = new CategoriesTabletPresenter(mCategoriesPresenter);
        categoriesFragment.setPresenter(mCategoriesTabletPresenter);
        achievementsFragment.setPresenter(mCategoriesTabletPresenter);
        mCategoriesTabletPresenter.setAchievementsPresenter(achievementsPresenter);
    }

    @NonNull
    private CategoriesPresenter createListPresenter(CategoriesFragment categoriesFragment) {
        CategoriesRepository categoriesRepository = this.createCategoriesRepository();
        this.mCategoriesPresenter = new CategoriesPresenter(this.mFragmentActivity, categoriesRepository, categoriesFragment);

        CategoriesViewModel categoriesViewModel = new CategoriesViewModel(this.mFragmentActivity, mCategoriesPresenter);
        categoriesFragment.setViewModel(categoriesViewModel);

        return this.mCategoriesPresenter;
    }

    @NonNull
    private AchievementsPresenter createDetailPresenter(AchievementsFragment achievementsFragment) {
        AchievementsRepository achievementsRepository = this.createAchievementsRepository();
        AchievementsPresenter achievementsPresenter = new AchievementsPresenter(achievementsRepository, achievementsFragment);

        AchievementsViewModel achievementsViewModel = new AchievementsViewModel(this.mFragmentActivity);
        achievementsFragment.setViewModel(achievementsViewModel);

        return achievementsPresenter;
    }

    @NonNull
    private CategoriesRepository createCategoriesRepository() {
        return CategoriesRepository.getInstance(
                CategoriesRemoteDataSource.getInstance(),
                CategoriesLocalDataSource.getInstance(this.mRealm));
    }

    @NonNull
    private AchievementsRepository createAchievementsRepository() {
        return AchievementsRepository.getInstance(
                AchievementsRemoteDataSource.getInstance(),
                AchievementsLocalDataSource.getInstance(this.mRealm));
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

    private Fragment getFragmentById(int fragmentId) {
        return getSupportFragmentManager().findFragmentById(fragmentId);
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

    public boolean navigateToPreviousCategory() {
        if (isTablet(mFragmentActivity)) {
            return this.mCategoriesTabletPresenter.navigateToPreviousCategory();
        }

        return this.mCategoriesPresenter.navigateToPreviousCategory();
    }
}
