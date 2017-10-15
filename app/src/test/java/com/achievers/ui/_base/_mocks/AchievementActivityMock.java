package com.achievers.ui._base._mocks;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.achievers.R;
import com.achievers.ui._base.AbstractActivity;

public class AchievementActivityMock extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.achievement_act);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();

        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowHomeEnabled(true);
        }
    }
}