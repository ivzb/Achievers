package com.achievers.utils.ui.multimedia;

import com.achievers.R;

public enum MultimediaType {
    Photo(1, R.drawable.ic_photo, 0, 0),
    Video(2, R.drawable.ic_video, 0, R.drawable.ic_play),
    Voice(3, R.drawable.ic_voice, R.drawable.ic_stop, R.drawable.ic_music_note);

    private int mId;
    private int mDrawable;
    private int mPlayDrawable;
    private int mStopDrawable;

    MultimediaType(int id, int drawable, int playDrawable, int stopDrawable) {
        mId = id;
        mDrawable = drawable;
        mPlayDrawable = playDrawable;
        mStopDrawable = stopDrawable;
    }

    public int getId() {
        return mId;
    }

    public int getDrawable() {
        return mDrawable;
    }

    public int getPlayDrawable() {
        return mPlayDrawable;
    }

    public int getStopDrawable() {
        return mStopDrawable;
    }
}