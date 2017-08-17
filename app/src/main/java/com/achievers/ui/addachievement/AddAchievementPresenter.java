package com.achievers.ui.addachievement;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.achievers.data.callbacks.SaveCallback;
import com.achievers.entities.Achievement;
import com.achievers.entities.File;
import com.achievers.entities.Involvement;
import com.achievers.data.source.achievements.AchievementsDataSource;
import com.achievers.data.source.files.FilesDataSource;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

public class AddAchievementPresenter implements AddAchievementContract.Presenter {

    @NonNull
    private final AchievementsDataSource mAchievementsRepository;

    @NonNull
    private final FilesDataSource mFilesRepository;

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
            @NonNull FilesDataSource filesRepository,
            @NonNull AddAchievementContract.View addAchievementView) {

        this.mAchievementsRepository = achievementsRepository;
        this.mFilesRepository = filesRepository;
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
        // todo: implement validator
        // todo: check if file has been uploaded
        return false;
    }

    @Override
    public void getInvolvements() {
        List<Involvement> involvement = Arrays.asList(Involvement.values());
        mAddAchievementView.showInvolvement(involvement);
    }

    @Override
    public void uploadImage(Bitmap bitmap, SaveCallback<File> callback) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        File uploadFile = new File(byteArray, "image/jpeg");

        this.mFilesRepository.storeFile(uploadFile, callback);
    }
}
