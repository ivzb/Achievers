package com.achievers.data.source.local;

import android.support.annotation.NonNull;

import com.achievers.data.Category;
import com.achievers.data.source.CategoriesDataSource;

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

    /**
     * Note: {@link LoadCategoriesCallback#onDataNotAvailable()} is fired if the database doesn't exist
     * or the table is empty.
     */
    @Override
    public void loadCategories(Integer parentId, @NonNull LoadCategoriesCallback callback) {
        RealmResults<Category> realmResults = this.mRealm
                .where(Category.class)
                .equalTo("parent.id", parentId)
                .findAll()
                .sort("createdOn", Sort.DESCENDING);

        List<Category> categories = this.mRealm.copyFromRealm(realmResults);

        if (categories.isEmpty()) {
            // This will be called if the table is new or just empty.
            callback.onDataNotAvailable();
        } else {
            callback.onLoaded(categories);
        }
    }

    /**
     * Note: {@link GetCategoryCallback#onDataNotAvailable()} is fired if the {@link Category} isn't
     * found.
     */
    @Override
    public void getCategory(@NonNull Integer categoryId, @NonNull GetCategoryCallback callback) {
        Category category = this.mRealm
                .where(Category.class)
                .equalTo("id", categoryId)
                .findFirst();

        if (category != null) {
            callback.onLoaded(category);
        } else {
            callback.onDataNotAvailable();
        }
    }

    @Override
    public void refreshCache() {
        // Not required because the {@link CategoriesRepository} handles the logic of refreshing the
        // categories from all the available data sources.
    }

    @Override
    public void saveCategory(@NonNull final Category category) {
        this.mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(category);
            }
        });
    }
}