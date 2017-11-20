package com.achievers.ui.evidence.views;

import com.achievers.ui.evidence.EvidenceView;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaPlayer;
import com.achievers.utils.ui.multimedia.players.PhotoMultimediaPlayer;

public class EvidencePhotoView extends EvidenceView {

    @Override
    public void initMultimedia() {
        BaseMultimediaPlayer player = new PhotoMultimediaPlayer(
                mDataBinding.mvEvidence);

        initMultimedia(player);
    }
}