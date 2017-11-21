package com.achievers.ui.quest;

import android.os.Bundle;

import com.achievers.ui._base.AbstractActivity;

public class QuestActivity extends AbstractActivity {

    public static final String EXTRA_QUEST = "quest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.quest_act);
//
//        // Set up the toolbar.
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        ActionBar ab = getSupportActionBar();
//
//        if (ab != null) {
//            ab.setDisplayHomeAsUpEnabled(true);
//            ab.setDisplayShowHomeEnabled(true);
//        }
//
//        Quest quest = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_QUEST));
//
//        AchievementView view = (AchievementView) getSupportFragmentManager()
//                .findFragmentById(R.id.contentFrame);
//
//        if (view == null) {
//            view = new QuestView();
//
//            ActivityUtils.addFragmentToActivity(
//                    getSupportFragmentManager(),
//                    view,
//                    R.id.contentFrame);
//        }
//
//        view.setViewModel(new QuestViewModel(quest));
//        view.setPresenter(new QuestPresenter(
//                view,
//                QuestsMockDataSource.getInstance()));
    }
}