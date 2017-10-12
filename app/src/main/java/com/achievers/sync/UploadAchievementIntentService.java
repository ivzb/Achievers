package com.achievers.sync;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.achievers.R;
import com.achievers.data.callbacks.SaveCallback;
import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.File;
import com.achievers.data.source.achievements.AchievementsMockDataSource;
import com.achievers.data.source.files.FilesMockDataSource;
import com.achievers.ui.achievement.AchievementActivity;
import com.achievers.ui.add_achievement.AddAchievementActivity;
import com.achievers.utils.NotificationUtils;
import com.achievers.utils.PictureUtils;

import org.parceler.Parcels;

import java.io.FileNotFoundException;

import static com.achievers.ui.add_achievement.AddAchievementFragment.DESCRIPTION_KEY;
import static com.achievers.ui.add_achievement.AddAchievementFragment.INVOLVEMENTS_ADAPTER_SELECTED_POSITION_KEY;
import static com.achievers.ui.add_achievement.AddAchievementFragment.PICTURE_KEY;
import static com.achievers.ui.add_achievement.AddAchievementFragment.TITLE_KEY;

public class UploadAchievementIntentService extends IntentService {

    public final static String ACHIEVEMENT_EXTRA = "achievement";

    private static final int ACHIEVEMENT_UPLOAD_PENDING_INTENT_ID = 3417;

    public UploadAchievementIntentService() {
        super("UploadAchievementIntentService");
    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        Parcelable parcelable = intent.getParcelableExtra(ACHIEVEMENT_EXTRA);
        Achievement achievement = Parcels.unwrap(parcelable);

        if (achievement == null) {
            showFailure(null);
            return;
        }

        File picture;

        try {
            picture = PictureUtils.toFile(this, achievement.getPictureUri());
        } catch (NullPointerException | FileNotFoundException e) {
            showFailure(achievement);
            return;
        }

        savePicture(picture, achievement);
    }

    private void savePicture(final File picture, final Achievement achievement) {
        FilesMockDataSource.getInstance().storeFile(picture, new SaveCallback<String>() {
            @Override
            public void onSuccess(String pictureUrl) {
                achievement.setPictureUrl(pictureUrl);
                saveAchievement(achievement);
            }

            @Override
            public void onFailure(String message) {
                showFailure(achievement);
            }
        });
    }

    private void saveAchievement(final Achievement achievement) {
        AchievementsMockDataSource.getInstance().saveAchievement(achievement, new SaveCallback<Long>() {
            @Override
            public void onSuccess(Long id) {
                showSuccess(id);
            }

            @Override
            public void onFailure(String message) {
                showFailure(achievement);
            }
        });
    }

    private void showSuccess(Long id) {
        PendingIntent intent = getSuccessIntent(this, id);
        show("Achievement uploaded.", intent);
    }

    private void showFailure(Achievement achievement) {
        PendingIntent intent = getFailureIntent(this, achievement);
        show("Could not upload achievement. Click to try again.", intent);
    }

    private void show(String text, PendingIntent intent) {
        NotificationUtils.notify(this, getString(R.string.app_name), text, intent);
    }

    private PendingIntent getSuccessIntent(Context context, Long id) {
        Intent startActivityIntent = new Intent(context, AchievementActivity.class);
        startActivityIntent.putExtra(AchievementActivity.EXTRA_ACHIEVEMENT_ID, id);

        return PendingIntent.getActivity(
                context,
                ACHIEVEMENT_UPLOAD_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private PendingIntent getFailureIntent(Context context, Achievement achievement) {
        Intent startActivityIntent = new Intent(context, AddAchievementActivity.class);

        if (achievement != null) {
            Bundle achievementExtras = createAchievementBundle(achievement);
            startActivityIntent.putExtras(achievementExtras);
        }

        return PendingIntent.getActivity(
                context,
                ACHIEVEMENT_UPLOAD_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private Bundle createAchievementBundle(Achievement achievement) {
        Bundle bundle = new Bundle();

        bundle.putString(TITLE_KEY, achievement.getTitle());
        bundle.putString(DESCRIPTION_KEY, achievement.getDescription());
        bundle.putInt(INVOLVEMENTS_ADAPTER_SELECTED_POSITION_KEY, achievement.getInvolvementPosition());
        bundle.putParcelable(PICTURE_KEY, Parcels.wrap(achievement.getPictureUri()));

        return bundle;
    }
}