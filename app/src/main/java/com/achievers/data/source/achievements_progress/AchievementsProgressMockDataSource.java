package com.achievers.data.source.achievements_progress;

import android.support.annotation.NonNull;

import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.entities.AchievementProgress;
import com.achievers.generator.AchievementsProgressGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.achievers.utils.Preconditions.checkNotNull;

@Deprecated
public class AchievementsProgressMockDataSource
        implements AchievementsProgressDataSource {

    private static AchievementsProgressDataSource sINSTANCE;
    private static int sPageSize = 9;
    private static String sDoesNotExistFailMessage = "Entity does not exist.";
    private static String sNoAchievementFailMessage = "No entity to save.";
    private static String sInvalidPageFailMessage = "Please provide non negative page.";

    private List<AchievementProgress> mEntities;
    private HashMap<Long, AchievementProgress> mEntitiesById;

    private AchievementsProgressGenerator mGenerator;

    public static AchievementsProgressDataSource getInstance() {
        if (sINSTANCE == null) sINSTANCE = new AchievementsProgressMockDataSource();

        return sINSTANCE;
    }

    private AchievementsProgressMockDataSource() {
        mEntities = new ArrayList<>();
        mEntitiesById = new HashMap<>();
        mGenerator = new AchievementsProgressGenerator();
    }

    @Override
    public void get(
            long id,
            @NonNull GetCallback<AchievementProgress> callback) {

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
            @NonNull LoadCallback<AchievementProgress> callback) {

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

    private void load(int to) {
        long nextId = mEntities.size() + 1;
        int size = to - mEntities.size();

        if (size > 0) {
            List<AchievementProgress> generated = mGenerator.multiple(nextId, size);

            for (AchievementProgress achievementProgress : generated) {
                mEntitiesById.put(achievementProgress.getId(), achievementProgress);
                mEntities.add(achievementProgress);
            }
        }
    }
}
