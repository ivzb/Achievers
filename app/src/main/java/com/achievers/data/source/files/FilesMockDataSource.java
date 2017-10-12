package com.achievers.data.source.files;

import android.support.annotation.NonNull;

import com.achievers.data.callbacks.SaveCallback;
import com.achievers.data.entities.File;
import com.achievers.utils.GeneratorUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.achievers.utils.Preconditions.checkNotNull;

public class FilesMockDataSource implements FilesDataSource {

    private static FilesMockDataSource sINSTANCE;
    private static String sNoFileFailMessage = "No file to save.";

    private List<File> mEntities;
    private HashMap<Long, File> mEntitiesById;

    public static FilesMockDataSource getInstance() {
        if (sINSTANCE == null) sINSTANCE = new FilesMockDataSource();

        return sINSTANCE;
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

        file.setId(mEntities.size() + 1);

        mEntitiesById.put(file.getId(), file);
        mEntities.add(file);

        String imageUrl = GeneratorUtils.getInstance().getImageUrl();

        callback.onSuccess(imageUrl);
    }
}
