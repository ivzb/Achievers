package com.achievers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.realm.Realm;

public class BaseActivity extends AppCompatActivity {

    protected Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mRealm = Realm.getDefaultInstance();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mRealm.close();
    }
}