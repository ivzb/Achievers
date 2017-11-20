package com.achievers.ui.voice_recording;

import android.content.Intent;
import android.databinding.ViewDataBinding;

import com.achievers.ui._base._contracts.BasePresenter;
import com.achievers.ui._base._contracts.BaseView;
import com.achievers.ui._base._contracts.BaseViewModel;

import java.io.File;

public class VoiceRecordingContract {

    interface View<DB extends ViewDataBinding> extends BaseView<Presenter, ViewModel, DB> {

        void requestPermissions(String[] permissions, int requestCode);

        File getFile();
        void setFile(File file);

        void finish(int resultCode, Intent intent);
    }

    interface Presenter extends BasePresenter {

        void requestRecordAudioPermission();

        void clickStartRecording();
        void clickStopRecording();

        void deliverPermissionsResult(
                int requestCode,
                int[] grantResults);
    }

    public interface ViewModel extends BaseViewModel {

        File getFile();
        void setFile(File file);
    }
}