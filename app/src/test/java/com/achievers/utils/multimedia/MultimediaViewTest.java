package com.achievers.utils.multimedia;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.view.LayoutInflater;

import com.achievers.AchieversDebugTestApplication;
import com.achievers.BuildConfig;
import com.achievers.R;
import com.achievers.databinding.MultimediaViewBinding;
import com.achievers.ui._base.contracts.action_handlers.BaseActionHandler;
import com.achievers.ui._base.contracts.action_handlers.BaseMultimediaActionHandler;
import com.achievers.ui._base.contracts.multimedia.BaseMultimediaPlayer;
import com.achievers.ui.achievement.AchievementActivity;
import com.achievers.ui.achievements.AchievementsActivity;
import com.achievers.utils.GeneratorUtils;
import com.achievers.utils.ui.multimedia.MultimediaType;
import com.achievers.utils.ui.multimedia.MultimediaView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.robolectric.Robolectric;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MultimediaViewTest {

    @Mock private Context mContext;
    @Mock private Resources mResources;
    @Mock private MultimediaViewBinding mBinding;
    @Mock private BaseMultimediaActionHandler mActionHandler;
    @Mock private BaseMultimediaPlayer mPlayer;

    @Captor protected ArgumentCaptor<MultimediaView> mMultimediaActionCaptor;

    private MultimediaView mMultimediaView;

    @Before
    public void before() throws Exception {
        when(mContext.getResources()).thenReturn(mResources);

        mMultimediaView = new MultimediaView(mContext, mBinding);
    }

    @After
    public void after() {

    }

    @Test(expected = NullPointerException.class)
    public void nullType_shouldThrow() {
        mMultimediaView.builder(null);
    }

    @Test
    public void build_allTypes_withNothing() {
        // arrange
        MultimediaType[] types = MultimediaType.values();
        int size = types.length;

        for (MultimediaType type: types) {
            // act
            mMultimediaView.builder(type).build();

            // assert
            verify(mBinding).setType(type);
        }

        verify(mContext, times(size)).getResources();
        verify(mBinding, times(size)).setPreviewUrl(null);
        verify(mBinding, times(size)).setActionHandler(isA(BaseActionHandler.class));

        verify(mBinding, times(size)).setIsPlaying(false);
        verify(mBinding, times(size)).setShowControls(false);
        verify(mBinding, times(size)).setPlayResource(0);
        verify(mBinding, times(size)).setPauseResource(0);

        verify(mBinding, times(size)).setResources(mResources);
        verify(mBinding, times(size)).executePendingBindings();

        verifyNoMoreInteractions(mContext);
        verifyNoMoreInteractions(mResources);
        verifyNoMoreInteractions(mBinding);
        verifyNoMoreInteractions(mActionHandler);
        verifyNoMoreInteractions(mPlayer);
    }

    @Test
    public void build_withPreviewUrl() {
        // arrange
        MultimediaType type = MultimediaType.Photo;
        String previewUrl = "preview-url.com";

        // act
        mMultimediaView.builder(type)
                .withPreviewUrl(previewUrl)
                .build();

        // assert
        verify(mBinding).setPreviewUrl(previewUrl);
    }

    @Test
    public void build_withControlsUrl() {
        // arrange
        MultimediaType type = MultimediaType.Photo;

        // act
        mMultimediaView.builder(type)
                .withControls(true)
                .build();

        // assert
        verify(mBinding).setShowControls(true);
    }

    @Test
    public void build_withPlayResource() {
        // arrange
        MultimediaType type = MultimediaType.Photo;

        // act
        mMultimediaView.builder(type)
                .withPlayResource(17)
                .build();

        // assert
        verify(mBinding).setPlayResource(17);
    }

    @Test
    public void build_withPauseResource() {
        // arrange
        MultimediaType type = MultimediaType.Photo;

        // act
        mMultimediaView.builder(type)
                .withPauseResource(32)
                .build();

        // assert
        verify(mBinding).setPauseResource(32);
    }

    @Test
    public void click_withoutPlayer() {
        // arrange
        MultimediaType type = MultimediaType.Photo;

        // act
        mMultimediaView.builder(type)
                .withActionHandler(mActionHandler)
//                .withPlayer(mPlayer)
                .build();

        mMultimediaView.onClick();

        // assert
//        verify(mBinding).setActionHandler(mActionHandler);
        verify(mBinding).setIsPlaying(true);
        verify(mBinding, times(3)).executePendingBindings();

        verify(mActionHandler).onMultimediaAction(mMultimediaActionCaptor.capture());

        assertEquals(mMultimediaActionCaptor.getValue(), mMultimediaView);

        assertTrue(mMultimediaView.isPlaying());
    }

    @Test
    public void clickTwice_withoutPlayer() {
        // arrange
        MultimediaType type = MultimediaType.Photo;

        // act
        mMultimediaView.builder(type)
                .withActionHandler(mActionHandler)
//                .withPlayer(mPlayer)
                .build();

        mMultimediaView.onClick();
        mMultimediaView.onClick();

        // assert
//        verify(mBinding).setActionHandler(mActionHandler);
        verify(mBinding, times(2)).setIsPlaying(false);
        verify(mBinding, times(4)).executePendingBindings();

        verify(mActionHandler, times(2)).onMultimediaAction(mMultimediaActionCaptor.capture());

        assertEquals(mMultimediaActionCaptor.getValue(), mMultimediaView);

        assertFalse(mMultimediaView.isPlaying());
    }

    @Test
    public void click_withPlayer() {
        // arrange
        MultimediaType type = MultimediaType.Photo;

        // act
        mMultimediaView.builder(type)
                .withActionHandler(mActionHandler)
//                .withPlayer(mPlayer)
                .build();

        mMultimediaView.onClick();

        // assert
//        verify(mBinding).setActionHandler(mActionHandler);
        verify(mBinding).setIsPlaying(true);
        verify(mBinding, times(3)).executePendingBindings();

        verify(mActionHandler).onMultimediaAction(mMultimediaActionCaptor.capture());

        assertEquals(mMultimediaActionCaptor.getValue(), mMultimediaView);

        assertTrue(mMultimediaView.isPlaying());
    }
}
