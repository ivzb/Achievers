package com.achievers.utils;

import android.content.Context;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import static junit.framework.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class PictureUtilsTest {

    @Rule public MockitoRule mMockitoRule = MockitoJUnit.rule();

    @Mock private Context mContext;

    private Date mDate;

    @Before
    public void setup() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2017);
        calendar.set(Calendar.MONTH, 9);
        calendar.set(Calendar.DATE, 10);
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 20);
        calendar.set(Calendar.SECOND, 40);
        calendar.set(Calendar.MILLISECOND, 50);

        mDate = calendar.getTime();
    }

    @Test(expected = NullPointerException.class)
    public void nullContext() throws IOException {
        PictureUtils.createFile(null, mDate);
    }

    @Test(expected = NullPointerException.class)
    public void nullDate() throws IOException {
        PictureUtils.createFile(mContext, null);
    }

    @Test
    public void createdFileName_shouldBeCorrect() throws IOException {
        File createdFile = PictureUtils.createFile(mContext, mDate);

        String nameRegex = "^JPEG_20171010_092040_[\\d]+\\.jpg$";
        boolean isNameCorrect = createdFile.getName().matches(nameRegex);

        assertTrue(isNameCorrect);

        createdFile.deleteOnExit();
    }
}
