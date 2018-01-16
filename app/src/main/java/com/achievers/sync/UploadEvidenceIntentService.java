package com.achievers.sync;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.achievers.R;
import com.achievers.data.callbacks.SaveCallback;
import com.achievers.data.entities.Evidence;
import com.achievers.data.entities.File;
import com.achievers.data.sources.DataSources;
import com.achievers.ui.add_evidence.AddEvidenceActivity;
import com.achievers.ui.evidence.EvidenceActivity;
import com.achievers.utils.NotificationUtils;
import com.achievers.utils.files.FileUtils;

import org.parceler.Parcels;

import java.io.IOException;

import static com.achievers.ui.add_evidence.AddEvidenceView.MULTIMEDIA_URI_KEY;
import static com.achievers.ui.add_evidence.AddEvidenceView.TITLE_KEY;
import static com.achievers.ui.evidence.EvidenceActivity.EXTRA_EVIDENCE_ID;

public class UploadEvidenceIntentService extends IntentService {

    public final static String EVIDENCE_EXTRA = "evidence";

    private static final int EVIDENCE_UPLOAD_PENDING_INTENT_ID = 4132;

    public UploadEvidenceIntentService() {
        super("UploadEvidenceIntentService");
    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        Parcelable parcelable = intent.getParcelableExtra(EVIDENCE_EXTRA);
        Evidence evidence = Parcels.unwrap(parcelable);

        if (evidence == null) {
            showFailure(null);
            return;
        }

        File multimedia;

        try {
            multimedia = FileUtils.toFile(this, evidence.getUri());
        } catch (NullPointerException | IOException e) {
            showFailure(evidence);
            return;
        }

        saveMultimedia(multimedia, evidence);
    }

    private void saveMultimedia(final File multimedia, final Evidence evidence) {
        DataSources.getInstance().getFiles().storeFile(multimedia, new SaveCallback<String>() {
            @Override
            public void onSuccess(String multimediaUrl) {
                evidence.setUrl(multimediaUrl);
                saveEvidence(evidence);
            }

            @Override
            public void onFailure(String message) {
                showFailure(evidence);
            }
        });
    }

    private void saveEvidence(final Evidence evidence) {
        DataSources.getInstance().getEvidences().save(evidence, new SaveCallback<String>() {
            @Override
            public void onSuccess(String id) {
                evidence.setId(id);
                showSuccess(evidence);
            }

            @Override
            public void onFailure(String message) {
                showFailure(evidence);
            }
        });
    }

    private void showSuccess(Evidence evidence) {
        PendingIntent intent = getSuccessIntent(this, evidence);
        show("Evidence uploaded.", intent);
    }

    private void showFailure(Evidence evidence) {
        PendingIntent intent = getFailureIntent(this, evidence);
        show("Could not upload evidence. Click to try again.", intent);
    }

    private void show(String text, PendingIntent intent) {
        NotificationUtils.notify(this, getString(R.string.app_name), text, intent);
    }

    private PendingIntent getSuccessIntent(Context context, Evidence evidence) {
        Intent startActivityIntent = new Intent(context, EvidenceActivity.class);
        startActivityIntent.putExtra(EXTRA_EVIDENCE_ID, evidence.getId());

        return PendingIntent.getActivity(
                context,
                EVIDENCE_UPLOAD_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private PendingIntent getFailureIntent(Context context, Evidence evidence) {
        Intent startActivityIntent = new Intent(context, AddEvidenceActivity.class);

        if (evidence != null) {
            Bundle evidenceExtras = new Bundle();

            evidenceExtras.putString(TITLE_KEY, evidence.getComment());

            evidenceExtras.putParcelable(MULTIMEDIA_URI_KEY, evidence.getUri());

            evidenceExtras.putString(AddEvidenceActivity.AchievementIdExtra,
                    evidence.getAchievementId());

            evidenceExtras.putSerializable(
                    AddEvidenceActivity.MultimediaTypeExtra,
                    evidence.getMultimediaType());

            startActivityIntent.putExtras(evidenceExtras);
        }

        return PendingIntent.getActivity(
                context,
                EVIDENCE_UPLOAD_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }
}