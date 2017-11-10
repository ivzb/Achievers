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

import static com.achievers.utils.FileUtils.FileType.Picture;
import static com.achievers.utils.FileUtils.FileType.Voice;
import static junit.framework.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class FileUtilsTest {

    @Rule public MockitoRule mMockitoRule = MockitoJUnit.rule();

    @Mock private Context mContext;

    private Date mDate;
    private FileUtils.FileType mFileType;

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
        mFileType = Picture;
    }

    @Test(expected = NullPointerException.class)
    public void nullContext() throws IOException {
        FileUtils.createFile(null, mDate, mFileType);
    }

    @Test(expected = NullPointerException.class)
    public void nullDate() throws IOException {
        FileUtils.createFile(mContext, null, mFileType);
    }

    @Test(expected = NullPointerException.class)
    public void nullFileType() throws IOException {
        FileUtils.createFile(mContext, mDate, null);
    }

    @Test
    public void createdFileName_picture_shouldBeCorrect() throws IOException {
        mFileType = Picture;

        File createdFile = FileUtils.createFile(mContext, mDate, mFileType);

        String nameRegex = "^JPEG_20171010_092040_[\\d]+\\.jpg$";
        boolean isNameCorrect = createdFile.getName().matches(nameRegex);

        assertTrue(isNameCorrect);

        createdFile.deleteOnExit();
    }

    @Test
    public void createdFileName_voice_shouldBeCorrect() throws IOException {
        mFileType = Voice;

        File createdFile = FileUtils.createFile(mContext, mDate, mFileType);

        String nameRegex = "^VOICE_20171010_092040_[\\d]+\\.3gp";
        boolean isNameCorrect = createdFile.getName().matches(nameRegex);

        assertTrue(isNameCorrect);

        createdFile.deleteOnExit();
    }
}
