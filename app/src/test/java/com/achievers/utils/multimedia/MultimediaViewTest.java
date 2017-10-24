package com.achievers.utils.multimedia;

import android.content.Context;
import android.content.res.Resources;

import com.achievers.R;
import com.achievers.databinding.MultimediaViewBinding;
import com.achievers.ui._base.contracts.action_handlers.BaseActionHandler;
import com.achievers.ui._base.contracts.action_handlers.BaseMultimediaActionHandler;
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

import static junit.framework.Assert.assertEquals;
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
    private MultimediaType mBuilderType;

    @Before
    public void before() throws Exception {
        when(mContext.getResources()).thenReturn(mResources);

        mView = new MultimediaView(mContext, mBinding, mViewModel);
        mBuilderType = MultimediaType.Photo;

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
    public void isPlaying_true() {
        isPlaying(true);
    }

    @Test
    public void isPlaying_false() {
        isPlaying(false);
    }

    private void isPlaying(boolean expected) {
        // arrange
        when(mViewModel.isPlaying()).thenReturn(expected);

        // act
        boolean actual = mView.isPlaying();

        // assert
        verify(mViewModel).isPlaying();

        assertEquals(expected, actual);
    }

    @Test
    public void noBuilder_stop() {
        // act
        mView.stop();

        // assert
        verify(mViewModel).getPlayer();
        verify(mViewModel).setPlaying(false);
        verify(mViewModel).setShowControls(true);
    }

    @Test
    public void builder_stop() {
        // arrange
        when(mViewModel.getPlayer()).thenReturn(mPlayer);

        playerAndActionHandlerBuilder(mBuilderType, null, mPlayer);

        // act
        mView.stop();

        // assert
        verify(mViewModel).getPlayer();
        verify(mPlayer).showControls();
        verify(mViewModel, times(2)).setPlaying(false);
        verify(mViewModel).setShowControls(true);

        verify(mPlayer).stop();
    }

    private void playerAndActionHandlerBuilder(
            MultimediaType type,
            BaseMultimediaActionHandler actionHandler,
            BaseMultimediaPlayer player) {

        // act
        mView.builder(type)
                .withActionHandler(actionHandler)
                .withPlayer(player)
                .build();

        // assert
        verifyViewModel(
                type,
                null,
                false,
                0,
                0,
                false,
                actionHandler,
                player);
    }

    // todo: test toggle with view's different states

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
        verifyViewModel(
                type,
                null,
                false,
                0,
                0,
                false,
                null,
                null);
    }

    @Test
    public void clean() {
        // arrange
        int size = 2;

        MultimediaType firstType = MultimediaType.Photo;
        MultimediaType secondType = MultimediaType.Video;

        String firstPreviewUrl = "url";
        String secondPreviewUrl = null;

        boolean firstShowControls = true;
        boolean secondShowControls = false;

        int firstPlayResource = 37;
        int secondPlayResource = R.drawable.ic_play;

        int firstPauseResource = 49;
        int secondPauseResource = R.drawable.ic_pause;

        boolean firstPlaying = true;
        boolean secondPlaying = false;

        BaseMultimediaActionHandler firstActionHandler = mock(BaseMultimediaActionHandler.class);
        BaseMultimediaActionHandler secondActionHandler = null;

        BaseMultimediaPlayer firstPlayer = mock(BaseMultimediaPlayer.class);
        BaseMultimediaPlayer secondPlayer = null;

        // act
        mView.builder(firstType)
                .withPreviewUrl(firstPreviewUrl)
                .withControls(firstShowControls)
                .withPlayResource(firstPlayResource)
                .withPauseResource(firstPauseResource)
                .withPlaying(firstPlaying)
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

        // showControls
        ArgumentCaptor<Boolean> showControlsCaptor = ArgumentCaptor.forClass(Boolean.class);
        verify(mViewModel, times(size)).setShowControls(showControlsCaptor.capture());
        assertThat(showControlsCaptor.getAllValues(), hasItems(firstShowControls, secondShowControls));

        // playResource
        ArgumentCaptor<Integer> playResourceCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(mViewModel, times(size)).setPlayResource(playResourceCaptor.capture());
        assertThat(playResourceCaptor.getAllValues(), hasItems(firstPlayResource, secondPlayResource));

        // pauseResource
        ArgumentCaptor<Integer> pauseResourceCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(mViewModel, times(size)).setPauseResource(pauseResourceCaptor.capture());
        assertThat(pauseResourceCaptor.getAllValues(), hasItems(firstPauseResource, secondPauseResource));

        // playing
        ArgumentCaptor<Boolean> playingCaptor = ArgumentCaptor.forClass(Boolean.class);
        verify(mViewModel, times(size)).setPlaying(playingCaptor.capture());
        assertThat(playingCaptor.getAllValues(), hasItems(firstPlaying, secondPlaying));

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

        // act
        getBuilder()
                .withPreviewUrl(previewUrl)
                .build();

        // assert
        verifyViewModel(
                mBuilderType,
                previewUrl,
                false,
                0,
                0,
                false,
                null,
                null);
    }

    @Test
    public void build_withControls_true() {
        build_withControls(true);
    }

    @Test
    public void build_withControls_false() {
        build_withControls(false);
    }

    private void build_withControls(boolean showControls) {
        // act
        getBuilder()
                .withControls(showControls)
                .build();

        // assert
        verifyViewModel(
                mBuilderType,
                null,
                showControls,
                0,
                0,
                false,
                null,
                null);
    }

    @Test
    public void build_withPlayResource() {
        // arrange
        int resource = 17;

        // act
        getBuilder()
                .withPlayResource(resource)
                .build();

        // assert
        verifyViewModel(
                mBuilderType,
                null,
                false,
                resource,
                0,
                false,
                null,
                null);
    }

    @Test
    public void build_withPauseResource() {
        // arrange
        int resource = 38;

        // act
        getBuilder()
                .withPauseResource(resource)
                .build();

        // assert
        verifyViewModel(
                mBuilderType,
                null,
                false,
                0,
                resource,
                false,
                null,
                null);
    }

    @Test
    public void build_withPlaying_true() {
        build_withPlaying(true);
    }

    @Test
    public void build_withPlaying_false() {
        build_withPlaying(false);
    }

    private void build_withPlaying(boolean playing) {
        // act
        getBuilder()
                .withPlaying(playing)
                .build();

        // assert
        verifyViewModel(
                mBuilderType,
                null,
                false,
                0,
                0,
                playing,
                null,
                null);
    }

    @Test
    public void build_withActionHandler() {
        // arrange
        BaseMultimediaActionHandler actionHandler = mock(BaseMultimediaActionHandler.class);

        // act
        getBuilder()
                .withActionHandler(actionHandler)
                .build();

        // assert
        verifyViewModel(
                mBuilderType,
                null,
                false,
                0,
                0,
                false,
                actionHandler,
                null);
    }

    @Test
    public void build_withPlayer() {
        // arrange
        BaseMultimediaPlayer player = mock(BaseMultimediaPlayer.class);

        // act
        getBuilder()
                .withPlayer(player)
                .build();

        // assert
        verifyViewModel(
                mBuilderType,
                null,
                false,
                0,
                0,
                false,
                null,
                player);
    }

    private BaseMultimediaBuilder getBuilder() {
        return mView.builder(mBuilderType);
    }

    private void verifyViewModel(
            MultimediaType type,
            String previewUrl,
            boolean showControls,
            int playResource,
            int pauseResource,
            boolean isPlaying,
            BaseMultimediaActionHandler multimediaActionHandler,
            BaseMultimediaPlayer player) {

        if (playResource == 0) playResource = R.drawable.ic_play;
        if (pauseResource == 0) pauseResource = R.drawable.ic_pause;

        verify(mViewModel).getType();
        verify(mViewModel).setType(type);

        verify(mViewModel).setPreviewUrl(previewUrl);
        verify(mViewModel).setActionHandler(isA(BaseActionHandler.class));

        verify(mViewModel).setPlaying(isPlaying);
        verify(mViewModel).setShowControls(showControls);
        verify(mViewModel).setPlayResource(playResource);
        verify(mViewModel).setPauseResource(pauseResource);

        verify(mViewModel).setMultimediaActionHandler(multimediaActionHandler);
        verify(mViewModel).setPlayer(player);
    }
}