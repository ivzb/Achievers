package com.achievers.utils.multimedia;

import android.net.Uri;

import com.achievers.ui._base._contracts.action_handlers.BaseMultimediaActionHandler;
import com.achievers.utils.multimedia._base.AbstractMultimediaViewTest;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaPlayer;

import org.junit.Test;

import static com.achievers.utils.ui.multimedia.MultimediaType.Photo;
import static com.achievers.utils.ui.multimedia.MultimediaType.Video;
import static com.achievers.utils.ui.multimedia.MultimediaType.Voice;
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
    public void emptyBuilder_voice() {
        mDefaultType = Voice;
        buildAndVerify();
    }

    @Test
    public void build_withPreviewUrl() {
        mDefaultUrl = "preview-url.com";
        buildAndVerify();
    }

    @Test
    public void build_withPreviewUri() {
        mDefaultUri = Uri.parse("preview-uri.com");
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