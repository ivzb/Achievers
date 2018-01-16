package com.achievers.data.sources;

import android.support.annotation.VisibleForTesting;

import com.achievers.BuildConfig;
import com.achievers.utils.SimpleIdlingResource;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RESTClient {

    private static Retrofit sRETROFIT;
    private final static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    private RESTClient() {

    }

    public static Retrofit getClient() {
        if (sRETROFIT == null) {
            createClient(initClient());
        }

        return sRETROFIT;
    }

    @VisibleForTesting
    public static void createClient(Retrofit retrofit) {
        sRETROFIT = retrofit;
    }

    public static void destroyClient() {
        sRETROFIT = null;
    }

    private static Retrofit initClient() {
        Gson gson = new GsonBuilder()
                .setDateFormat(DATE_FORMAT)
                .create();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        SimpleIdlingResource.createInstance(okHttpClient);

        return retrofit;
    }
}