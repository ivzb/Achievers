package com.achievers.data.source._base;

import android.support.annotation.NonNull;

import com.achievers.data.callbacks.SaveCallback;
import com.achievers.data.entities._base.BaseModel;
import com.achievers.generator._base.BaseGenerator;

import java.util.ArrayList;
import java.util.Date;

import static com.achievers.utils.Preconditions.checkNotNull;

public abstract class AbstractDataSource<T extends BaseModel>
        extends AbstractReceiveDataSource<T>
        implements BaseDataSource<T> {

    private static String sNoEntityFailMessage = "No entity to save.";

    public AbstractDataSource(BaseGenerator<T> generator) {
        super(generator);
    }

    @Override
    public void save(
            @NonNull T entity,
            @NonNull SaveCallback<Long> callback) {

        checkNotNull(callback);

        if (entity == null) {
            callback.onFailure(sNoEntityFailMessage);
            return;
        }

        entity.setId(mEntities.size() + 1);
        entity.setCreatedOn(new Date());

        mEntitiesById.put(entity.getId(), entity);

        if (!mEntities.containsKey(entity.getContainerId())) {
            mEntities.put(entity.getContainerId(), new ArrayList<T>());
        }

        mEntities.get(entity.getContainerId()).add(entity);

        callback.onSuccess(entity.getId());
    }
}