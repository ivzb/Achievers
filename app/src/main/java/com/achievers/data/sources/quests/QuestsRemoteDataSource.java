package com.achievers.data.sources.quests;

import android.support.annotation.NonNull;

import com.achievers.data.Result;
import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.endpoints.QuestsAPI;
import com.achievers.data.entities.Quest;
import com.achievers.data.sources._base.BaseRemoteDataSource;

import java.util.List;

import retrofit2.Call;

public class QuestsRemoteDataSource
    extends BaseRemoteDataSource<Quest, QuestsAPI>
    implements QuestsDataSource {

    private static QuestsDataSource sINSTANCE;

    public static QuestsDataSource getInstance() {
        if (sINSTANCE == null) {
            sINSTANCE = new QuestsRemoteDataSource();
        }

        return sINSTANCE;
    }

    public static void destroyInstance() {
        sINSTANCE = null;
    }

    // Prevent direct instantiation.
    private QuestsRemoteDataSource() {
        super(QuestsAPI.class);
    }

    @Override
    public void get(
            final String id,
            final @NonNull GetCallback<Quest> callback) {

        final Call<Result<Quest>> call = mApiService.get(id);
        call.enqueue(getCallback(callback));
    }

    @Override
    public void load(
            final String _,
            final int page,
            final @NonNull LoadCallback<Quest> callback) {

        final Call<Result<List<Quest>>> call = mApiService.load(page);
        call.enqueue(loadCallback(page, callback));
    }
}
