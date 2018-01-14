package com.achievers.data.sources;

import com.achievers.data.generators.config.GeneratorConfig;
import com.achievers.data.seed.Seed;
import com.achievers.data.sources.achievements.AchievementsDataSource;
import com.achievers.data.sources.achievements.AchievementsMockDataSource;
import com.achievers.data.sources.authentication.AuthenticationDataSource;
import com.achievers.data.sources.contributions.ContributionsDataSource;
import com.achievers.data.sources.contributions.ContributionsMockDataSource;
import com.achievers.data.sources.evidences.EvidencesDataSource;
import com.achievers.data.sources.evidences.EvidencesMockDataSource;
import com.achievers.data.sources.files.FilesDataSource;
import com.achievers.data.sources.files.FilesMockDataSource;
import com.achievers.data.sources.involvements.InvolvementsDataSource;
import com.achievers.data.sources.involvements.InvolvementsMockDataSource;
import com.achievers.data.sources.profiles.ProfilesDataSource;
import com.achievers.data.sources.profiles.ProfilesMockDataSource;
import com.achievers.data.sources.quests.QuestsDataSource;
import com.achievers.data.sources.quests.QuestsMockDataSource;
import com.achievers.data.sources.rewards.RewardsDataSource;
import com.achievers.data.sources.rewards.RewardsMockDataSource;
import com.achievers.data.sources.user.UserDataSource;
import com.achievers.data.sources.user.UserMockDataSource;

import java.util.Random;

import io.bloco.faker.Faker;

public class MockStrategy implements DataSourcesStrategy {

    public MockStrategy() {
        GeneratorConfig.initialize(new Random(), new Faker());
        Seed.initialize(GeneratorConfig.getInstance());
    }

    @Override
    public AchievementsDataSource getAchievements() {
        return AchievementsMockDataSource.getInstance();
    }

    @Override
    public AuthenticationDataSource getAuthentication() {
        return null;
    }

    @Override
    public ContributionsDataSource getContributions() {
        return ContributionsMockDataSource.getInstance();
    }

    @Override
    public EvidencesDataSource getEvidences() {
        return EvidencesMockDataSource.getInstance();
    }

    @Override
    public FilesDataSource getFiles() {
        return FilesMockDataSource.getInstance();
    }

    @Override
    public InvolvementsDataSource getInvolvements() {
        return InvolvementsMockDataSource.getInstance();
    }

    @Override
    public ProfilesDataSource getProfiles() {
        return ProfilesMockDataSource.getInstance();
    }

    @Override
    public QuestsDataSource getQuests() {
        return QuestsMockDataSource.getInstance();
    }

    @Override
    public RewardsDataSource getRewards() {
        return RewardsMockDataSource.getInstance();
    }

    @Override
    public UserDataSource getUser() {
        return UserMockDataSource.getInstance();
    }
}
