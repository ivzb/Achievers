package com.achievers.data.source.local;

import android.support.annotation.NonNull;

import com.achievers.data.Category;
import com.achievers.data.source.CategoriesDataSource;
import com.achievers.data.source.callbacks.GetCallback;
import com.achievers.data.source.callbacks.LoadCallback;
import com.achievers.data.source.callbacks.SaveCallback;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

import static com.google.common.base.Preconditions.checkNotNull;

public class CategoriesLocalDataSource implements CategoriesDataSource {

    private static CategoriesLocalDataSource INSTANCE;
    private Realm mRealm;

    // Prevent direct instantiation.
    private CategoriesLocalDataSource(@NonNull Realm realm) {
        checkNotNull(realm);
        this.mRealm = realm;
    }

    public static CategoriesLocalDataSource getInstance(@NonNull Realm realm) {
        if (INSTANCE == null) {
            INSTANCE = new CategoriesLocalDataSource(realm);
        }

        return INSTANCE;
    }

    @Override
    public void loadCategories(
            final Integer parentId,
            @NonNull final LoadCallback<List<Category>> callback
    ) {
        RealmResults<Category> realmResults = this.mRealm
                .where(Category.class)
                .equalTo("parent.id", parentId)
                .findAll()
                .sort("createdOn", Sort.DESCENDING);

        List<Category> categories = this.mRealm.copyFromRealm(realmResults);

        if (categories.isEmpty()) {
            callback.onNoMoreData();
            return;
        }

        callback.onSuccess(categories);
    }

    @Override
    public void getCategory(
            @NonNull final Integer categoryId,
            @NonNull GetCallback<Category> callback
    ) {
        Category category = this.mRealm
                .where(Category.class)
                .equalTo("id", categoryId)
                .findFirst();

        if (category == null) {
            callback.onFailure(null);
        }

        callback.onSuccess(category);
    }

    @Override
    public void refreshCache() {
        // Not required because the {@link CategoriesRepository} handles the logic of refreshing the
        // categories from all the available data sources.
    }

    @Override
    public void saveCategories(
            @NonNull final List<Category> categories,
            @NonNull final SaveCallback<Void> callback
    ) {
        this.mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(categories);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callback.onSuccess(null);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                callback.onFailure(error.getMessage());
            }
        });
    }
}