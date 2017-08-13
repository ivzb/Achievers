package com.achievers.data.provider;

import android.content.UriMatcher;
import android.net.Uri;
import android.util.SparseArray;

/**
 * Provides methods to match a {@link android.net.Uri} to a {@link AchieversUriEnum}.
 * <p />
 * All methods are thread safe, except {@link #buildUriMatcher()} and {@link #buildEnumsMap()},
 * which is why they are called only from the constructor.
 */
public class AchieversProviderUriMatcher {

    /**
     * All methods on a {@link UriMatcher} are thread safe, except {@code addURI}.
     */
    private UriMatcher mUriMatcher;

    private SparseArray<AchieversUriEnum> mEnumsMap = new SparseArray<>();

    /**
     * This constructor needs to be called from a thread-safe method as it isn't thread-safe itself.
     */
    public AchieversProviderUriMatcher(){
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        buildUriMatcher();
    }

    private void buildUriMatcher() {
        final String authority = AchieversContract.CONTENT_AUTHORITY;

        for (AchieversUriEnum uri: AchieversUriEnum.values()) {
            mUriMatcher.addURI(authority, uri.path, uri.code);
        }

        buildEnumsMap();
    }

    private void buildEnumsMap() {
        for (AchieversUriEnum uri: AchieversUriEnum.values()) {
            mEnumsMap.put(uri.code, uri);
        }
    }

    /**
     * Matches a {@code uri} to a {@link AchieversUriEnum}.
     *
     * @return the {@link AchieversUriEnum}, or throws new UnsupportedOperationException if no match.
     */
    public AchieversUriEnum matchUri(Uri uri){
        final int code = mUriMatcher.match(uri);

        try {
            return matchCode(code);
        } catch (UnsupportedOperationException e){
            throw new UnsupportedOperationException("Unknown uri " + uri);
        }
    }

    /**
     * Matches a {@code code} to a {@link AchieversUriEnum}.
     *
     * @return the {@link AchieversUriEnum}, or throws new UnsupportedOperationException if no match.
     */
    public AchieversUriEnum matchCode(int code){
        AchieversUriEnum AchieversUriEnum = mEnumsMap.get(code);
        if (AchieversUriEnum != null){
            return AchieversUriEnum;
        } else {
            throw new UnsupportedOperationException("Unknown uri with code " + code);
        }
    }
}