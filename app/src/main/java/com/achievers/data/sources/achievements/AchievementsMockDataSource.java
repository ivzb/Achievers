package com.achievers.data.sources.achievements;

import android.support.annotation.NonNull;

import com.achievers.data.Result;
import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.entities.Achievement;
import com.achievers.data.generators.AchievementsGenerator;
import com.achievers.data.generators.config.GeneratorConfig;
import com.achievers.data.sources._base.mocks.BaseMockDataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.achievers.utils.Preconditions.checkNotNull;

public class AchievementsMockDataSource
        extends BaseMockDataSource<Achievement>
        implements AchievementsDataSource {

    private static AchievementsMockDataSource sINSTANCE;

    protected HashMap<String, List<Achievement>> mEntitiesByQuestId;

    public static AchievementsMockDataSource getInstance() {
        checkNotNull(sINSTANCE);

        return sINSTANCE;
    }

    public static AchievementsMockDataSource createInstance() {
        sINSTANCE = new AchievementsMockDataSource();

        return getInstance();
    }

    public static void destroyInstance() {
        sINSTANCE = null;
    }

    private AchievementsMockDataSource() {
        super(new AchievementsGenerator(GeneratorConfig.getInstance()));

        mEntitiesByQuestId = new HashMap<>();
    }

    @Override
    public void loadByQuestId(
            String questId,
            int page,
            @NonNull LoadCallback<Achievement> callback) {

        checkNotNull(questId);
        checkNotNull(callback);

        if (page < 0) {
            callback.onFailure(sInvalidPageFailMessage);
            return;
        }

        List<Achievement> entities = mEntitiesByQuestId.get(questId);

        if (entities == null) {
            callback.onNoMore();
            return;
        }

        int start = page * sPageSize;
        int size = entities.size();
        boolean noMore = start > size || size == 0;
        int end = Math.min(start + sPageSize, size);

        if (noMore) {
            callback.onNoMore();
            return;
        }

        List<Achievement> data = entities.subList(start, end);
        Result<List<Achievement>> result = new Result<>(data);

        callback.onSuccess(result, page);
    }

    public void seedAchievementsByQuest(
            String questId,
            List<Achievement> achievements) {

        checkNotNull(questId);
        checkNotNull(achievements);

        int entitiesSize = 0;

        if (mEntitiesByQuestId.containsKey(questId)) {
            entitiesSize = mEntitiesByQuestId.get(questId).size();
        }

        int generateSize = entitiesSize + achievements.size();

        if (generateSize <= 0) {
            return;
        }

        if (!mEntitiesByQuestId.containsKey(questId)) {
            mEntitiesByQuestId.put(questId, new ArrayList<Achievement>());
        }

        for (Achievement achievement: achievements) {
            mEntitiesByQuestId.get(questId).add(achievement);
        }
    }
}