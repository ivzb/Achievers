package com.achievers.ui.evidence;

import com.achievers.data.entities.Evidence;
import com.achievers.ui.achievement.AchievementContract;

/**
 * Listens to user actions from the list item in ({@link AchievementContract.View}) and redirects them to the
 * AchievementsFragment's actions listener.
 */
public class EvidenceItemActionHandler {

    private AchievementContract.Presenter mListener;

    public EvidenceItemActionHandler(AchievementContract.Presenter listener) {
        this.mListener = listener;
    }

    /**
     * Called by the Data Binding library when the row is clicked.
     */
    public void evidenceClicked(Evidence evidence) {
        // todo
    }
}