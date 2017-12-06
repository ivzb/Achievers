package com.achievers.data.sources._base.mocks;

import android.support.annotation.NonNull;

import com.achievers.DefaultConfig;
import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.entities._base.BaseModel;
import com.achievers.data.generators._base.contracts.BaseGenerator;
import com.achievers.data.sources._base.contracts.GetDataSource;
import com.achievers.data.sources._base.contracts.mocks.SeedDataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.achievers.utils.Preconditions.checkNotNull;

public abstract class GetMockDataSource<T extends BaseModel>
        implements GetDataSource<T>, SeedDataSource<T> {

    private BaseGenerator<T> mGenerator;

    protected HashMap<Long, List<T>> mEntitiesByContainerId;
    protected HashMap<Long, T> mEntitiesById;

    protected static String sDoesNotExistFailMessage = "Entity does not exist.";

    public GetMockDataSource(BaseGenerator<T> generator) {
        mEntitiesByContainerId = new HashMap<>();
        mEntitiesById = new HashMap<>();
        mGenerator = generator;
    }

    @Override
    public void get(
            long id,
            @NonNull GetCallback<T> callback) {

        checkNotNull(callback);

        if (!mEntitiesById.containsKey(id)) {
            callback.onFailure(sDoesNotExistFailMessage);
            return;
        }

        callback.onSuccess(mEntitiesById.get(id));
    }

    @Override
    public List<T> seed(Long containerId, int size) {
        if (containerId == null) containerId = DefaultConfig.ID;

        int entitiesSize = 0;

        if (mEntitiesByContainerId.containsKey(containerId)) {
            entitiesSize = mEntitiesByContainerId.get(containerId).size();
        }

        int generateSize = entitiesSize + size;

        if (generateSize <= 0) {
            return null;
        }

        long nextId = mEntitiesById.size() + 1;
        List<T> generated = mGenerator.multiple(nextId, generateSize);

        if (!mEntitiesByContainerId.containsKey(containerId)) {
            mEntitiesByContainerId.put(containerId, new ArrayList<T>());
        }

        for (T entity: generated) {
            mEntitiesById.put(entity.getId(), entity);
            mEntitiesByContainerId.get(containerId).add(entity);
        }

        return mEntitiesByContainerId.get(containerId);
    }
}