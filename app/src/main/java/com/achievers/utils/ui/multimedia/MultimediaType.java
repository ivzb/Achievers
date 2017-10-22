package com.achievers.utils.ui.multimedia;

import com.achievers.R;

public enum MultimediaType {
    Photo(1, 0, 0, 0),
    Video(2, R.layout.multimedia_video_view, R.drawable.ic_play, R.drawable.ic_pause),
    Audio(3, 0, R.drawable.ic_music_note, R.drawable.ic_pause);

    private int mId;
    private int mPlayerLayout;
    private int mPlayResource;
    private int mPauseResource;

    MultimediaType(int id, int playerLayout, int playResource, int pauseResource) {
        mId = id;
        mPlayerLayout = playerLayout;
        mPlayResource = playResource;
        mPauseResource = pauseResource;
    }

    public int getId() {
        return mId;
    }

    public int getPlayerLayout() {
        return mPlayerLayout;
    }

    public int getPlayResource() {
        return mPlayResource;
    }

    public int getPauseResource() {
        return mPauseResource;
    }
}