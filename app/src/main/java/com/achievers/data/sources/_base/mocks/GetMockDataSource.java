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

    protected HashMap<String, List<T>> mEntitiesByContainerId;
    protected HashMap<String, T> mEntitiesById;

    protected static String sDoesNotExistFailMessage = "Entity does not exist.";

    public GetMockDataSource(BaseGenerator<T> generator) {
        mEntitiesByContainerId = new HashMap<>();
        mEntitiesById = new HashMap<>();
        mGenerator = generator;
    }

    @Override
    public void get(
            String id,
            @NonNull GetCallback<T> callback) {

        checkNotNull(callback);

        if (!mEntitiesById.containsKey(id)) {
            callback.onFailure(sDoesNotExistFailMessage);
            return;
        }

        callback.onSuccess(mEntitiesById.get(id));
    }

    @Override
    public List<T> seed(String containerId, int size) {
        if (containerId == null) containerId = DefaultConfig.String;

        int entitiesSize = 0;

        if (mEntitiesByContainerId.containsKey(containerId)) {
            entitiesSize = mEntitiesByContainerId.get(containerId).size();
        }

        int generateSize = entitiesSize + size;

        if (generateSize <= 0) {
            return null;
        }

        List<T> generated = mGenerator.multiple(generateSize);

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