package com.achievers.data.sources.user;

import com.achievers.MockConfig;
import com.achievers.data.Result;
import com.achievers.data.callbacks.SaveCallback;
import com.achievers.data.entities.Auth;
import com.achievers.data.generators.config.GeneratorConfig;

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
public class UserMockDataSourceTest {

    private UserMockDataSource mDataSource;

    private static final String sCorrectEmail = MockConfig.Email;
    private static final String sCorrectPassword = MockConfig.Password;
    private static final String sCorrectAuthenticationToken = MockConfig.Token;

    private static final String sIncorrectEmail = "incorrect_email";
    private static final String sIncorrectPassword = "incorrect_password";

    @Mock private SaveCallback<String> mSaveCallback;

    @Captor private ArgumentCaptor<Result<String>> mSuccessCaptor;
    @Captor private ArgumentCaptor<String> mFailureCaptor;

    @Before
    public void before() {
        GeneratorConfig.destroyInstance();
        GeneratorConfig.initialize(new Random(), new Faker());

        UserMockDataSource.destroyInstance();
        mDataSource = UserMockDataSource.createInstance();
    }

    @Test(expected = NullPointerException.class)
    public void get_nullCallback_shouldThrow() {
        Auth auth = new Auth(sCorrectEmail, sCorrectPassword);
        mDataSource.auth(auth, null);
    }

    @Test
    public void auth_invalidEmail_shouldReturnFailure() {
        Auth auth = new Auth(sIncorrectEmail, sCorrectPassword);
        mDataSource.auth(auth, mSaveCallback);
        verify(mSaveCallback).onFailure(mFailureCaptor.capture());

        String actual = mFailureCaptor.getValue();
        assertNotNull(actual);
    }

    @Test
    public void auth_invalidPassword_shouldReturnFailure() {
        Auth auth = new Auth(sCorrectEmail, sIncorrectPassword);
        mDataSource.auth(auth, mSaveCallback);
        verify(mSaveCallback).onFailure(mFailureCaptor.capture());

        String actual = mFailureCaptor.getValue();
        assertNotNull(actual);
    }

    @Test
    public void auth_validCredentials_shouldReturnCorrectToken() {
        Auth auth = new Auth(sCorrectEmail, sCorrectPassword);
        mDataSource.auth(auth, mSaveCallback);
        verify(mSaveCallback).onSuccess(mSuccessCaptor.capture());

        Result<String> data = mSuccessCaptor.getValue();
        String actual = data.getResults();
        assertNotNull(actual);
        assertEquals(sCorrectAuthenticationToken, actual);
    }
}