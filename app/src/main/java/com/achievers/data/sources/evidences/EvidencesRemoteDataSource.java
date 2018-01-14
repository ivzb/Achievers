package com.achievers.data.sources.evidences;

import android.support.annotation.NonNull;

import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.callbacks.SaveCallback;
import com.achievers.data.endpoints.EvidencesAPI;
import com.achievers.data.entities.Evidence;
import com.achievers.data.sources._base.BaseRemoteDataSource;

import java.util.List;

import retrofit2.Call;

/**
 * Implementation of remote network data source.
 */
public class EvidencesRemoteDataSource
        extends BaseRemoteDataSource<Evidence, EvidencesAPI>
        implements EvidencesDataSource {

    private static EvidencesDataSource sINSTANCE;

    public static EvidencesDataSource getInstance() {
        if (sINSTANCE == null) {
            sINSTANCE = new EvidencesRemoteDataSource();
        }

        return sINSTANCE;
    }

    public static void destroyInstance() {
        sINSTANCE = null;
    }

    // Prevent direct instantiation.
    private EvidencesRemoteDataSource() {
        super(EvidencesAPI.class);
    }

    @Override
    public void get(
            final String id,
            final @NonNull GetCallback<Evidence> callback) {

        final Call<Evidence> call = mApiService.get(id);
        call.enqueue(getCallback(callback));
    }

    @Override
    public void load(
            final String categoryId,
            final int page,
            final @NonNull LoadCallback<Evidence> callback) {

        final Call<List<Evidence>> call = mApiService.load(page);
        call.enqueue(loadCallback(page, callback));
    }

    @Override
    public void save(
            @NonNull Evidence achievement,
            @NonNull SaveCallback<String> callback) {

        final Call<String> call = mApiService.create(achievement);
        call.enqueue(saveCallback(callback));
    }
}
