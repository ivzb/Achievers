package com.achievers.data.source.evidences;

import android.support.annotation.NonNull;

import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.callbacks.SaveCallback;
import com.achievers.data.entities.Evidence;
import com.achievers.generator.EvidencesGenerator;
import com.achievers.generator._base.BaseGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.achievers.utils.Preconditions.checkNotNull;

public class EvidencesMockDataSource implements EvidencesDataSource {

    private static EvidencesDataSource sINSTANCE;
    private static int sPageSize = 9;
    private static String sDoesNotExistFailMessage = "Entity does not exist.";
    private static String sNoEvidenceFailMessage = "No entity to save.";
    private static String sInvalidPageFailMessage = "Please provide non negative page.";

    private HashMap<Long, List<Evidence>> mEntities;
    private HashMap<Long, Evidence> mEntitiesById;

    private BaseGenerator<Evidence> mGenerator;

    public static EvidencesDataSource getInstance() {
        if (sINSTANCE == null) sINSTANCE = new EvidencesMockDataSource();

        return sINSTANCE;
    }

    private EvidencesMockDataSource() {
        mEntities = new HashMap<>();
        mEntitiesById = new HashMap<>();
        mGenerator = new EvidencesGenerator();
    }

    @Override
    public void get(
            long id,
            @NonNull GetCallback<Evidence> callback) {

        checkNotNull(callback);

        if (!mEntitiesById.containsKey(id)) {
            callback.onFailure(sDoesNotExistFailMessage);
            return;
        }

        callback.onSuccess(mEntitiesById.get(id));
    }

    @Override
    public void load(
            Long achievementId,
            int page,
            @NonNull LoadCallback<Evidence> callback) {

        checkNotNull(achievementId);
        checkNotNull(callback);

        if (page < 0) {
            callback.onFailure(sInvalidPageFailMessage);
            return;
        }

        int start = page * sPageSize;
        int end = start + sPageSize;
        load(achievementId, end);

        callback.onSuccess(mEntities.get(achievementId).subList(start, end));
    }

    @Override
    public void save(
            @NonNull Evidence evidence,
            @NonNull SaveCallback<Long> callback) {

        checkNotNull(callback);

        if (evidence == null) {
            callback.onFailure(sNoEvidenceFailMessage);
            return;
        }

        evidence.setId(mEntities.size() + 1);
        evidence.setCreatedOn(new Date());

        mEntitiesById.put(evidence.getId(), evidence);

        if (!mEntities.containsKey(evidence.getAchievementId())) {
            mEntities.put(evidence.getAchievementId(), new ArrayList<Evidence>());
        }

        mEntities.get(evidence.getAchievementId()).add(evidence);

        callback.onSuccess(evidence.getId());
    }

    private void load(long achievementId, int to) {
        long nextId = mEntitiesById.size() + 1;

        int entitiesSize = 0;

        if (mEntities.containsKey(achievementId)) {
            entitiesSize = mEntities.get(achievementId).size();
        }

        int size = to - entitiesSize;

        if (size > 0) {
            List<Evidence> generated = mGenerator.multiple(nextId, size);

            if (!mEntities.containsKey(achievementId)) {
                mEntities.put(achievementId, new ArrayList<Evidence>());
            }

            for (Evidence evidence : generated) {
                mEntitiesById.put(evidence.getId(), evidence);
                mEntities.get(achievementId).add(evidence);
            }
        }
    }
}