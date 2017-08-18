package com.achievers.sync;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;

import com.achievers.provider.AchieversContract;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
public class SyncIntentService extends IntentService {

    public SyncIntentService() {
        super("SyncIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Uri contentUri = intent.getData();

        if (AchieversContract.Categories.CONTENT_URI.equals(contentUri)) {
            SyncTask.syncCategories(this);
        }
    }
}