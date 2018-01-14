package com.achievers.data.sources;

import com.achievers.data.sources.achievements.AchievementsDataSource;
import com.achievers.data.sources.achievements.AchievementsRemoteDataSource;
import com.achievers.data.sources.authentication.AuthenticationDataSource;
import com.achievers.data.sources.contributions.ContributionsDataSource;
import com.achievers.data.sources.evidences.EvidencesDataSource;
import com.achievers.data.sources.evidences.EvidencesRemoteDataSource;
import com.achievers.data.sources.files.FilesDataSource;
import com.achievers.data.sources.files.FilesRemoteDataSource;
import com.achievers.data.sources.involvements.InvolvementsDataSource;
import com.achievers.data.sources.profiles.ProfilesDataSource;
import com.achievers.data.sources.quests.QuestsDataSource;
import com.achievers.data.sources.rewards.RewardsDataSource;
import com.achievers.data.sources.user.UserDataSource;
import com.achievers.data.sources.user.UserRemoteDataSource;

public class RemoteStrategy implements DataSourcesStrategy {

    @Override
    public AchievementsDataSource getAchievements() {
        return AchievementsRemoteDataSource.getInstance();
    }

    @Override
    public AuthenticationDataSource getAuthentication() {
        return null;
    }

    @Override
    public ContributionsDataSource getContributions() {
        return null;
    }

    @Override
    public EvidencesDataSource getEvidences() {
        return EvidencesRemoteDataSource.getInstance();
    }

    @Override
    public FilesDataSource getFiles() {
        return FilesRemoteDataSource.getInstance();
    }

    @Override
    public InvolvementsDataSource getInvolvements() {
        return null;
    }

    @Override
    public ProfilesDataSource getProfiles() {
        return null;
    }

    @Override
    public QuestsDataSource getQuests() {
        return null;
    }

    @Override
    public RewardsDataSource getRewards() {
        return null;
    }

    @Override
    public UserDataSource getUser() {
        return UserRemoteDataSource.getInstance();
    }
}
