package com.achievers.sync;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

public class SyncUtils {

    /**
     * Helper method to perform a sync immediately using an IntentService for asynchronous
     * execution.
     *
     * @param context The Context used to start the IntentService for the sync.
     */
    public static void startSync(@NonNull final Context context, Uri contentUri) {
        Intent intentToSyncImmediately = new Intent(context, SyncIntentService.class);
        intentToSyncImmediately.setData(contentUri);
        context.startService(intentToSyncImmediately);
    }
}