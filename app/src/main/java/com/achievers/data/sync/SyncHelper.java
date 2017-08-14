package com.achievers.data.sync;


import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.support.annotation.Nullable;

import static com.achievers.util.LogUtils.makeLogTag;

/**
 * A helper class for dealing with conference data synchronization. All operations occur on the
 * thread they're called from, so it's best to wrap calls in an {@link android.os.AsyncTask}, or
 * better yet, a {@link android.app.Service}.
 */
public class SyncHelper {

    private static final String TAG = makeLogTag(SyncHelper.class);

    private Context mContext;

    /**
     *
     * @param context Can be Application, Activity or Service context.
     */
    public SyncHelper(Context context) {
        mContext = context;
    }

    /**
     * Attempts to perform data synchronization.
     * <p />
     *
     *
     * @param syncResult The sync result object to update with statistics.
     * @param extras Specifies additional information about the sync. This must contain key
     *               {@code SyncAdapter.EXTRA_SYNC_USER_DATA_ONLY} with boolean value
     * @return true if the sync changed the data.
     */
    public boolean performSync(@Nullable SyncResult syncResult, Bundle extras) {
        boolean dataChanged = false;

        // todo

        return dataChanged;
    }
}