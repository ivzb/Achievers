package com.achievers.ui.evidence;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.achievers.DefaultConfig;
import com.achievers.R;
import com.achievers.data.Result;
import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.entities.Evidence;
import com.achievers.data.sources.DataSources;
import com.achievers.ui._base.AbstractActivity;
import com.achievers.ui.evidence.views.EvidencePhotoView;
import com.achievers.ui.evidence.views.EvidenceVideoView;
import com.achievers.ui.evidence.views.EvidenceVoiceView;
import com.achievers.utils.ActivityUtils;

public class EvidenceActivity extends AbstractActivity {

    public static final String EXTRA_EVIDENCE_ID = "evidence_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.evidence_act);

        // Set up the toolbar.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();

        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowHomeEnabled(true);
        }

        String evidenceId = getIntent().getStringExtra(EXTRA_EVIDENCE_ID);

        if (evidenceId.equals(DefaultConfig.NO_ID)) {
            // todo: redirect to friendly error activity
            throw new IllegalArgumentException();
        }

        DataSources.getInstance().getEvidences().get(evidenceId, new GetCallback<Evidence>() {
            @Override
            public void onSuccess(Result<Evidence> response) {
                if (EvidenceActivity.this.isFinishing()) return;

                Evidence evidence = response.getResults();

                initView(evidence);
            }

            @Override
            public void onFailure(String message) {
                // todo: show message
            }
        });
    }

    private void initView(Evidence evidence) {
        EvidenceView view = (EvidenceView) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (view == null) {
            switch (evidence.getMultimediaType()) {
                case Photo:
                    view = new EvidencePhotoView();
                    break;
                case Video:
                    view = new EvidenceVideoView();
                    break;
                case Voice:
                    view = new EvidenceVoiceView();
                    break;
                default:
                    throw new IllegalArgumentException("View should extend EvidenceView");
            }

            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    view,
                    R.id.contentFrame);
        }

        view.setViewModel(new EvidenceViewModel(evidence));
        view.setPresenter(new EvidencePresenter(
                view,
                DataSources.getInstance().getEvidences()));
    }
}