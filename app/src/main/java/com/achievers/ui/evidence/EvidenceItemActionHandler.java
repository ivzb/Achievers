package com.achievers.ui.evidence;

import com.achievers.ui.achievement.AchievementDetailContract;
import com.achievers.data.models.Evidence;

/**
 * Listens to user actions from the list item in ({@link AchievementDetailContract.View}) and redirects them to the
 * Fragment's actions listener.
 */
public class EvidenceItemActionHandler {

    private AchievementDetailContract.Presenter mListener;

    public EvidenceItemActionHandler(AchievementDetailContract.Presenter listener) {
        this.mListener = listener;
    }

    /**
     * Called by the Data Binding library when the row is clicked.
     */
    public void evidenceClicked(Evidence evidence) {
        // todo
    }
}