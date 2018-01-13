package com.achievers.data.sources.categories;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.entities.Category;
import com.achievers.data.sources._base.BaseRepository;

import java.util.List;

import static com.achievers.utils.Preconditions.checkNotNull;

public class CategoriesRepository extends BaseRepository<CategoriesDataSource> implements CategoriesDataSource {

    private static CategoriesDataSource INSTANCE = null;

    private CategoriesRepository(
            @NonNull CategoriesDataSource remoteDataSource,
            @NonNull CategoriesDataSource localDataSource) {

        super(remoteDataSource, localDataSource);
    }

    public static CategoriesDataSource getInstance(
            CategoriesDataSource remoteDataSource,
            CategoriesDataSource localDataSource) {

        if (INSTANCE == null) {
            INSTANCE = new CategoriesRepository(remoteDataSource, localDataSource);
        }

        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void get(int id, @NonNull final GetCallback<Category> callback) {
        checkNotNull(callback);

        mRemoteDataSource.get(id, callback);
    }

    @Override
    public void load(final Long parentId) { // , @NonNull final LoadCallback<Category> callback) {
//        checkNotNull(callback);

        // Load from server
//        mRemoteDataSource.load(parentId, new LoadCallback<Category>() {
//            @Override
//            public void onSuccess(List<Category> categories) {
//                save(categories);
////                callback.onSuccess(null);
//            }
//
//            @Override
//            public void onFailure(String message) {
////                callback.onFailure(message);
//            }
//        });
    }

    @Override
    public void save(@NonNull List<Category> categories) {
        checkNotNull(categories);

        mLocalDataSource.save(categories);
    }

    public interface LoadDataCallback {
        void onDataLoaded(Cursor data);

        void onDataEmpty();

        void onDataNotAvailable();

        void onDataReset();
    }
}
