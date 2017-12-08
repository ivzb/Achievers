package com.achievers.data.sources;

import com.achievers.DefaultConfig;
import com.achievers.data.callbacks.GetCallback;
import com.achievers.data.entities.Authentication;
import com.achievers.data.generators.config.GeneratorConfig;
import com.achievers.data.sources.authentication.AuthenticationMockDataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Random;

import io.bloco.faker.Faker;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationMockDataSourceTest {

    private AuthenticationMockDataSource mDataSource;

    private static final String sCorrectEmail = DefaultConfig.Mocks.sEmail;
    private static final String sCorrectPassword = DefaultConfig.Mocks.sPassword;
    private static final String sCorrectAuthenticationToken = DefaultConfig.Mocks.sAuthenticationToken;

    private static final String sIncorrectEmail = "incorrect_email";
    private static final String sIncorrectPassword = "incorrect_password";

    @Mock private GetCallback<Authentication> mGetCallback;

    @Captor private ArgumentCaptor<Authentication> mSuccessCaptor;
    @Captor private ArgumentCaptor<String> mFailureCaptor;

    @Before
    public void before() {
        GeneratorConfig.destroyInstance();
        GeneratorConfig.initialize(new Random(), new Faker());

        AuthenticationMockDataSource.destroyInstance();
        mDataSource = AuthenticationMockDataSource.createInstance();
    }

    @Test(expected = NullPointerException.class)
    public void get_nullCallback_shouldThrow() {
        mDataSource.auth(sCorrectEmail, sCorrectPassword, null);
    }

    @Test
    public void auth_invalidEmail_shouldReturnFailure() {
        mDataSource.auth(sIncorrectEmail, sCorrectPassword, mGetCallback);
        verify(mGetCallback).onFailure(mFailureCaptor.capture());

        String actual = mFailureCaptor.getValue();
        assertNotNull(actual);
    }

    @Test
    public void auth_invalidPassword_shouldReturnFailure() {
        mDataSource.auth(sCorrectEmail, sIncorrectPassword, mGetCallback);
        verify(mGetCallback).onFailure(mFailureCaptor.capture());

        String actual = mFailureCaptor.getValue();
        assertNotNull(actual);
    }

    @Test
    public void auth_validCredentials_shouldReturnCorrectToken() {
        mDataSource.auth(sCorrectEmail, sCorrectPassword, mGetCallback);
        verify(mGetCallback).onSuccess(mSuccessCaptor.capture());

        Authentication actual = mSuccessCaptor.getValue();
        assertNotNull(actual);
        assertEquals(sCorrectAuthenticationToken, actual.getToken());
    }
}