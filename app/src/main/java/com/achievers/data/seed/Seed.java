package com.achievers.data.seed;

import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Contribution;
import com.achievers.data.entities.Evidence;
import com.achievers.data.entities.Quest;
import com.achievers.data.entities.Reward;
import com.achievers.data.generators._base.contracts.BaseGeneratorConfig;
import com.achievers.data.sources.achievements.AchievementsMockDataSource;
import com.achievers.data.sources.authentication.AuthenticationMockDataSource;
import com.achievers.data.sources.contributions.ContributionsMockDataSource;
import com.achievers.data.sources.evidences.EvidencesMockDataSource;
import com.achievers.data.sources.files.FilesMockDataSource;
import com.achievers.data.sources.involvements.InvolvementsMockDataSource;
import com.achievers.data.sources.profiles.ProfilesMockDataSource;
import com.achievers.data.sources.quests.QuestsMockDataSource;
import com.achievers.data.sources.rewards.RewardsMockDataSource;

import java.util.ArrayList;
import java.util.List;

public class Seed {

    private final BaseGeneratorConfig mConfig;

    public static void initialize(BaseGeneratorConfig config) {
        new Seed(config);
    }

    private Seed(BaseGeneratorConfig config) {
        mConfig = config;

        List<Achievement> achievements = seedAchievements();
        List<Evidence> evidences = seedEvidence(achievements);
        List<Reward> rewards = seedRewards();
        List<Quest> quests = seedQuests(achievements, rewards);
        List<Contribution> achievementProgresses = seedAchievementProgresses();

        seedAchievementsByQuests(quests);

        AuthenticationMockDataSource.createInstance();
        ProfilesMockDataSource.createInstance();
        FilesMockDataSource.createInstance();
        InvolvementsMockDataSource.createInstance();
    }

    private List<Reward> seedRewards() {
        int size = mConfig.getNumber(30);
        return RewardsMockDataSource.createInstance().seed(null, size);
    }

    private List<Achievement> seedAchievements() {
        int size = mConfig.getNumber(30);
        return AchievementsMockDataSource.createInstance().seed(null, size);
    }

    private List<Evidence> seedEvidence(List<Achievement> achievements) {
        List<Evidence> evidences = new ArrayList<>();

        EvidencesMockDataSource dataSource = EvidencesMockDataSource.createInstance();

        for (Achievement achievement: achievements) {
            int size = mConfig.getNumber(30);
            evidences.addAll(dataSource.seed(achievement.getId(), size));
        }

        return evidences;
    }

    private List<Quest> seedQuests(
            List<Achievement> achievements,
            List<Reward> rewards) {

        int size = mConfig.getNumber(30);

        QuestsMockDataSource dataSource = QuestsMockDataSource.createInstance(
                achievements,
                rewards);

        return dataSource.seed(null, size);
    }

    private List<Contribution> seedAchievementProgresses() {
        int size = mConfig.getNumber(30);
        return ContributionsMockDataSource.createInstance().seed(null, size);
    }

    private void seedAchievementsByQuests(List<Quest> quests) {
        AchievementsMockDataSource dataSource = AchievementsMockDataSource.getInstance();

        for (Quest quest: quests) {
            dataSource.seedAchievementsByQuest(quest.getId(), quest.getAchievements());
        }
    }
}
