package com.achievers.utils.multimedia._base;

import android.content.Context;
import android.net.Uri;

import com.achievers.databinding.MultimediaViewBinding;
import com.achievers.ui._base._contracts.action_handlers.BaseActionHandler;
import com.achievers.ui._base._contracts.action_handlers.BaseMultimediaActionHandler;
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

@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractMultimediaViewTest {

    @Mock protected Context mContext;
    @Mock protected MultimediaViewBinding mBinding;

    @Mock protected BaseMultimediaViewModel mViewModel;
    @Mock protected BaseMultimediaActionHandler mActionHandler;
    @Mock protected BaseMultimediaPlayer mPlayer;

    protected BaseMultimediaView mView;

    protected MultimediaType mDefaultType;
    protected String mDefaultUrl;
    protected Uri mDefaultUri;
    protected boolean mDefaultPlaying;
    protected BaseMultimediaActionHandler mDefaultMultimediaActionHandler;
    protected BaseMultimediaPlayer mDefaultPlayer;
    protected MultimediaControllerState mDefaultControllerState;

    @Before
    public void before() throws Exception {
        mView = new MultimediaView(mContext, mBinding, mViewModel);
        mDefaultType = Photo;
        mDefaultUrl = null;
        mDefaultUri = null;
        mDefaultPlaying = false;
        mDefaultMultimediaActionHandler = null;
        mDefaultPlayer = null;
        mDefaultControllerState = None;

        verify(mBinding).setViewModel(mViewModel);
    }

    @After
    public void after() {
        verifyNoMoreInteractions(mContext);
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
                .withUrl(mDefaultUrl)
                .withUri(mDefaultUri)
                .withActionHandler(mDefaultMultimediaActionHandler)
                .withPlayer(mDefaultPlayer)
                .build();
    }

    private void verifyViewModel() {
        verify(mViewModel).getType();
        verify(mViewModel).setType(mDefaultType);

        verify(mViewModel).setUrl(mDefaultUrl);
        verify(mViewModel).setUri(mDefaultUri);

        verify(mViewModel).setPlaying(mDefaultPlaying);
        verify(mViewModel).setActionHandler(isA(BaseActionHandler.class));

        verify(mViewModel).setControllerState(mDefaultControllerState);

        verify(mViewModel).setMultimediaActionHandler(mDefaultMultimediaActionHandler);
        verify(mViewModel).setPlayer(mDefaultPlayer);
    }
}