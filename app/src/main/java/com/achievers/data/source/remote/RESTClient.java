package com.achievers.data.source.remote;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RESTClient {
    private static Retrofit RETROFIT;
    private final static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    private final static String REST_URL = "http://host:port/odata/";

    public static Retrofit getClient() {
        if (RETROFIT == null) {
            Gson gson = new GsonBuilder()
                    .setDateFormat(DATE_FORMAT)
                    .create();

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addNetworkInterceptor(new StethoInterceptor())
                    .build();

            RETROFIT = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(REST_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return RETROFIT;
    }

    public static void destroyClient() {
        RETROFIT = null;
    }

    // Prevent direct instantiation.
    private RESTClient() {}
}
