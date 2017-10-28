package com.achievers.data.source.achievements;

import android.support.annotation.NonNull;

import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.callbacks.SaveCallback;
import com.achievers.data.entities.Achievement;
import com.achievers.generator.AchievementsGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.achievers.utils.Preconditions.checkNotNull;

@Deprecated
public class AchievementsMockDataSource implements AchievementsDataSource {

    private static AchievementsDataSource sINSTANCE;
    private static int sPageSize = 9;
    private static String sDoesNotExistFailMessage = "Entity does not exist.";
    private static String sNoAchievementFailMessage = "No entity to save.";
    private static String sInvalidPageFailMessage = "Please provide non negative page.";

    private List<Achievement> mEntities;
    private HashMap<Long, Achievement> mEntitiesById;

    private AchievementsGenerator mGenerator;

    public static AchievementsDataSource getInstance() {
        if (sINSTANCE == null) sINSTANCE = new AchievementsMockDataSource();

        return sINSTANCE;
    }

    private AchievementsMockDataSource() {
        mEntities = new ArrayList<>();
        mEntitiesById = new HashMap<>();
        mGenerator = new AchievementsGenerator();
    }

    @Override
    public void get(
            long id,
            @NonNull GetCallback<Achievement> callback) {

        checkNotNull(callback);

        if (!mEntitiesById.containsKey(id)) {
            callback.onFailure(sDoesNotExistFailMessage);
            return;
        }

        callback.onSuccess(mEntitiesById.get(id));
    }

    @Override
    public void load(
            Long categoryId,
            int page,
            @NonNull LoadCallback<Achievement> callback) {

        checkNotNull(callback);

        if (page < 0) {
            callback.onFailure(sInvalidPageFailMessage);
            return;
        }

        int start = page * sPageSize;
        int end = start + sPageSize;
        load(end);

        callback.onSuccess(mEntities.subList(start, end));
    }

    @Override
    public void save(
            @NonNull Achievement achievement,
            @NonNull SaveCallback<Long> callback) {

        checkNotNull(callback);

        if (achievement == null) {
            callback.onFailure(sNoAchievementFailMessage);
            return;
        }

        achievement.setId(mEntities.size() + 1);
        achievement.setCreatedOn(new Date());

        mEntitiesById.put(achievement.getId(), achievement);
        mEntities.add(achievement);

        callback.onSuccess(achievement.getId());
    }

    private void load(int to) {
        long nextId = mEntities.size() + 1;
        int size = to - mEntities.size();

        if (size > 0) {
            List<Achievement> generated = mGenerator.multiple(nextId, size);

            for (Achievement achievement : generated) {
                mEntitiesById.put(achievement.getId(), achievement);
                mEntities.add(achievement);
            }
        }
    }
}