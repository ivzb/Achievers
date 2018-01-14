package com.achievers.ui.add_evidence;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.achievers.DefaultConfig;
import com.achievers.R;
import com.achievers.ui._base.AbstractActivity;
import com.achievers.utils.ActivityUtils;
import com.achievers.utils.ExoPlayerUtils;
import com.achievers.utils.files.factory.PictureFileFactory;
import com.achievers.utils.ui.multimedia.MultimediaType;
import com.google.android.exoplayer2.SimpleExoPlayer;

public class AddEvidenceActivity extends AbstractActivity {

    public static final int REQUEST_ADD_EVIDENCE = 156;

    public static final String AchievementIdExtra = "AchievementId";
    public static final String MultimediaTypeExtra = "MultimediaType";

    private SimpleExoPlayer mExoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_evidence_act);

        // Set up the toolbar.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(R.string.add_evidence);
        }

        AddEvidenceView view =
                (AddEvidenceView) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (view == null) {
            view = new AddEvidenceView();

            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    view,
                    R.id.contentFrame);
        }

        Bundle extras = getIntent().getExtras();

        String achievementId = DefaultConfig.String;
        MultimediaType multimediaType = MultimediaType.Photo;

        if (extras != null) {
            if (extras.containsKey(AchievementIdExtra)) {
                achievementId = extras.getString(AchievementIdExtra);
            }

            if (extras.containsKey(MultimediaTypeExtra)) {
                multimediaType = (MultimediaType) extras.getSerializable(MultimediaTypeExtra);
            }

            view.setArguments(extras);
        }

        mExoPlayer = ExoPlayerUtils.initialize(this);

        view.setViewModel(new AddEvidenceViewModel(achievementId, multimediaType));
        view.setPresenter(
                new AddEvidencePresenter(
                        this,
                        view,
                        new PictureFileFactory(this),
                        mExoPlayer));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mExoPlayer.release();
        mExoPlayer = null;
    }
}