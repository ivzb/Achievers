package com.achievers.data.sources.files;

import android.support.annotation.NonNull;

import com.achievers.data.callbacks.SaveCallback;
import com.achievers.data.entities.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.achievers.utils.Preconditions.checkNotNull;

public class FilesMockDataSource implements FilesDataSource {

    private static FilesMockDataSource sINSTANCE;
    private static String sNoFileFailMessage = "No file to save.";

    private List<File> mEntities;
    private HashMap<String, File> mEntitiesById;

    public static FilesMockDataSource getInstance() {
        checkNotNull(sINSTANCE);

        return sINSTANCE;
    }

    public static FilesMockDataSource createInstance() {
        sINSTANCE = new FilesMockDataSource();

        return getInstance();
    }

    public static void destroyInstance() {
        sINSTANCE = null;
    }

    private FilesMockDataSource() {
        mEntities = new ArrayList<>();
        mEntitiesById = new HashMap<>();
    }

    @Override
    public void storeFile(@NonNull File file, @NonNull SaveCallback<String> callback) {
        checkNotNull(callback);

        if (file == null) {
            callback.onFailure(sNoFileFailMessage);
            return;
        }

        file.setId(String.valueOf(mEntities.size() + 1));

        mEntitiesById.put(file.getId(), file);
        mEntities.add(file);

        callback.onSuccess("mock_url");
    }
}
