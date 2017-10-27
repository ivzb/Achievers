package com.achievers.utils.multimedia;

import com.achievers.ui._base.contracts.action_handlers.BaseActionHandler;
import com.achievers.ui._base.contracts.action_handlers.BaseMultimediaActionHandler;
import com.achievers.utils.multimedia._base.AbstractMultimediaViewTest;
import com.achievers.utils.ui.multimedia.MultimediaControllerState;
import com.achievers.utils.ui.multimedia.MultimediaType;
import com.achievers.utils.ui.multimedia.MultimediaView;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaPlayer;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaView;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static com.achievers.utils.ui.multimedia.MultimediaType.Photo;
import static com.achievers.utils.ui.multimedia.MultimediaType.Video;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MultimediaViewTest extends AbstractMultimediaViewTest {

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

    @Test
    public void click_noBuilder_nothingShouldHappen() {
        mView.onClick();

        verify(mViewModel).getMultimediaActionHandler();

        verify(mViewModel).getPlayer();
    }

    @Test
    public void click_notPlaying_shouldPlay() {
        // arrange
        when(mViewModel.getMultimediaActionHandler()).thenReturn(null);
        when(mViewModel.getPlayer()).thenReturn(mPlayer);
        when(mViewModel.isPlaying()).thenReturn(false);

        buildAndVerify();

        // act
        mView.onClick();

        // assert
        verify(mViewModel).getMultimediaActionHandler();
        verify(mViewModel).getPlayer();
        verify(mViewModel).isPlaying();
        verify(mPlayer).start();
        verify(mViewModel).setPlaying(true);
    }

    @Test
    public void click_playing_shouldStop() {
        // arrange
        when(mViewModel.getMultimediaActionHandler()).thenReturn(null);
        when(mViewModel.getPlayer()).thenReturn(mPlayer);
        when(mViewModel.isPlaying()).thenReturn(true);

        buildAndVerify();

        // act
        mView.onClick();

        // assert
        verify(mViewModel).getMultimediaActionHandler();
        verify(mViewModel).getPlayer();
        verify(mViewModel).isPlaying();
        verify(mPlayer).stop();
        verify(mViewModel, times(2)).setPlaying(false);
    }

    @Test
    public void click_builder_noActionHandler_noPlayer_nothingShouldHappen() {
        click(null, null, false);
    }

    @Test
    public void click_builder_actionHandler_actionHandler_noPlayer_shouldTriggerAction() {
        click(mActionHandler, null, false);
    }

    @Test
    public void click_builder_noActionHandler_playerNotPlaying_shouldPlay() {
        click(null, mPlayer, false);
    }

    @Test
    public void click_builder_noActionHandler_playerPlaying_shouldStop() {
        click(null, mPlayer, true);
    }

    @Test
    public void click_builder_actionHandler_playerNotPlaying_shouldTriggerActionAndPlay() {
        click(mActionHandler, mPlayer, false);
    }

    @Test
    public void click_builder_actionHandler_playerPlaying_shouldTriggerActionAndStop() {
        click(mActionHandler, mPlayer, true);
    }

    private void click(
            BaseMultimediaActionHandler multimediaActionHandler,
            BaseMultimediaPlayer player,
            boolean isPlaying) {

        // arrange
        when(mViewModel.getMultimediaActionHandler()).thenReturn(multimediaActionHandler);
        when(mViewModel.getPlayer()).thenReturn(player);
        when(mViewModel.isPlaying()).thenReturn(isPlaying);

        buildAndVerify();

        // act
        mView.onClick();

        // assert
        verify(mViewModel).getMultimediaActionHandler();

        if (multimediaActionHandler != null) {
            verify(multimediaActionHandler).onMultimediaAction(isA(BaseMultimediaView.class));
        }

        verify(mViewModel).getPlayer();

        if (player != null) {
            verify(mViewModel).isPlaying();

            if (isPlaying) {
                verify(player).stop();
                verify(mViewModel, times(2)).setPlaying(false);
            } else {
                verify(player).start();
                verify(mViewModel).setPlaying(true);
            }
        }
    }

    @Test
    public void release_noBuilder() {
        // act
        mView.release();

        // assert
        verify(mViewModel).getPlayer();
    }

    @Test
    public void release_builder_noPlayer() {
        // arrange
        buildAndVerify();

        // act & assert
        release_noBuilder();
    }

    @Test
    public void release_builder_player() {
        // arrange
        when(mViewModel.getPlayer()).thenReturn(mPlayer);

        mDefaultMultimediaActionHandler = null;
        buildAndVerify();

        // act
        mView.release();

        // assert
        verify(mViewModel).getPlayer();
        verify(mPlayer).stop();
        verify(mViewModel, times(2)).setPlaying(false);
    }

    @Test
    public void changeState() {
        for (MultimediaControllerState state: MultimediaControllerState.values()) {
            changeState(state);
        }
    }

    private void changeState(MultimediaControllerState state) {
        mView.changeState(state);

        verify(mViewModel).setControllerState(state);
    }

    @Test
    public void reuseView_typicalRecyclerBehaviour() {
        // arrange
        int size = 2;

        MultimediaType firstType = Photo;
        MultimediaType secondType = Video;

        String firstPreviewUrl = "url";
        String secondPreviewUrl = null;

        boolean firstPlaying = false;
        boolean secondPlaying = false;

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

        // playing
        ArgumentCaptor<Boolean> playingCaptor = ArgumentCaptor.forClass(Boolean.class);
        verify(mViewModel, times(size)).setPlaying(playingCaptor.capture());
        assertThat(playingCaptor.getAllValues(), hasItems(firstPlaying, secondPlaying));

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
}