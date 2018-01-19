package com.achievers.data.sources.achievements;

import com.achievers.MockConfig;
import com.achievers.data._base.BaseRemoteDataSourceTest;
import com.achievers.data.entities.Achievement;
import com.achievers.data.sources.RESTClient;
import com.achievers.data.sources._base.contracts.BaseDataSource;
import com.achievers.data.sources._base.contracts.GetDataSource;
import com.achievers.data.sources._base.contracts.LoadDataSource;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static okhttp3.mock.HttpCodes.HTTP_200_OK;
import static okhttp3.mock.HttpCodes.HTTP_400_BAD_REQUEST;
import static okhttp3.mock.HttpCodes.HTTP_404_NOT_FOUND;
import static okhttp3.mock.HttpCodes.HTTP_405_METHOD_NOT_ALLOWED;
import static okhttp3.mock.HttpCodes.HTTP_500_INTERNAL_SERVER_ERROR;

public class AchievementsRemoteDataSourceTest extends BaseRemoteDataSourceTest {

    private static final String sAchievement = "achievement";

    @Override
    protected BaseDataSource<Achievement> instantiateDataSource() {
        return AchievementsRemoteDataSource.getInstance();
    }

    @Override
    protected Achievement instantiateModel() {
        return new Achievement(
                MockConfig.Id,
                MockConfig.Title,
                MockConfig.Description,
                MockConfig.InvolvementId,
                MockConfig.Url,
                MockConfig.Id,
                MockConfig.Date,
                MockConfig.Date,
                MockConfig.Date);
    }

    @Before
    public void before() {
        AchievementsRemoteDataSource.destroyInstance();
        RESTClient.destroyClient();
    }

    @Test
    public void get_200() throws InterruptedException {
        GetDataSource<Achievement> dataSource = setupGetFor(sAchievement, HTTP_200_OK);

        Achievement expected = instantiateModel();

        super.getCallback(ExpectedCallback.Success, dataSource, expected);
    }

    @Test
    public void get_400() throws InterruptedException {
        GetDataSource<Achievement> dataSource = setupGetFor(sAchievement, HTTP_400_BAD_REQUEST);

        String expected = "missing id";

        super.getCallback(ExpectedCallback.Failure, dataSource, expected);
    }

    @Test
    public void get_404() throws InterruptedException {
        GetDataSource<Achievement> dataSource = setupGetFor(sAchievement, HTTP_404_NOT_FOUND);

        String expected = "achievement not found";

        super.getCallback(ExpectedCallback.Failure, dataSource, expected);
    }

    @Test
    public void get_405() throws InterruptedException {
        GetDataSource<Achievement> dataSource = setupGetFor(sAchievement, HTTP_405_METHOD_NOT_ALLOWED);

        String expected = "method not allowed";

        super.getCallback(ExpectedCallback.Failure, dataSource, expected);
    }

    @Test
    public void get_500() throws InterruptedException {
        GetDataSource<Achievement> dataSource = setupGetFor(sAchievement, HTTP_500_INTERNAL_SERVER_ERROR);

        String expected = "an error occurred, please try again later";

        super.getCallback(ExpectedCallback.Failure, dataSource, expected);
    }

    @Test
    public void load_200_full_page() throws InterruptedException {
        LoadDataSource<Achievement> dataSource = setupLoadFor(sAchievement, HTTP_200_OK, "_full_page");

        List<Achievement> expected = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            expected.add(instantiateModel());
        }

        super.loadCallback(ExpectedCallback.Success, dataSource, expected);
    }

    @Test
    public void load_200_half_page() throws InterruptedException {
        LoadDataSource<Achievement> dataSource = setupLoadFor(sAchievement, HTTP_200_OK, "_half_page");

        List<Achievement> expected = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            expected.add(instantiateModel());
        }

        super.loadCallback(ExpectedCallback.Success, dataSource, expected);
    }

    @Test
    public void load_200_empty_page() throws InterruptedException {
        LoadDataSource<Achievement> dataSource = setupLoadFor(sAchievement, HTTP_200_OK, "_empty_page");

        List<Achievement> expected = null;

        super.loadCallback(ExpectedCallback.NoMore, dataSource, expected);
    }
}