package com.achievers.ui.voice_recording;

import android.content.Intent;
import android.databinding.ViewDataBinding;

import com.achievers.ui._base.contracts.BasePresenter;
import com.achievers.ui._base.contracts.BaseView;
import com.achievers.ui._base.contracts.BaseViewModel;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaViewActionHandler;

import java.io.File;

public class VoiceRecordingContract {

    interface View<DB extends ViewDataBinding> extends BaseView<Presenter, ViewModel, DB> {

        void finish(int resultCode, Intent intent);
    }

    interface Presenter extends BasePresenter {

        void clickStartRecording();
        void clickStopRecording();

        void deliverPermission(
                int requestCode,
                int resultCode,
                Intent data,
                BaseMultimediaViewActionHandler actionHandler);
    }

    public interface ViewModel extends BaseViewModel {

        File getFile();
        void setFile(File file);
    }
}