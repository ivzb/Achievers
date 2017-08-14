package com.achievers.data.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

import com.achievers.BuildConfig;

import static com.achievers.util.LogUtils.LOGE;
import static com.achievers.util.LogUtils.LOGI;
import static com.achievers.util.LogUtils.makeLogTag;

/**
 * Used for download sync only.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {
    private static final String TAG = makeLogTag(SyncAdapter.class);

    private final Context mContext;

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mContext = context;

        //noinspection ConstantConditions,PointlessBooleanExpression
        if (!BuildConfig.DEBUG) {
            Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread thread, Throwable throwable) {
                    LOGE(TAG, "Uncaught sync exception, suppressing UI in release build.",
                            throwable);
                }
            });
        }
    }

    @Override
    public void onPerformSync(final Account account, Bundle extras, String authority,
                              final ContentProviderClient provider, final SyncResult syncResult) {
        final boolean uploadOnly = extras.getBoolean(ContentResolver.SYNC_EXTRAS_UPLOAD, false);
        final boolean initialize = extras.getBoolean(ContentResolver.SYNC_EXTRAS_INITIALIZE, false);

        // This Adapter is declared not to support uploading in its xml file.
        // {@code ContentResolver.SYNC_EXTRAS_UPLOAD} is set by the system in some cases, but never
        // by the app.
        if (uploadOnly) {
            return;
        }

        LOGI(TAG, "Beginning sync," +
                " uploadOnly=" + uploadOnly +
                " initialize=" + initialize);

        // Sync from bootstrap and remote data, as needed
        new SyncHelper(mContext).performSync(syncResult, extras);
    }

}
