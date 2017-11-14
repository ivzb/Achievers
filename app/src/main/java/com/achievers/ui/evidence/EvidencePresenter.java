package com.achievers.ui.evidence;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.achievers.data.source.evidences.EvidencesDataSource;
import com.achievers.ui._base.AbstractPresenter;
import com.achievers.utils.ui.multimedia.MultimediaType;
import com.achievers.utils.ui.multimedia.MultimediaView;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaPlayer;

import static com.achievers.utils.Preconditions.checkNotNull;

public class EvidencePresenter
        extends AbstractPresenter<EvidenceContract.View>
        implements EvidenceContract.Presenter {

    @VisibleForTesting
    static final int REQUEST_READ_EXTERNAL_STORAGE_PERMISSION = 300;

    private final EvidencesDataSource mEvidencesDataSource;

    EvidencePresenter(
            @NonNull EvidenceContract.View view,
            EvidencesDataSource evidencesDataSource) {

        mView = checkNotNull(view, "view cannot be null");
        mEvidencesDataSource = checkNotNull(evidencesDataSource, "context cannot be null");
    }

    @Override
    public void start() {

    }

    @Override
    public void requestReadExternalStoragePermission() {
        String [] permissions = { Manifest.permission.READ_EXTERNAL_STORAGE };
        mView.requestPermissions(permissions, REQUEST_READ_EXTERNAL_STORAGE_PERMISSION);
    }

    @Override
    public void deliverPermissionsResult(int requestCode, int[] grantResults) {
        boolean permissionGranted = false;

        if (requestCode == REQUEST_READ_EXTERNAL_STORAGE_PERMISSION) {
            permissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }

        if (!permissionGranted) {
            mView.finish();
            return;
        }

        mView.initMultimedia();
    }

    @Override
    public void buildMultimedia(
            MultimediaView view,
            MultimediaType type,
            String previewUrl,
            BaseMultimediaPlayer player) {

        try {
            view.builder(type)
                    .withUrl(previewUrl)
                    .withPlayer(player)
                    .build();
        } catch (NullPointerException e) {
            mView.showMultimediaError();
        }
    }
}