package com.achievers.utils.ui.multimedia;

import com.achievers.R;

public enum MultimediaType {
    Photo(1, 0, 0),
    Video(2, 0, R.drawable.ic_play),
    Audio(3, R.drawable.ic_stop, R.drawable.ic_music_note);

    private int mId;
    private int mPlayDrawable;
    private int mStopDrawable;

    MultimediaType(int id, int playDrawable, int stopDrawable) {
        mId = id;
        mPlayDrawable = playDrawable;
        mStopDrawable = stopDrawable;
    }

    public int getId() {
        return mId;
    }

    public int getPlayDrawable() {
        return mPlayDrawable;
    }

    public int getStopDrawable() {
        return mStopDrawable;
    }
}