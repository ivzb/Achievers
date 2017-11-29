package com.achievers.data.sources._base;

import android.support.annotation.NonNull;

import com.achievers.Config;
import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.entities._base.BaseModel;
import com.achievers.data.generators._base.BaseGenerator;
import com.achievers.data.sources._base.contracts.ReceiveDataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.achievers.utils.Preconditions.checkNotNull;

public abstract class AbstractReceiveDataSource<T extends BaseModel>
        implements ReceiveDataSource<T> {

    private static int sPageSize = 9;
    private static String sDoesNotExistFailMessage = "Entity does not exist.";
    private static String sInvalidPageFailMessage = "Please provide non negative page.";

    HashMap<Long, List<T>> mEntitiesByContainerId;
    HashMap<Long, T> mEntitiesById;

    private BaseGenerator<T> mGenerator;

    public AbstractReceiveDataSource(BaseGenerator<T> generator) {
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
    public void load(
            Long containerId,
            int page,
            @NonNull LoadCallback<T> callback) {

        checkNotNull(callback);

        if (containerId == null) containerId = Config.NO_ID;

        if (page < 0) {
            callback.onFailure(sInvalidPageFailMessage);
            return;
        }

        int start = page * sPageSize;
        int size = mEntitiesByContainerId.size();
        boolean noMore = start > size || size == 0;
        int end = Math.max(start + sPageSize, size);

        if (noMore) {
            callback.onNoMore();
            return;
        }

        List<T> data = mEntitiesByContainerId.get(containerId).subList(start, end);

        callback.onSuccess(data, page);
    }

    public void seed(Long containerId, int size) {
        if (containerId == null) containerId = Config.NO_ID;

        int entitiesSize = 0;

        if (mEntitiesByContainerId.containsKey(containerId)) {
            entitiesSize = mEntitiesByContainerId.get(containerId).size();
        }

        int generateSize = entitiesSize + size;

        if (generateSize <= 0) {
            return;
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
    }
}