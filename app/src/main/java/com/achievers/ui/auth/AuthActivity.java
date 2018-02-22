package com.achievers.ui.auth;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.achievers.R;
import com.achievers.data.sources.DataSources;
import com.achievers.ui._base.AbstractActivity;
import com.achievers.utils.ActivityUtils;

public class AuthActivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.auth_act);

        // Set up the toolbar.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();

        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowHomeEnabled(true);
        }

        AuthView view = (AuthView) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (view == null) {
            view = new AuthView();

            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    view,
                    R.id.contentFrame);
        }

        view.setViewModel(new AuthViewModel());
        view.setPresenter(new AuthPresenter(
                this,
                view,
                DataSources.getInstance().getUser()));
    }
}

