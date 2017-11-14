package com.achievers.ui._base._shadows;

import com.google.android.exoplayer2.ui.SubtitleView;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(SubtitleView.class)
public class SubtitleViewShadow {

    @Implementation
    private void setUserDefaultStyle() {

    }
}
