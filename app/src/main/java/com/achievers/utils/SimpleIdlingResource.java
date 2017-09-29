package com.achievers.utils;

import android.support.annotation.NonNull;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class SimpleIdlingResource implements IdlingResource {

    private static SimpleIdlingResource sINSTANCE = null;
    private static final String TAG = SimpleIdlingResource.class.getSimpleName();

    public static void createInstance(@NonNull OkHttpClient client) {
        checkNotNull(client);

        sINSTANCE = new SimpleIdlingResource(TAG, client.dispatcher());
    }

    public static SimpleIdlingResource getInstance() {
        checkNotNull(sINSTANCE);

        return sINSTANCE;
    }

    private final String name;
    private final Dispatcher dispatcher;
    private volatile ResourceCallback mCallback;

    private SimpleIdlingResource(String name, Dispatcher dispatcher) {
        this.name = name;
        this.dispatcher = dispatcher;

        dispatcher.setIdleCallback(new Runnable() {
            @Override
            public void run() {
                ResourceCallback callback = mCallback;

                if (callback != null) {
                    callback.onTransitionToIdle();
                }
            }
        });
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isIdleNow() {
        return dispatcher.runningCallsCount() == 0;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.mCallback = callback;
    }

    private AtomicBoolean mIsIdleNow = new AtomicBoolean(true);

    public void setIdleState(boolean isIdleNow) {
        mIsIdleNow.set(isIdleNow);

        if (isIdleNow && mCallback != null) {
            mCallback.onTransitionToIdle();
        }
    }
}