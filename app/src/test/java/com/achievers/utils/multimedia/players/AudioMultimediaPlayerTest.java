package com.achievers.utils.multimedia.players;

import com.achievers.R;
import com.achievers.utils.multimedia.players._base.AbstractMultimediaPlayerTest;
import com.achievers.utils.ui.multimedia.players.AudioMultimediaPlayer;
import com.google.android.exoplayer2.source.MediaSource;

import org.junit.Test;

import static com.achievers.utils.ui.multimedia.MultimediaControllerState.Play;
import static com.achievers.utils.ui.multimedia.MultimediaControllerState.Stop;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AudioMultimediaPlayerTest extends AbstractMultimediaPlayerTest {

    @Test(expected = NullPointerException.class)
    public void nullActionHandler() {
        new AudioMultimediaPlayer(null, mContext, mExoPlayer, mUrl);
    }

    @Test(expected = NullPointerException.class)
    public void nullContext() {
        new AudioMultimediaPlayer(mMultimediaActionHandler, null, mExoPlayer, mUrl);
    }

    @Test(expected = NullPointerException.class)
    public void nullExoPlayer() {
        new AudioMultimediaPlayer(mMultimediaActionHandler, mContext, null, mUrl);
    }

    @Test(expected = NullPointerException.class)
    public void nullUrl() {
        new AudioMultimediaPlayer(mMultimediaActionHandler, mContext, mExoPlayer, null);
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

        verify(mMultimediaActionHandler).changeState(Play, R.drawable.ic_stop);
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

        verify(mMultimediaActionHandler).changeState(Stop, R.drawable.ic_music_note);
    }

    private void initPlayer() {
        when(mMultimediaActionHandler.getExoPlayerView()).thenReturn(mExoPlayerView);

        mPlayer = new AudioMultimediaPlayer(
                mMultimediaActionHandler,
                mContext,
                mExoPlayer,
                mUrl);

        verify(mMultimediaActionHandler).getExoPlayerView();
    }
}
