package com.achievers.utils.multimedia;

import com.achievers.ui._base.contracts.action_handlers.BaseMultimediaActionHandler;
import com.achievers.utils.multimedia._base.AbstractMultimediaViewTest;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaPlayer;

import org.junit.Test;

import static com.achievers.utils.ui.multimedia.MultimediaType.Audio;
import static com.achievers.utils.ui.multimedia.MultimediaType.Photo;
import static com.achievers.utils.ui.multimedia.MultimediaType.Video;
import static org.mockito.Mockito.mock;

public class MultimediaBuilderTest extends AbstractMultimediaViewTest {

    @Test(expected = NullPointerException.class)
    public void builder_nullType_shouldThrow() {
        mView.builder(null);
    }

    @Test
    public void emptyBuilder_photo() {
        mDefaultType = Photo;
        buildAndVerify();
    }

    @Test
    public void emptyBuilder_video() {
        mDefaultType = Video;
        buildAndVerify();
    }

    @Test
    public void emptyBuilder_audio() {
        mDefaultType = Audio;
        buildAndVerify();
    }

    @Test
    public void build_withPreviewUrl() {
        mDefaultPreviewUrl = "preview-url.com";
        buildAndVerify();
    }

    @Test
    public void build_withActionHandler() {
        mDefaultMultimediaActionHandler = mock(BaseMultimediaActionHandler.class);
        buildAndVerify();
    }

    @Test
    public void build_withPlayer() {
        mDefaultPlayer = mock(BaseMultimediaPlayer.class);
        buildAndVerify();
    }
}