package com.achievers.utils.multimedia.players;

import com.achievers.R;
import com.achievers.utils.multimedia.players._base.AbstractMultimediaPlayerTest;
import com.achievers.utils.ui.multimedia.players.VoiceMultimediaPlayer;
import com.google.android.exoplayer2.source.MediaSource;

import org.junit.Test;

import static com.achievers.utils.ui.multimedia.MultimediaControllerState.Play;
import static com.achievers.utils.ui.multimedia.MultimediaControllerState.Stop;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class VoiceMultimediaPlayerTest extends AbstractMultimediaPlayerTest {

    @Test(expected = NullPointerException.class)
    public void nullActionHandler() {
        new VoiceMultimediaPlayer(null, mContext, mExoPlayer, mUri);
    }

    @Test(expected = NullPointerException.class)
    public void nullContext() {
        new VoiceMultimediaPlayer(mMultimediaActionHandler, null, mExoPlayer, mUri);
    }

    @Test(expected = NullPointerException.class)
    public void nullExoPlayer() {
        new VoiceMultimediaPlayer(mMultimediaActionHandler, mContext, null, mUri);
    }

    @Test(expected = NullPointerException.class)
    public void nullUrl() {
        new VoiceMultimediaPlayer(mMultimediaActionHandler, mContext, mExoPlayer, null);
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

        mPlayer = new VoiceMultimediaPlayer(
                mMultimediaActionHandler,
                mContext,
                mExoPlayer,
                mUri);

        verify(mMultimediaActionHandler).getExoPlayerView();
    }
}
