package com.achievers.data.source._base;

import android.support.annotation.NonNull;

import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.entities._base.BaseModel;
import com.achievers.generator._base.BaseGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.achievers.utils.Preconditions.checkNotNull;

public abstract class AbstractReceiveDataSource<T extends BaseModel>
        implements ReceiveDataSource<T> {

    private static int sPageSize = 9;
    private static String sDoesNotExistFailMessage = "Entity does not exist.";
    private static String sInvalidPageFailMessage = "Please provide non negative page.";

    HashMap<Long, List<T>> mEntities;
    HashMap<Long, T> mEntitiesById;

    private BaseGenerator<T> mGenerator;

    public AbstractReceiveDataSource(BaseGenerator<T> generator) {
        mEntities = new HashMap<>();
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

        if (containerId == null) containerId = -1L;

        if (page < 0) {
            callback.onFailure(sInvalidPageFailMessage);
            return;
        }

        int start = page * sPageSize;
        int end = start + sPageSize;
        load(containerId, end);

        callback.onSuccess(mEntities.get(containerId).subList(start, end));
    }

    private void load(long id, int to) {
        long nextId = mEntitiesById.size() + 1;

        int entitiesSize = 0;

        if (mEntities.containsKey(id)) {
            entitiesSize = mEntities.get(id).size();
        }

        int size = to - entitiesSize;

        if (size > 0) {
            List<T> generated = mGenerator.multiple(nextId, size);

            if (!mEntities.containsKey(id)) {
                mEntities.put(id, new ArrayList<T>());
            }

            for (T entity: generated) {
                mEntitiesById.put(entity.getId(), entity);
                mEntities.get(id).add(entity);
            }
        }
    }
}