package com.achievers.data.generators;

import android.net.Uri;

import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Involvement;
import com.achievers.data.entities.Quest;
import com.achievers.data.entities.Reward;
import com.achievers.data.entities._base.BaseModel;
import com.achievers.data.generators._base.BaseGenerator;
import com.achievers.data.generators._base.BaseGeneratorConfig;
import com.achievers.data.generators._base.BaseGeneratorTest;
import com.achievers.utils.ui.multimedia.MultimediaType;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

public abstract class AbstractGeneratorTest<T extends BaseModel>
        implements BaseGeneratorTest<T> {

    static String sFailMessage = "Generated entity does not match";

    private BaseGenerator<T> mGenerator;

    static final long sId = 5L;
    static final String sWord = "word";
    static final String sSentence = "sentence";
    static final int sNumber = 42;
    static final String sImageUrl = "http://image.url";
    static final Uri sImageUri = Uri.parse(sImageUrl);
    static final String sVideoUrl = "http://video.url";
    static final Uri sVideoUri = Uri.parse(sVideoUrl);
    static final String sVoiceUrl = "http://voice.url";
    static final Uri sVoiceUri = Uri.parse(sVoiceUrl);
    static final Date sDate = new Date(2017, 11, 23);

    static final Involvement sInvolvement = Involvement.Gold;
    static final Achievement.Type sAchievementType = Achievement.Type.Progressive;
    static final MultimediaType sMultimediaType = MultimediaType.Video;
    static final Quest.Type sQuestType = Quest.Type.Daily;
    static final Reward.Type sRewardType = Reward.Type.Item;

    @Mock BaseGeneratorConfig mGeneratorConfig;

    @Override
    public BaseGenerator<T> getGenerator() {
        return mGenerator;
    }

    @Override
    public void setGenerator(BaseGenerator<T> generator) {
        mGenerator = generator;
    }

    @Before
    public void before() {
        when(mGeneratorConfig.getId()).thenReturn(sId);
        when(mGeneratorConfig.getWord()).thenReturn(sWord);
        when(mGeneratorConfig.getSentence()).thenReturn(sSentence);
        when(mGeneratorConfig.getNumber()).thenReturn(sNumber);
        when(mGeneratorConfig.getNumber(anyInt())).thenReturn(sNumber);
        when(mGeneratorConfig.getImageUrl()).thenReturn(sImageUrl);
        when(mGeneratorConfig.getImageUri()).thenReturn(sImageUri);
        when(mGeneratorConfig.getVideoUrl()).thenReturn(sVideoUrl);
        when(mGeneratorConfig.getVideoUri()).thenReturn(sVideoUri);
        when(mGeneratorConfig.getVoiceUrl()).thenReturn(sVoiceUrl);
        when(mGeneratorConfig.getVideoUri()).thenReturn(sVoiceUri);
        when(mGeneratorConfig.getDate()).thenReturn(sDate);

        when(mGeneratorConfig.getEnum(isA(Involvement[].class))).thenReturn(sInvolvement);
        when(mGeneratorConfig.getEnum(isA(Achievement.Type[].class))).thenReturn(sAchievementType);
        when(mGeneratorConfig.getEnum(isA(MultimediaType[].class))).thenReturn(sMultimediaType);
        when(mGeneratorConfig.getEnum(isA(Quest.Type[].class))).thenReturn(sQuestType);
        when(mGeneratorConfig.getEnum(isA(Reward.Type[].class))).thenReturn(sRewardType);
    }

    @Test
    public void instantiate() {
        // arrange
        T expected = instantiate(sId);

        // act
        T actual = getGenerator().instantiate(sId);

        // assert
        assertEquals(sFailMessage, expected, actual);
    }

    @Test
    public void single() {
        // arrange
        long id = 5;
        T expected = instantiate(id);

        // act
        T actual = mGenerator.single(id);

        // assert
        assertEquals(sFailMessage, expected.getId(), actual.getId());
    }

    @Test
    public void multiple() {
        // arrange
        long id = 5;
        int size = 5;

        // act
        List<T> actual = mGenerator.multiple(id, size);

        // assert
        for (int i = 0; i < size; i++) {
            T expected = instantiate(id + i);
            assertEquals(sFailMessage, expected.getId(), actual.get(i).getId());
        }
    }

    @Test
    public void getAmong_min() {
        int size = 4;
        int[] expectedIds = new int[] { 0 };

        getAmong(size, expectedIds);
    }

    @Test
    public void getAmong_medium() {
        int size = 4;
        int[] expectedIds = new int[] { 0, 2 };

        getAmong(size, expectedIds);
    }

    @Test
    public void getAmong_max() {
        int size = 4;
        int[] expectedIds = new int[] { 0, 1, 2, 3 };

        getAmong(size, expectedIds);
    }

    private void getAmong(int size, int[] expectedIds) {
        ArrayList<T> entities = new ArrayList<>();

        for (int i = 1; i <= size; i++) {
            entities.add(instantiate(i));
        }

        ArrayList<T> expected = new ArrayList<>();

        for (int id: expectedIds) {
            expected.add(entities.get(id));
        }

        when(mGeneratorConfig.getNumber(anyInt())).thenReturn(expectedIds.length);

        // act
        ArrayList<BaseModel> actual = (ArrayList<BaseModel>) mGenerator.getAmong(entities);

        // assert
        assertEquals(expected.size(), actual.size());

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), actual.get(i));
        }
    }
}