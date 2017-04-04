package com.achievers.data.source.local;

import android.content.Context;
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

    // Prevent direct instantiation.
    private CategoriesLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
    }

    public static CategoriesLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new CategoriesLocalDataSource(context);
        }

        return INSTANCE;
    }

    /**
     * Note: {@link LoadCategoriesCallback#onDataNotAvailable()} is fired if the database doesn't exist
     * or the table is empty.
     */
    @Override
    public void getCategories(Integer parentId, @NonNull LoadCategoriesCallback callback) {
        RealmResults<Category> realmResults = Realm.getDefaultInstance()
                .where(Category.class)
                .equalTo("parent.id", parentId)
                .findAll()
                .sort("createdOn", Sort.DESCENDING);

        List<Category> categories = Realm.getDefaultInstance().copyFromRealm(realmResults);

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
    public void getCategory(@NonNull int categoryId, @NonNull GetCategoryCallback callback) {
        Category category = Realm.getDefaultInstance()
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
    public void refreshCategories(List<Category> categories) {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(Category.class)
                    .findAll()
                    .deleteAllFromRealm();
            }
        });
    }
}
