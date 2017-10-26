package com.achievers.utils.multimedia;

import android.content.Context;
import android.content.res.Resources;

import com.achievers.databinding.MultimediaViewBinding;
import com.achievers.ui._base.contracts.action_handlers.BaseActionHandler;
import com.achievers.ui._base.contracts.action_handlers.BaseMultimediaActionHandler;
import com.achievers.utils.ui.multimedia.MultimediaControllerState;
import com.achievers.utils.ui.multimedia.MultimediaType;
import com.achievers.utils.ui.multimedia.MultimediaView;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaBuilder;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaPlayer;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaView;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.achievers.utils.ui.multimedia.MultimediaControllerState.None;
import static com.achievers.utils.ui.multimedia.MultimediaControllerState.Play;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MultimediaViewTest {

    @Mock private Context mContext;
    @Mock private Resources mResources;
    @Mock private MultimediaViewBinding mBinding;

    @Mock private BaseMultimediaViewModel mViewModel;
    @Mock private BaseMultimediaActionHandler mActionHandler;
    @Mock private BaseMultimediaPlayer mPlayer;

    private BaseMultimediaView mView;

    private MultimediaType mDefaultType;
    private String mDefaultPreviewUrl;
    private BaseMultimediaActionHandler mDefaultMultimediaActionHandler;
    private BaseMultimediaPlayer mDefaultPlayer;
    private MultimediaControllerState mDefaultControllerState;

    @Before
    public void before() throws Exception {
        when(mContext.getResources()).thenReturn(mResources);

        mView = new MultimediaView(mContext, mBinding, mViewModel);
        mDefaultType = MultimediaType.Photo;
        mDefaultPreviewUrl = null;
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

    @Test(expected = NullPointerException.class)
    public void nullContext_shouldThrow() {
        new MultimediaView(null, mBinding, mViewModel);
    }

    @Test(expected = NullPointerException.class)
    public void nullBinding_shouldThrow() {
        new MultimediaView(mContext, null, mViewModel);
    }

    @Test(expected = NullPointerException.class)
    public void nullViewModel_shouldThrow() {
        new MultimediaView(mContext, mBinding, null);
    }

    @Test(expected = NullPointerException.class)
    public void builder_nullType_shouldThrow() {
        mView.builder(null);
    }

    @Test
    public void noBuilder_stop() {
        // act
        mView.release();

        // assert
        verify(mViewModel).getPlayer();
    }

    @Test
    public void builder_withPlayer_stop() {
        // arrange
        when(mViewModel.getPlayer()).thenReturn(mPlayer);

        buildWithPlayerAndActionHandler(mDefaultType, null, mPlayer);

        // act
        mView.release();

        // assert
        verify(mViewModel).getPlayer();
        verify(mPlayer).stop();
    }

    @Test
    public void builder_withoutPlayer_stop() {
        // arrange
        emptyBuilder(mDefaultType);

        // act
        mView.release();

        // assert
        verify(mViewModel).getPlayer();
    }

    @Test
    public void builder_playing_click_shouldStop() {
        clickPlayer(true);
    }

    @Test
    public void builder_notPlaying_click_shouldStart() {
        clickPlayer(false);
    }

    private void clickPlayer(boolean isPlaying) {
        // arrange
        when(mViewModel.getMultimediaActionHandler()).thenReturn(mActionHandler);
        when(mViewModel.getPlayer()).thenReturn(mPlayer);

        MultimediaControllerState state = isPlaying ? Play : mDefaultControllerState;
        when(mViewModel.getControllerState()).thenReturn(state);

        buildWithPlayerAndActionHandler(mDefaultType, mActionHandler, mPlayer);

        // act
        mView.onClick();

        // assert
        verify(mViewModel).getMultimediaActionHandler();
        verify(mActionHandler).onMultimediaAction(isA(BaseMultimediaView.class));

        verify(mViewModel).getPlayer();
        verify(mViewModel).getControllerState();

        if (isPlaying) {
            verify(mPlayer).stop();
        } else {
            verify(mPlayer).start();
        }
    }

    private void buildWithPlayerAndActionHandler(
            MultimediaType type,
            BaseMultimediaActionHandler actionHandler,
            BaseMultimediaPlayer player) {

        // act
        mView.builder(type)
                .withActionHandler(actionHandler)
                .withPlayer(player)
                .build();

        // assert
        mDefaultType = type;
        mDefaultMultimediaActionHandler = actionHandler;
        mDefaultPlayer = player;

        verifyViewModel();
    }

    @Test
    public void emptyBuilder_photo() {
        emptyBuilder(MultimediaType.Photo);
    }

    @Test
    public void emptyBuilder_video() {
        emptyBuilder(MultimediaType.Video);
    }

    @Test
    public void emptyBuilder_audio() {
        emptyBuilder(MultimediaType.Audio);
    }

    private void emptyBuilder(MultimediaType type) {
        // act
        mView.builder(type).build();

        // assert
        mDefaultType = type;
        verifyViewModel();
    }

    @Test
    public void reuseView_typicalRecyclerBehaviour() {
        // arrange
        int size = 2;

        MultimediaType firstType = MultimediaType.Photo;
        MultimediaType secondType = MultimediaType.Video;

        String firstPreviewUrl = "url";
        String secondPreviewUrl = null;

        BaseMultimediaActionHandler firstActionHandler = mock(BaseMultimediaActionHandler.class);
        BaseMultimediaActionHandler secondActionHandler = null;

        BaseMultimediaPlayer firstPlayer = mock(BaseMultimediaPlayer.class);
        BaseMultimediaPlayer secondPlayer = null;

        // act
        mView.builder(firstType)
                .withPreviewUrl(firstPreviewUrl)
                .withActionHandler(firstActionHandler)
                .withPlayer(firstPlayer)
                .build();

        mView.builder(secondType).build();

        // assert

        // type
        verify(mViewModel, times(size)).getType();

        ArgumentCaptor<MultimediaType> typeCaptor = ArgumentCaptor.forClass(MultimediaType.class);
        verify(mViewModel, times(size)).setType(typeCaptor.capture());
        assertThat(typeCaptor.getAllValues(), hasItems(firstType, secondType));

        // previewUrl
        ArgumentCaptor<String> previewUrlCaptor = ArgumentCaptor.forClass(String.class);
        verify(mViewModel, times(size)).setPreviewUrl(previewUrlCaptor.capture());
        assertThat(previewUrlCaptor.getAllValues(), hasItems(firstPreviewUrl, secondPreviewUrl));

        // actionHandler
        verify(mViewModel, times(size)).setActionHandler(isA(BaseActionHandler.class));

        // controller state
        verify(mViewModel, times(size)).setControllerState(mDefaultControllerState);

        // multimediaActionHandler
        ArgumentCaptor<BaseMultimediaActionHandler> multimediaActionHandlerCaptor = ArgumentCaptor.forClass(BaseMultimediaActionHandler.class);
        verify(mViewModel, times(size)).setMultimediaActionHandler(multimediaActionHandlerCaptor.capture());
        assertThat(multimediaActionHandlerCaptor.getAllValues(), hasItems(firstActionHandler, secondActionHandler));

        // player
        ArgumentCaptor<BaseMultimediaPlayer> playerCaptor = ArgumentCaptor.forClass(BaseMultimediaPlayer.class);
        verify(mViewModel, times(size)).setPlayer(playerCaptor.capture());
        assertThat(playerCaptor.getAllValues(), hasItems(firstPlayer, secondPlayer));
    }

    @Test
    public void build_withPreviewUrl() {
        // arrange
        String previewUrl = "preview-url.com";
        mDefaultPreviewUrl = previewUrl;

        // act
        getBuilder()
                .withPreviewUrl(previewUrl)
                .build();

        // assert
        verifyViewModel();
    }

    @Test
    public void build_withActionHandler() {
        // arrange
        BaseMultimediaActionHandler actionHandler = mock(BaseMultimediaActionHandler.class);
        mDefaultMultimediaActionHandler = actionHandler;

        // act
        getBuilder()
                .withActionHandler(actionHandler)
                .build();

        // assert
        verifyViewModel();
    }

    @Test
    public void build_withPlayer() {
        // arrange
        BaseMultimediaPlayer player = mock(BaseMultimediaPlayer.class);
        mDefaultPlayer = player;

        // act
        getBuilder()
                .withPlayer(player)
                .build();

        // assert
        verifyViewModel();
    }

    private BaseMultimediaBuilder getBuilder() {
        return mView.builder(mDefaultType);
    }

    private void verifyViewModel() {
        verify(mViewModel).getType();
        verify(mViewModel).setType(mDefaultType);

        verify(mViewModel).setPreviewUrl(mDefaultPreviewUrl);
        verify(mViewModel).setActionHandler(isA(BaseActionHandler.class));

        verify(mViewModel).setControllerState(mDefaultControllerState);

        verify(mViewModel).setMultimediaActionHandler(mDefaultMultimediaActionHandler);
        verify(mViewModel).setPlayer(mDefaultPlayer);
    }
}