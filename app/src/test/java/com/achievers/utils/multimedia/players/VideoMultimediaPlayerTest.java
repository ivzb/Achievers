package com.achievers.utils.multimedia.players;

import com.achievers.R;
import com.achievers.utils.multimedia.players._base.AbstractMultimediaPlayerTest;
import com.achievers.utils.ui.multimedia.players.VideoMultimediaPlayer;
import com.google.android.exoplayer2.source.MediaSource;

import org.junit.Test;

import static com.achievers.utils.ui.multimedia.MultimediaControllerState.None;
import static com.achievers.utils.ui.multimedia.MultimediaControllerState.Stop;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class VideoMultimediaPlayerTest extends AbstractMultimediaPlayerTest {

    @Test(expected = NullPointerException.class)
    public void nullActionHandler() {
        new VideoMultimediaPlayer(null, mContext, mExoPlayer, mUrl);
    }

    @Test(expected = NullPointerException.class)
    public void nullContext() {
        new VideoMultimediaPlayer(mMultimediaActionHandler, null, mExoPlayer, mUrl);
    }

    @Test(expected = NullPointerException.class)
    public void nullExoPlayer() {
        new VideoMultimediaPlayer(mMultimediaActionHandler, mContext, null, mUrl);
    }

    @Test(expected = NullPointerException.class)
    public void nullUrl() {
        new VideoMultimediaPlayer(mMultimediaActionHandler, mContext, mExoPlayer, null);
    }

    @Test
    public void start() throws NullPointerException {
        // arrange
        initPlayer();

        // act
        mPlayer.start();

        // assert
        verify(mContext).getPackageName();
        verify(mContext).getPackageManager();
        verify(mContext).getApplicationContext();

        verify(mExoPlayer).addListener(mPlayer);
        verify(mExoPlayer).prepare(isA(MediaSource.class));
        verify(mExoPlayer).setPlayWhenReady(true);

        verify(mMultimediaActionHandler).changeState(None, 0);

        verify(mExoPlayerView).setPlayer(mExoPlayer);
    }

    @Test
    public void stop() {
        // arrange
        initPlayer();

        // act
        mPlayer.stop();

        // assert
        verify(mExoPlayer).removeListener(mPlayer);
        verify(mExoPlayer).setPlayWhenReady(false);

        verify(mMultimediaActionHandler).changeState(Stop, R.drawable.ic_play);
    }

    private void initPlayer() {
        when(mMultimediaActionHandler.getExoPlayerView()).thenReturn(mExoPlayerView);

        mPlayer = new VideoMultimediaPlayer(
                mMultimediaActionHandler,
                mContext,
                mExoPlayer,
                mUrl);

        verify(mMultimediaActionHandler).getExoPlayerView();
    }
}
