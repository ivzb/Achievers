package com.achievers.ui.rewards;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.achievers.R;
import com.achievers.data.entities.Quest;
import com.achievers.data.sources.DataSources;
import com.achievers.ui._base.AbstractActivity;
import com.achievers.utils.ActivityUtils;

import org.parceler.Parcels;

public class RewardsActivity extends AbstractActivity {

    public static final String EXTRA_QUEST = "quest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.rewards_act);

        // Set up the toolbar.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();

        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowHomeEnabled(true);
        }

        Quest quest = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_QUEST));

        RewardsView view = (RewardsView) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (view == null) {
            view = new RewardsView();

            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    view,
                    R.id.contentFrame);
        }

        view.setViewModel(new RewardsViewModel(quest));
        view.setPresenter(new RewardsPresenter(
                this,
                view,
                DataSources.getInstance().getRewards()));
    }
}
