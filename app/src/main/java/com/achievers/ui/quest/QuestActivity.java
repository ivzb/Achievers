package com.achievers.ui.quest;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.achievers.DefaultConfig;
import com.achievers.R;
import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.entities.Quest;
import com.achievers.data.sources.DataSources;
import com.achievers.ui._base.activities.CollapsingToolbarActivity;
import com.achievers.utils.ActivityUtils;

public class QuestActivity extends CollapsingToolbarActivity {

    public static final String EXTRA_QUEST_ID = "quest_id";

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

        String questId = getIntent().getStringExtra(EXTRA_QUEST_ID);

        if (questId.equals(DefaultConfig.String)) {
            // todo: redirect to friendly error activity
            throw new IllegalArgumentException();
        }

        initCollapsingToolbar(
                DefaultConfig.PlaceholderImageResource,
                DefaultConfig.PlaceholderText);

        DataSources.getInstance().getQuests().get(questId, new GetCallback<Quest>() {
            @Override
            public void onSuccess(Quest quest) {
                if (QuestActivity.this.isFinishing()) return;

                setCollapsingToolbarImage(quest.getPictureUri());
                setCollapsingToolbarTitle(quest.getName());

                initView(quest);
            }

            @Override
            public void onFailure(String message) {
                // todo: show message
            }
        });


    }

    private void initView(Quest quest) {
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
                DataSources.getInstance().getAchievements()));
    }
}