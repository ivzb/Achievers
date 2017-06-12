package com.achievers.AddAchievement;

import android.support.annotation.NonNull;

import com.achievers.data.Achievement;
import com.achievers.data.Involvement;
import com.achievers.data.source.AchievementsDataSource;
import com.achievers.data.source.callbacks.SaveCallback;

public class AddAchievementPresenter implements AddAchievementContract.Presenter {

    @NonNull
    private final AchievementsDataSource mAchievementsRepository;

    @NonNull
    private final AddAchievementContract.View mAddAchievementView;

    /**
     * Creates a presenter for the add/edit view.
     *
     * @param achievementsRepository a repository of data for tasks
     * @param addAchievementView the add view
     */
    public AddAchievementPresenter(
            @NonNull AchievementsDataSource achievementsRepository,
            @NonNull AddAchievementContract.View addAchievementView) {

        this.mAchievementsRepository = achievementsRepository;
        this.mAddAchievementView = addAchievementView;
    }

    @Override
    public void start() {
        getInvolvements();
    }

    @Override
    public void saveAchievement(final Achievement achievement) {
        this.mAchievementsRepository.saveAchievement(achievement, new SaveCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                mAddAchievementView.finishActivity();
            }

            @Override
            public void onFailure(String message) {
                mAddAchievementView.showInvalidAchievementMessage(message);
            }
        });
    }

    @Override
    public boolean validateAchievement(Achievement achievement) {
        return false;
    }

    @Override
    public void getInvolvements() {
        Involvement[] involvements = Involvement.values();
        mAddAchievementView.showInvolvements(involvements);
    }
}
