package com.achievers.utils.multimedia._base;

import android.content.Context;
import android.content.res.Resources;

import com.achievers.databinding.MultimediaViewBinding;
import com.achievers.ui._base.contracts.action_handlers.BaseActionHandler;
import com.achievers.ui._base.contracts.action_handlers.BaseMultimediaActionHandler;
import com.achievers.utils.ui.multimedia.MultimediaControllerState;
import com.achievers.utils.ui.multimedia.MultimediaType;
import com.achievers.utils.ui.multimedia.MultimediaView;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaPlayer;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaView;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.achievers.utils.ui.multimedia.MultimediaControllerState.None;
import static com.achievers.utils.ui.multimedia.MultimediaType.Photo;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractMultimediaViewTest {

    @Mock protected Context mContext;
    @Mock private Resources mResources;
    @Mock protected MultimediaViewBinding mBinding;

    @Mock protected BaseMultimediaViewModel mViewModel;
    @Mock protected BaseMultimediaActionHandler mActionHandler;
    @Mock protected BaseMultimediaPlayer mPlayer;

    protected BaseMultimediaView mView;

    protected MultimediaType mDefaultType;
    protected String mDefaultPreviewUrl;
    protected boolean mDefaultPlaying;
    protected BaseMultimediaActionHandler mDefaultMultimediaActionHandler;
    protected BaseMultimediaPlayer mDefaultPlayer;
    protected MultimediaControllerState mDefaultControllerState;

    @Before
    public void before() throws Exception {
        when(mContext.getResources()).thenReturn(mResources);

        mView = new MultimediaView(mContext, mBinding, mViewModel);
        mDefaultType = Photo;
        mDefaultPreviewUrl = null;
        mDefaultPlaying = false;
        mDefaultMultimediaActionHandler = null;
        mDefaultPlayer = null;
        mDefaultControllerState = None;

        verify(mBinding).setViewModel(mViewModel);
    }

    @After
    public void after() {
        verify(mContext).getResources();
        verify(mViewModel).setResources(mResources);

        verifyNoMoreInteractions(mContext);
        verifyNoMoreInteractions(mResources);
        verifyNoMoreInteractions(mBinding);
        verifyNoMoreInteractions(mViewModel);
        verifyNoMoreInteractions(mActionHandler);
        verifyNoMoreInteractions(mPlayer);
    }

    protected void buildAndVerify() {
        build();
        verifyViewModel();
    }

    private void build() {
        mView.builder(mDefaultType)
                .withPreviewUrl(mDefaultPreviewUrl)
                .withActionHandler(mDefaultMultimediaActionHandler)
                .withPlayer(mDefaultPlayer)
                .build();
    }

    private void verifyViewModel() {
        verify(mViewModel).getType();
        verify(mViewModel).setType(mDefaultType);

        verify(mViewModel).setPreviewUrl(mDefaultPreviewUrl);
        verify(mViewModel).setPlaying(mDefaultPlaying);
        verify(mViewModel).setActionHandler(isA(BaseActionHandler.class));

        verify(mViewModel).setControllerState(mDefaultControllerState);

        verify(mViewModel).setMultimediaActionHandler(mDefaultMultimediaActionHandler);
        verify(mViewModel).setPlayer(mDefaultPlayer);
    }
}