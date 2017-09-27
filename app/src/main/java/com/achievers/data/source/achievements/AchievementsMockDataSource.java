package com.achievers.data.source.achievements;

import android.support.annotation.NonNull;

import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.callbacks.SaveCallback;
import com.achievers.data.entities.Achievement;
import com.achievers.generator.AchievementsGenerator;
import com.achievers.utils.GeneratorUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// todo: test
public class AchievementsMockDataSource implements AchievementsDataSource {

    private static AchievementsDataSource sINSTANCE;
    private static int sPageSize = 9;
    private static String sDoesNotExistFailMessage = "Achievement does not exist.";
    private static String sNoAchievementFailMessage = "No achievement to save.";

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
    public void getAchievement(long id, @NonNull GetCallback<Achievement> callback) {
        if (!mEntitiesById.containsKey(id)) {
            callback.onFailure(sDoesNotExistFailMessage);
            return;
        }

        callback.onSuccess(mEntitiesById.get(id));
    }

    @Override
    public void loadAchievements(int page, @NonNull LoadCallback<Achievement> callback) {
        int start = page * sPageSize;
        int end = start + sPageSize;
        load(start, end);

        callback.onSuccess(mEntities.subList(start, end));
    }

    @Override
    public void saveAchievement(@NonNull Achievement achievement, @NonNull SaveCallback<Achievement> callback) {
        if (achievement == null) {
            callback.onFailure(sNoAchievementFailMessage);
            return;
        }

        achievement.setId(mEntities.size());

        mEntitiesById.put(achievement.getId(), achievement);
        mEntities.add(achievement);

        callback.onSuccess(achievement);
    }

    private void load(int start, int end) {
        long nextId = 1;
        int size = end - mEntities.size();

        if (mEntities.size() > 0) {
            Achievement last = mEntities.get(mEntities.size() - 1);
            nextId = last.getId() + 1;
        }

        List<Achievement> generated = mGenerator.multiple(nextId, size);

        for (Achievement achievement: generated) {
            mEntitiesById.put(achievement.getId(), achievement);
            mEntities.add(achievement);
        }
    }
}