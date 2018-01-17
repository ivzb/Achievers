package com.achievers.data.sources.achievements;

import com.achievers.BuildConfig;
import com.achievers.MockConfig;
import com.achievers.data._base.BaseRemoteDataSourceTest;
import com.achievers.data.entities.Achievement;
import com.achievers.data.sources.RESTClient;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import okhttp3.mock.MockInterceptor;
import okhttp3.mock.Rule;
import retrofit2.Retrofit;

import static okhttp3.mock.ClasspathResources.resource;
import static okhttp3.mock.HttpCodes.HTTP_404_NOT_FOUND;
import static okhttp3.mock.MediaTypes.MEDIATYPE_JSON;

public class AchievementsRemoteDataSourceTest extends BaseRemoteDataSourceTest {

    @Before
    public void before() {
        AchievementsRemoteDataSource.destroyInstance();
        RESTClient.destroyClient();
    }

    @Test
    public void getSuccess() throws InterruptedException {
        MockInterceptor interceptor = new MockInterceptor();

        interceptor.addRule(new Rule.Builder()
                .get()
                .url(MockConfig.Url + BuildConfig.API_VERSION + "/achievement/" + MockConfig.Id)
                .respond(resource("http_responses/achievements/get_200.json"), MEDIATYPE_JSON));

        Retrofit retrofit = super.buildRetrofitClient(interceptor);
        RESTClient.createClient(retrofit);

        AchievementsDataSource dataSource = AchievementsRemoteDataSource.getInstance();

        Achievement expected = new Achievement(
                MockConfig.Id,
                MockConfig.Title,
                MockConfig.Description,
                MockConfig.InvolvementId,
                MockConfig.Url,
                MockConfig.Id,
                MockConfig.Date,
                MockConfig.Date,
                MockConfig.Date);

        super.getCallback(ExpectedCallback.Success, dataSource, expected);
    }

    @Test
    public void getError_404() throws InterruptedException {
        MockInterceptor interceptor = new MockInterceptor();

        interceptor.addRule(new Rule.Builder()
                .get()
                .url(MockConfig.Url + BuildConfig.API_VERSION + "/achievement/" + MockConfig.Id)
                .respond(resource("http_responses/achievements/get_404.json"), MEDIATYPE_JSON)
                .code(HTTP_404_NOT_FOUND)
        );

        Retrofit retrofit = super.buildRetrofitClient(interceptor);
        RESTClient.createClient(retrofit);

        AchievementsDataSource dataSource = AchievementsRemoteDataSource.getInstance();

        String expected = "achievement not found";

        super.getCallback(ExpectedCallback.Failure, dataSource, expected);
    }

    @Test
    public void loadSuccess() throws InterruptedException {
        MockInterceptor interceptor = new MockInterceptor();

        interceptor.addRule(new Rule.Builder()
                .get()
                .url(MockConfig.Url + BuildConfig.API_VERSION + "/achievements?page=0")
                .respond(resource("http_responses/achievements/load_200_full_page.json"), MEDIATYPE_JSON));

        Retrofit retrofit = super.buildRetrofitClient(interceptor);
        RESTClient.createClient(retrofit);

        AchievementsDataSource dataSource = AchievementsRemoteDataSource.getInstance();

        List<Achievement> expected = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            expected.add(new Achievement(
                    MockConfig.Id,
                    MockConfig.Title,
                    MockConfig.Description,
                    MockConfig.InvolvementId,
                    MockConfig.Url,
                    MockConfig.Id,
                    MockConfig.Date,
                    MockConfig.Date,
                    MockConfig.Date));
        }

        super.loadCallback(ExpectedCallback.Success, dataSource, expected);
    }
}