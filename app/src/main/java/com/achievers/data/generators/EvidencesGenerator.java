package com.achievers.data.generators;

import android.net.Uri;

import com.achievers.data.entities.Evidence;
import com.achievers.data.generators._base.BaseGeneratorConfig;
import com.achievers.utils.ui.multimedia.MultimediaType;

import java.util.Date;

public class EvidencesGenerator
        extends AbstractGenerator<Evidence> {

    public EvidencesGenerator(BaseGeneratorConfig config) {
        super(config);
    }

    @Override
    public Evidence instantiate(long id) {
        String comment = mConfig.getSentence();
        MultimediaType multimediaType = mConfig.getEnum(MultimediaType.values());
        String previewUrl = mConfig.getImageUrl();
        Uri uri;
        Date createdOn = mConfig.getDate();

        switch (multimediaType) {
            case Video:
                uri = mConfig.getVideoUri();
                break;
            case Voice:
                uri = mConfig.getVoiceUri();
                break;
            default:
                uri = Uri.parse(previewUrl);
                break;
        }

        return new Evidence(
                id,
                comment,
                multimediaType,
                previewUrl,
                uri,
                createdOn);
    }
}