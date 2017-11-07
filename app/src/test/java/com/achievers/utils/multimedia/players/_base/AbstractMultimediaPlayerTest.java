package com.achievers.utils.multimedia.players._base;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.achievers.utils.ui.multimedia._base.BaseMultimediaViewActionHandler;
import com.achievers.utils.ui.multimedia.players.ExoMultimediaPlayer;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractMultimediaPlayerTest {

    @Mock protected BaseMultimediaViewActionHandler mMultimediaActionHandler;
    @Mock protected Context mContext;
    @Mock protected SimpleExoPlayer mExoPlayer;
    @Mock protected SimpleExoPlayerView mExoPlayerView;
    @Mock protected PackageManager mPackageManager;

    @Mock protected Uri mUri;
    protected ExoMultimediaPlayer mPlayer;

    @Before
    public void before() throws Exception {
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
}