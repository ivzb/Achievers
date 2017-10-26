package com.achievers.utils.multimedia.players;

import android.content.Context;
import android.content.pm.PackageManager;

import com.achievers.utils.ui.multimedia._base.BaseMultimediaViewActionHandler;
import com.achievers.utils.ui.multimedia.players.AudioMultimediaPlayer;
import com.achievers.utils.ui.multimedia.players.ExoMultimediaPlayer;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.achievers.utils.ui.multimedia.MultimediaControllerState.Play;
import static com.achievers.utils.ui.multimedia.MultimediaControllerState.Stop;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AudioMultimediaPlayerTest {

    @Mock private BaseMultimediaViewActionHandler mMultimediaActionHandler;
    @Mock private Context mContext;
    @Mock private SimpleExoPlayer mExoPlayer;
    @Mock private SimpleExoPlayerView mExoPlayerView;
    @Mock private PackageManager mPackageManager;

    private String mUrl;
    private ExoMultimediaPlayer mPlayer;

    @Before
    public void before() throws Exception {
        mUrl = "url";

        when(mPackageManager.getPackageInfo(anyString(), anyInt()))
                .thenThrow(PackageManager.NameNotFoundException.class);

        when(mContext.getPackageName()).thenReturn("packageName");
        when(mContext.getPackageManager()).thenReturn(mPackageManager);
    }

    @After
    public void after() {
        verifyNoMoreInteractions(mMultimediaActionHandler);
        verifyNoMoreInteractions(mContext);
        verifyNoMoreInteractions(mExoPlayer);
        verifyNoMoreInteractions(mExoPlayerView);
    }

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

        verify(mMultimediaActionHandler).changeState(Play);
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

        verify(mMultimediaActionHandler).changeState(Stop);
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
