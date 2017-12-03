package com.achievers.ui.quest;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.achievers.R;
import com.achievers.data.entities.Quest;
import com.achievers.data.sources.achievements.AchievementsMockDataSource;
import com.achievers.ui._base.activities.CollapsingToolbarActivity;
import com.achievers.utils.ActivityUtils;

import org.parceler.Parcels;

public class QuestActivity extends CollapsingToolbarActivity {

    public static final String EXTRA_QUEST = "quest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.quest_act);

        // Set up the toolbar.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();

        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowHomeEnabled(true);
        }

        Quest quest = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_QUEST));

        // todo
//        initCollapsingToolbar(quest.getPictureUri(), quest.getName());

        QuestView view = (QuestView) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (view == null) {
            view = new QuestView();

            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    view,
                    R.id.contentFrame);
        }

        view.setViewModel(new QuestViewModel(quest));
        view.setPresenter(new QuestPresenter(
                this,
                view,
                AchievementsMockDataSource.getInstance()));
    }
}