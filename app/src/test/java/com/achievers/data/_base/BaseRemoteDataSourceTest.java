package com.achievers.data._base;

import com.achievers.BuildConfig;
import com.achievers.MockConfig;
import com.achievers.data.Result;
import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.entities._base.BaseModel;
import com.achievers.data.sources.RESTClient;
import com.achievers.data.sources._base.contracts.BaseDataSource;
import com.achievers.data.sources._base.contracts.GetDataSource;
import com.achievers.data.sources._base.contracts.LoadDataSource;
import com.achievers.utils.SimpleIdlingResource;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.mock.MockInterceptor;
import okhttp3.mock.Rule;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.achievers.DefaultConfig.DATE_FORMAT;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import static okhttp3.mock.ClasspathResources.resource;
import static okhttp3.mock.MediaTypes.MEDIATYPE_JSON;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public abstract class BaseRemoteDataSourceTest {

    protected enum ExpectedCallback {
        Success,
        NoMore,
        Failure
    }

    protected abstract <T extends BaseModel> BaseDataSource<T> instantiateDataSource();
    protected abstract <T extends BaseModel> T instantiateModel();

    protected <T extends BaseModel> void getCallback(
            final ExpectedCallback type,
            final GetDataSource<T> dataSource,
            final Object expected) throws InterruptedException {

        final CountDownLatch lock = new CountDownLatch(1);
        final AtomicReference<AssertionError> assertionError = new AtomicReference<>();

        GetCallback<T> callback = new GetCallback<T>() {
            @Override
            public void onSuccess(Result<T> data) {
                try {
                    switch (type) {
                        case Success:
                            T actual = data.getResults();
                            assertEquals(expected, actual);
                            break;
                        case Failure:
                            fail("it should not reach onSuccess");
                            break;
                        default:
                            fail("unsupported CallbackType");
                            break;
                    }
                } catch (AssertionError e) {
                    assertionError.set(e);
                }

                lock.countDown();
            }

            @Override
            public void onFailure(String actual) {
                try {
                    switch (type) {
                        case Success:
                            fail("it should not reach onFailure");
                            break;
                        case Failure:
                            assertEquals(expected, actual);
                            break;
                        default:
                            fail("unsupported CallbackType");
                            break;
                    }
                } catch (AssertionError e) {
                    assertionError.set(e);
                }

                lock.countDown();
            }
        };

        dataSource.get(MockConfig.Id, callback);

        lock.await();

        if (assertionError.get() != null) {
            throw assertionError.get();
        }
    }

    protected <T extends BaseModel> void loadCallback(
            final ExpectedCallback type,
            final LoadDataSource<T> dataSource,
            final Object expected) throws InterruptedException {

        final CountDownLatch lock = new CountDownLatch(1);
        final AtomicReference<AssertionError> assertionError = new AtomicReference<>();

        LoadCallback<T> callback = new LoadCallback<T>() {
            @Override
            public void onSuccess(Result<List<T>> data, int page) {
                try {
                    switch (type) {
                        case Success:
                            List<T> actual = data.getResults();

                            assertThat(actual, is(expected));
                            break;
                        case NoMore:
                        case Failure:
                            fail("it should not reach onSuccess");
                            break;
                        default:
                            fail("unsupported CallbackType");
                            break;
                    }
                } catch (AssertionError e) {
                    assertionError.set(e);
                }

                lock.countDown();
            }

            @Override
            public void onNoMore() {
                try {
                    switch (type) {
                        case NoMore:
                            // everything is fine, life is good
                            break;
                        case Success:
                        case Failure:
                            fail("it should not reach onSuccess");
                            break;
                        default:
                            fail("unsupported CallbackType");
                            break;
                    }
                } catch (AssertionError e) {
                    assertionError.set(e);
                }

                lock.countDown();
            }

            @Override
            public void onFailure(String actual) {
                try {
                    switch (type) {
                        case Success:
                        case NoMore:
                            fail("it should not reach onFailure");
                            break;
                        case Failure:
                            assertEquals(expected, actual);
                            break;
                        default:
                            fail("unsupported CallbackType");
                            break;
                    }
                } catch (AssertionError e) {
                    assertionError.set(e);
                }

                lock.countDown();
            }
        };

        dataSource.load(MockConfig.Id, MockConfig.Page, callback);

        lock.await();

        if (assertionError.get() != null) {
            throw assertionError.get();
        }
    }

    protected Retrofit buildRetrofitClient(Interceptor interceptor) {
        Gson gson = new GsonBuilder()
                .setDateFormat(DATE_FORMAT)
                .create();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(MockConfig.Url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        SimpleIdlingResource.createInstance(okHttpClient);

        return retrofit;
    }

    private void setupInterceptorRule(
            Rule.Builder builder,
            String url,
            String method,
            String model,
            int statusCode,
            String statusSuffix) {

        MockInterceptor interceptor = new MockInterceptor();

        String resourcePath = String.format("http_responses/%ss/%s_%d%s.json", model, method, statusCode, statusSuffix);

        interceptor.addRule(builder
                .url(url)
                .respond(resource(resourcePath), MEDIATYPE_JSON)
                .code(statusCode)
        );

        Retrofit retrofit = buildRetrofitClient(interceptor);
        RESTClient.createClient(retrofit);
    }

    protected <T extends BaseModel> GetDataSource<T> setupGetFor(
            String model,
            int statusCode) {

        Rule.Builder builder = new Rule.Builder().get();
        String url = MockConfig.Url + BuildConfig.API_VERSION + "/" + model + "/" + MockConfig.Id;

        setupInterceptorRule(builder, url, "get", model, statusCode, "");

        return instantiateDataSource();
    }

    protected <T extends BaseModel> LoadDataSource<T> setupLoadFor(
            String model,
            int statusCode,
            String statusSuffix) {

        Rule.Builder builder = new Rule.Builder().get();
        String url = MockConfig.Url + BuildConfig.API_VERSION + "/" + model + "s?page=0";

        setupInterceptorRule(builder, url, "load", model, statusCode, statusSuffix);

        return instantiateDataSource();
    }
}