package com.achievers.ui.add_evidence;

import android.content.Context;

import com.achievers.utils.GeneratorUtils;
import com.google.android.exoplayer2.SimpleExoPlayer;

import org.junit.After;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Random;

import io.bloco.faker.Faker;

import static org.mockito.Mockito.verifyNoMoreInteractions;

public class AddEvidencePresenterTest {

    @Mock private Context mContext;
    @Mock private AddEvidenceContract.View mView;

    @Mock private SimpleExoPlayer mSimpleExoPlayer;

    private AddEvidenceContract.Presenter mPresenter;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        GeneratorUtils.initialize(new Random(), new Faker());

        mPresenter = new AddEvidencePresenter(
                mContext,
                mView,
                mSimpleExoPlayer);
    }

    @After
    public void after() {
        verifyNoMoreInteractions(mView);
        verifyNoMoreInteractions(mSimpleExoPlayer);
    }
}