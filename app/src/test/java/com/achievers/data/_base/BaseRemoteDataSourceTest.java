package com.achievers.data._base;

import com.achievers.MockConfig;
import com.achievers.data.Result;
import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.entities._base.BaseModel;
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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.achievers.DefaultConfig.DATE_FORMAT;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public abstract class BaseRemoteDataSourceTest {

    protected enum ExpectedCallback {
        Success,
        NoMore,
        Failure
    }

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
                        case Success:
                        case Failure:
                            fail("it should not reach onSuccess");
                            break;
                        case NoMore:
                            // todo: assert something
                            fail("todo");
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
}
