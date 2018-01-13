package com.achievers.data.sources;

import com.achievers.data.sources.achievements.AchievementsDataSource;
import com.achievers.data.sources.authentication.AuthenticationDataSource;
import com.achievers.data.sources.contributions.ContributionsDataSource;
import com.achievers.data.sources.evidences.EvidencesDataSource;
import com.achievers.data.sources.files.FilesDataSource;
import com.achievers.data.sources.involvements.InvolvementsDataSource;
import com.achievers.data.sources.profiles.ProfilesDataSource;
import com.achievers.data.sources.quests.QuestsDataSource;
import com.achievers.data.sources.rewards.RewardsDataSource;
import com.achievers.data.sources.user.UserDataSource;

public interface DataSourcesStrategy {

    AchievementsDataSource getAchievements();

    AuthenticationDataSource getAuthentication();

    ContributionsDataSource getContributions();

    EvidencesDataSource getEvidences();

    FilesDataSource getFiles();

    InvolvementsDataSource getInvolvements();

    ProfilesDataSource getProfiles();

    QuestsDataSource getQuests();

    RewardsDataSource getRewards();

    UserDataSource getUser();
}
