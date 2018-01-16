package com.achievers.data.sources.achievements;

import com.achievers.BuildConfig;
import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.entities.Achievement;
import com.achievers.data.sources.RESTClient;
import com.achievers.utils.SimpleIdlingResource;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.mock.MockInterceptor;
import okhttp3.mock.Rule;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.achievers.DefaultConfig.DATE_FORMAT;
import static junit.framework.Assert.assertNotNull;
import static okhttp3.mock.ClasspathResources.resource;
import static okhttp3.mock.MediaTypes.MEDIATYPE_JSON;

public class AchievementsRemoteDataSourceTest {

    private static final String MOCK_URL = "http://mock_url/";
    private static final String MOCK_ID = "mock_id";

    private static final int DATA_LIMIT = 5;

    private Interceptor buildInterceptor() {
        MockInterceptor interceptor = new MockInterceptor();

//        interceptor.addRule(new Rule.Builder()
//                .get().or().post().or().put()
//                .url("https://testserver/api/login")
//                .respond(HTTP_401_UNAUTHORIZED))
//                .header("WWW-Authenticate", "Basic");
//
//        interceptor.addRule(new Rule.Builder()
//                .get()
//                .url("https://testserver/api/json")
//                .respond(MEDIATYPE_JSON, "{succeed:true}"));
//
        interceptor.addRule(new Rule.Builder()
                .get()
                .url(MOCK_URL + BuildConfig.API_VERSION + "/achievement/" + MOCK_ID)
                .respond(resource("achievement_get.json"), MEDIATYPE_JSON));

        return interceptor;
    }

    private Retrofit buildRetrofitClient() {
        Gson gson = new GsonBuilder()
                .setDateFormat(DATE_FORMAT)
                .create();

        Interceptor interceptor = buildInterceptor();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(MOCK_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        SimpleIdlingResource.createInstance(okHttpClient);

        return retrofit;
    }

    @Before
    public void before() {
        RESTClient.destroyClient();

        Retrofit retrofit = buildRetrofitClient();
        RESTClient.createClient(retrofit);
    }

    @Test
    public void test() throws InterruptedException {
        final CountDownLatch lock = new CountDownLatch(1);
        final AtomicReference<AssertionError> assertionError = new AtomicReference<>();

        AchievementsDataSource dataSource = AchievementsRemoteDataSource.getInstance();

        GetCallback<Achievement> callback = new GetCallback<Achievement>() {
            @Override
            public void onSuccess(Achievement data) {
                try {
                    assertNotNull(null);
                } catch (AssertionError e) {
                    assertionError.set(e);
                }

                lock.countDown();
            }

            @Override
            public void onFailure(String message) {
                try {
                    assertNotNull(null);
                } catch (AssertionError e) {
                    assertionError.set(e);
                }

                lock.countDown();
            }
        };

        dataSource.get(MOCK_ID, callback);

        //lock.await();

        if (assertionError.get() != null) {
            throw assertionError.get();
        }
    }
}
