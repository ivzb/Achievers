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
import com.achievers.Categories.CategoriesPresenter;
import com.achievers.data.Category;
import com.achievers.data.source.CategoriesRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Presenter for the tablet screen that can act as a Tasks Presenter and a Task Detail Presenter.
 */
public class CategoriesTabletPresenter implements CategoriesContract.Presenter, AchievementsContract.Presenter {

    @NonNull
    private final CategoriesRepository mCategoriesRepository;
    @NonNull
    private CategoriesPresenter mCategoriesPresenter;
    @Nullable
    private AchievementsPresenter mAchievementsPresenter;

    public CategoriesTabletPresenter(@NonNull CategoriesRepository categoriesRepository,
                                @NonNull CategoriesPresenter categoriesPresenter) {
        mCategoriesRepository = checkNotNull(categoriesRepository);
        mCategoriesPresenter = checkNotNull(categoriesPresenter);
    }

    @Nullable public AchievementsPresenter getAchievementsPresenter() {
        return mAchievementsPresenter;
    }

    public void setCategoryDetailPresenter(AchievementsPresenter achievementsPresenter) {
        mAchievementsPresenter = achievementsPresenter;
    }

    /* CategoriesContract.Presenter methods can be called with or without a detail pane */

    @Override
    public void onCategoriesResult(int requestCode, int resultCode) {
        mCategoriesPresenter.onCategoriesResult(requestCode, resultCode);
    }
}
