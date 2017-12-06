package com.achievers.data.sources._base.mocks;

import android.support.annotation.NonNull;

import com.achievers.Config;
import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.entities._base.BaseModel;
import com.achievers.data.generators._base.contracts.BaseGenerator;
import com.achievers.data.sources._base.contracts.ReceiveDataSource;
import com.achievers.data.sources._base.contracts.mocks.SeedDataSource;

import java.util.List;

import static com.achievers.utils.Preconditions.checkNotNull;

public abstract class ReceiveMockDataSource<T extends BaseModel>
        extends GetMockDataSource<T>
        implements ReceiveDataSource<T>, SeedDataSource<T> {

    protected static int sPageSize = 9;
    protected static String sInvalidPageFailMessage = "Please provide non negative page.";

    public ReceiveMockDataSource(BaseGenerator<T> generator) {
        super(generator);
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

        List<T> entities = mEntitiesByContainerId.get(containerId);

        if (entities == null) {
            callback.onNoMore();
            return;
        }

        int start = page * sPageSize;
        int size = entities.size();
        boolean noMore = start > size || size == 0;
        int end = Math.min(start + sPageSize, size);

        if (noMore) {
            callback.onNoMore();
            return;
        }

        List<T> data = entities.subList(start, end);

        callback.onSuccess(data, page);
    }
}