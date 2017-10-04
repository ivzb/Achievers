package com.achievers.ui.add_achievement;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.achievers.data.callbacks.SaveCallback;
import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.File;
import com.achievers.data.entities.Involvement;
import com.achievers.data.source.achievements.AchievementsDataSource;
import com.achievers.data.source.files.FilesDataSource;
import com.achievers.ui._base.AbstractPresenter;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

public class AddAchievementPresenter
        extends AbstractPresenter<AddAchievementContract.View>
        implements AddAchievementContract.Presenter {

    @NonNull
    private final AchievementsDataSource mAchievementsRepository;

    @NonNull
    private final FilesDataSource mFilesRepository;

    public AddAchievementPresenter(
            @NonNull AddAchievementContract.View view,
            @NonNull AchievementsDataSource achievementsRepository,
            @NonNull FilesDataSource filesRepository) {

        mView = view;
        mAchievementsRepository = achievementsRepository;
        mFilesRepository = filesRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void saveAchievement(final Achievement achievement) {
        this.mAchievementsRepository.saveAchievement(achievement, new SaveCallback<Achievement>() {
            @Override
            public void onSuccess(Achievement result) {
                mView.finish();
            }

            @Override
            public void onFailure(String message) {
                mView.showErrorMessage(message);
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
        mView.showInvolvement(involvement);
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
