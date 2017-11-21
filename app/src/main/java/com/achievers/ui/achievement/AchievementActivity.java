package com.achievers.ui.achievement;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.achievers.R;
import com.achievers.data.entities.Achievement;
import com.achievers.data.source.evidences.EvidencesMockDataSource;
import com.achievers.ui._base.AbstractActivity;
import com.achievers.utils.ActivityUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import org.parceler.Parcels;

public class AchievementActivity extends AbstractActivity {

    public static final String EXTRA_ACHIEVEMENT = "achievement";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.achievement_act);

        // Set up the toolbar.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();

        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowHomeEnabled(true);
        }

        Achievement achievement = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_ACHIEVEMENT));

        initCollapsingToolbar(achievement);

        AchievementView view = (AchievementView) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (view == null) {
            view = new AchievementView();

            ActivityUtils.addFragmentToActivity(
                getSupportFragmentManager(),
                view,
                R.id.contentFrame);
        }

        view.setViewModel(new AchievementViewModel(achievement));
        view.setPresenter(new AchievementPresenter(
                this,
                view,
                EvidencesMockDataSource.getInstance()));
    }

    private void initCollapsingToolbar(Achievement achievement) {
        // todo: add default achievement image while loading

        SimpleDraweeView image = findViewById(R.id.image);
        image.setImageURI(achievement.getPictureUri());

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(achievement.getTitle());
    }
}