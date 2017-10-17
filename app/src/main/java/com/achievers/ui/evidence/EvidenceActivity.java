package com.achievers.ui.evidence;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.achievers.R;
import com.achievers.data.entities.Evidence;
import com.achievers.data.source.evidences.EvidencesMockDataSource;
import com.achievers.ui._base.AbstractActivity;
import com.achievers.ui.evidence.views.EvidenceAudioFragment;
import com.achievers.ui.evidence.views.EvidenceLocationFragment;
import com.achievers.ui.evidence.views.EvidencePhotoFragment;
import com.achievers.ui.evidence.views.EvidenceVideoFragment;
import com.achievers.utils.ActivityUtils;

import org.parceler.Parcels;

public class EvidenceActivity extends AbstractActivity {

    public static final String EXTRA_EVIDENCE = "evidence";

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

        Evidence evidence = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_EVIDENCE));

        EvidenceFragment view = (EvidenceFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (view == null) {
            switch (evidence.getEvidenceType()) {
                case Image:
                    view = new EvidencePhotoFragment();
                    break;
                case Video:
                    view = new EvidenceVideoFragment();
                    break;
                case Audio:
                    view = new EvidenceAudioFragment();
                    break;
                case Location:
                    view = new EvidenceLocationFragment();
                    break;
                default:
                    throw new IllegalArgumentException("View should extend EvidenceFragment");
            }

            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    view,
                    R.id.contentFrame);
        }

        view.setViewModel(new EvidenceViewModel(evidence));
        view.setPresenter(new EvidencePresenter(
                view,
                EvidencesMockDataSource.getInstance()));
    }
}