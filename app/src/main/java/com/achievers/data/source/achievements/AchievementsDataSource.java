package com.achievers.data.source.achievements;

import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.entities.Achievement;
import com.achievers.data.source._base.contracts.BaseDataSource;

public interface AchievementsDataSource extends BaseDataSource<Achievement> {

    void loadByQuestId(Long id, int page, LoadCallback<Achievement> callback);
}