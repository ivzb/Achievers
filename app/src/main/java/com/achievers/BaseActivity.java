package com.achievers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.realm.Realm;

public class BaseActivity extends AppCompatActivity {

    protected Realm mRealm;
    private boolean orientationChange = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!this.orientationChange) this.mRealm = Realm.getDefaultInstance();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        this.orientationChange = true;
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.orientationChange = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!this.orientationChange) this.mRealm.close();
    }
}