package com.achievers.data.sources._base.mocks;

import android.support.annotation.NonNull;

import com.achievers.data.callbacks.SaveCallback;
import com.achievers.data.entities._base.BaseModel;
import com.achievers.data.generators._base.contracts.BaseGenerator;
import com.achievers.data.sources._base.contracts.BaseDataSource;

import java.util.ArrayList;
import java.util.Date;

import static com.achievers.utils.Preconditions.checkNotNull;

public abstract class BaseMockDataSource<T extends BaseModel>
        extends ReceiveMockDataSource<T>
        implements BaseDataSource<T> {

    protected static String sNoEntityFailMessage = "No entity to save.";

    public BaseMockDataSource(BaseGenerator<T> generator) {
        super(generator);
    }

    @Override
    public void save(
            @NonNull T entity,
            @NonNull SaveCallback<String> callback) {

        checkNotNull(callback);

        if (entity == null) {
            callback.onFailure(sNoEntityFailMessage);
            return;
        }

        entity.setId(String.valueOf(mEntitiesByContainerId.size() + 1));
        entity.setCreatedAt(new Date());

        mEntitiesById.put(entity.getId(), entity);

        if (!mEntitiesByContainerId.containsKey(entity.getContainerId())) {
            mEntitiesByContainerId.put(entity.getContainerId(), new ArrayList<T>());
        }

        mEntitiesByContainerId.get(entity.getContainerId()).add(entity);

        callback.onSuccess(entity.getId());
    }
}