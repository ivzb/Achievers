package com.achievers.ui.add_achievement;

import android.content.Intent;
import android.databinding.Observable;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;

import com.achievers.AchieversDebugTestApplication;
import com.achievers.BuildConfig;
import com.achievers.data.entities.Involvement;
import com.achievers.ui._base._shadows.FileProviderShadow;
import com.achievers.ui._base._shadows.IntentShadow;
import com.achievers.ui._base.contracts.BaseSelectableAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.parceler.Parcels;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.robolectric.Shadows.shadowOf;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.LOLLIPOP,
        constants = BuildConfig.class,
        application = AchieversDebugTestApplication.class,
        shadows = { IntentShadow.class })
public class AddAchievementFragmentTest {

    private @Mock AddAchievementContract.Presenter mPresenter;
    private @Mock AddAchievementViewModel mViewModel;

    private AddAchievementFragment mFragment;

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);

        mFragment = new AddAchievementFragment();
        mFragment.setPresenter(mPresenter);
        mFragment.setViewModel(mViewModel);

        startFragment(mFragment);

        verify(mViewModel).addOnPropertyChangedCallback(isA(Observable.OnPropertyChangedCallback.class));

        verify(mPresenter).start();
        verify(mPresenter).loadInvolvements();
    }

    @After
    public void after() {
        verifyNoMoreInteractions(mViewModel);
        verifyNoMoreInteractions(mPresenter);
    }

    @Test
    public void shouldNotBeNull() throws Exception {
        assertNotNull(mFragment);
    }

    @Test
    public void showInvolvements() {
        // arrange
        List<Involvement> involvements = Arrays.asList(Involvement.values());

        // act
        mFragment.showInvolvements(involvements);

        // assert
        verify(mViewModel).setInvolvements(
                isA(BaseSelectableAdapter.class),
                isA(RecyclerView.LayoutManager.class));
    }

    @Test
    public void takePicture() {
        // arrange
        Uri uri = Uri.parse("fake-uri");
        int requestCode = 1;

        // act
        mFragment.takePicture(uri, requestCode);

        // assert
        Intent intent = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(intent.getAction(), MediaStore.ACTION_IMAGE_CAPTURE);
    }

    @Test
    public void choosePicture() {
        // arrange
        String type = "image/*";
        int requestCode = 2;

        // act
        mFragment.choosePicture(type, requestCode);

        // assert
        Intent intent = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(intent.getAction(), Intent.ACTION_PICK);
    }

    @Test
    public void showPicture_nullUri() {
        // arrange
        Uri uri = null;

        // act
        mFragment.showPicture(uri);

        // assert
        verify(mViewModel).setImageLoading(false);
        verify(mViewModel).setImageUri(null);
    }

    @Test
    public void showPicture_validUri() {
        // arrange
        Uri uri = Uri.parse("fake-uri");

        // act
        mFragment.showPicture(uri);

        // assert
        verify(mViewModel).setImageLoading(true);
        verify(mViewModel).setImageUri(uri);
    }
}