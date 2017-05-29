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
    private final static String REST_URL = "http://192.168.0.164:11218/odata/";
    private final static int PAGE_SIZE = 21;

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

    public static int getPageSize() {
        return PAGE_SIZE;
    }

    // Prevent direct instantiation.
    private RESTClient() {}
}
