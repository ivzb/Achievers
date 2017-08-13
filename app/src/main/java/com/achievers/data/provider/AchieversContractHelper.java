package com.achievers.data.provider;

import android.net.Uri;
import android.text.TextUtils;

/**
 * Provides helper methods for specifying query parameters on {@code Uri}s.
 */
public class AchieversContractHelper {

    public static final String QUERY_PARAMETER_DISTINCT = "distinct";

    private static final String QUERY_PARAMETER_OVERRIDE_ACCOUNT_NAME = "overrideAccountName";

    private static final String QUERY_PARAMETER_CALLER_IS_SYNC_ADAPTER = "callerIsSyncAdapter";


    public static boolean isUriCalledFromSyncAdapter(Uri uri) {
        return uri.getBooleanQueryParameter(QUERY_PARAMETER_CALLER_IS_SYNC_ADAPTER, false);
    }

    public static Uri setUriAsCalledFromSyncAdapter(Uri uri) {
        return uri.buildUpon().appendQueryParameter(QUERY_PARAMETER_CALLER_IS_SYNC_ADAPTER, "true")
                .build();
    }

    public static boolean isQueryDistinct(Uri uri){
        return !TextUtils.isEmpty(uri.getQueryParameter(QUERY_PARAMETER_DISTINCT));
    }

    public static String formatQueryDistinctParameter(String parameter){
        return AchieversContractHelper.QUERY_PARAMETER_DISTINCT + " " + parameter;
    }

    public static String getOverrideAccountName(Uri uri) {
        return uri.getQueryParameter(QUERY_PARAMETER_OVERRIDE_ACCOUNT_NAME);
    }

    /**
     * Adds an account override parameter to the {@code uri}. This is used by the
     * {@link AchieversProvider} when fetching account-specific data.
     */
    public static Uri addOverrideAccountName(Uri uri, String accountName) {
        return uri.buildUpon().appendQueryParameter(
                QUERY_PARAMETER_OVERRIDE_ACCOUNT_NAME, accountName).build();
    }
}