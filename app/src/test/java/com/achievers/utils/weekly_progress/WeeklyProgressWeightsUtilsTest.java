package com.achievers.utils.weekly_progress;

import com.achievers.utils.ui.weekly_progress.WeeklyProgressWeightsUtils;

import org.junit.Test;

import static com.achievers.utils.ui.weekly_progress.WeeklyProgressWeightsUtils.Weight.Dark;
import static com.achievers.utils.ui.weekly_progress.WeeklyProgressWeightsUtils.Weight.Empty;
import static com.achievers.utils.ui.weekly_progress.WeeklyProgressWeightsUtils.Weight.Light;
import static com.achievers.utils.ui.weekly_progress.WeeklyProgressWeightsUtils.Weight.MediumDark;
import static com.achievers.utils.ui.weekly_progress.WeeklyProgressWeightsUtils.Weight.MediumLight;
import static junit.framework.Assert.assertEquals;

public class WeeklyProgressWeightsUtilsTest {

    @Test
    public void evaluate_shorterLength_shouldPadWithZeros() {
        int[] expected = new int[7];
        int[] actual = WeeklyProgressWeightsUtils.evaluate(new int[5]);

        assertArrayEquals(expected, actual);
    }

    @Test
    public void evaluate_longerLength_shouldTrim() {
        int[] expected = new int[7];
        int[] actual = WeeklyProgressWeightsUtils.evaluate(new int[10]);

        assertArrayEquals(expected, actual);
    }

    @Test
    public void evaluate_negativeValues_shouldMakeThemZeros() {
        int[] expected = new int[7];
        int[] actual = WeeklyProgressWeightsUtils.evaluate(new int[] { -1, -2, -3, 0, -5, -2, 0 });

        assertArrayEquals(expected, actual);
    }

    @Test
    public void evaluate_zeros_shouldReturnAllEmpty() {
        int[] weights = new int[7];
        int[] expected = WeeklyProgressWeightsUtils.evaluate(weights);
        int[] actual = new int[7];

        assertArrayEquals(expected, actual);
    }

    @Test
    public void evaluate_sameZero_shouldReturnAllEmpty() {
        int[] weights = new int[7];
        int[] expected = new int[7];

        for (int j = 0; j < 7; j++) {
            weights[j] = 0;
            expected[j] = WeeklyProgressWeightsUtils.Weight.Empty.index;
        }

        int[] actual = WeeklyProgressWeightsUtils.evaluate(weights);

        assertArrayEquals(expected, actual);
    }

    @Test
    public void evaluate_sameNotZero_shouldReturnAllLight() {
        int[] weights = new int[7];
        int[] expected = new int[7];

        for (int j = 0; j < 7; j++) {
            weights[j] = 1;
            expected[j] = WeeklyProgressWeightsUtils.Weight.Light.index;
        }

        int[] actual = WeeklyProgressWeightsUtils.evaluate(weights);

        assertArrayEquals(expected, actual);
    }

    @Test
    public void evaluate_twoPositiveValues_shouldReturnLightAndMediumLight() {
        int[] weights = new int[7];
        weights[0] = 1;
        weights[1] = 2;

        int[] expected = new int[7];
        expected[0] = WeeklyProgressWeightsUtils.Weight.Light.index;
        expected[1] = WeeklyProgressWeightsUtils.Weight.MediumLight.index;

        int[] actual = WeeklyProgressWeightsUtils.evaluate(weights);

        assertArrayEquals(expected, actual);
    }

    @Test
    public void evaluate_threePositiveValues_shouldReturnLightAndMediumLightAndMediumDark() {
        int[] weights = new int[7];
        weights[0] = 1;
        weights[1] = 2;
        weights[2] = 3;

        int[] expected = new int[7];
        expected[0] = WeeklyProgressWeightsUtils.Weight.Light.index;
        expected[1] = WeeklyProgressWeightsUtils.Weight.MediumLight.index;
        expected[2] = WeeklyProgressWeightsUtils.Weight.MediumDark.index;

        int[] actual = WeeklyProgressWeightsUtils.evaluate(weights);

        assertArrayEquals(expected, actual);
    }

    @Test
    public void evaluate_greaterThreePositiveValues_shouldReturnLightAndMediumLightAndMediumDark() {
        int[] weights = new int[7];
        weights[0] = 1;
        weights[1] = 3;
        weights[2] = 5;

        int[] expected = new int[7];
        expected[0] = WeeklyProgressWeightsUtils.Weight.Light.index;
        expected[1] = WeeklyProgressWeightsUtils.Weight.MediumLight.index;
        expected[2] = WeeklyProgressWeightsUtils.Weight.MediumDark.index;

        int[] actual = WeeklyProgressWeightsUtils.evaluate(weights);

        assertArrayEquals(expected, actual);
    }

    @Test
    public void evaluate_evenGreaterThreePositiveValues_shouldReturnLightAndMediumLightAndMediumDark() {
        int[] weights = new int[7];
        weights[0] = 1;
        weights[1] = 5;
        weights[2] = 9;

        int[] expected = new int[7];
        expected[0] = WeeklyProgressWeightsUtils.Weight.Light.index;
        expected[1] = WeeklyProgressWeightsUtils.Weight.MediumLight.index;
        expected[2] = WeeklyProgressWeightsUtils.Weight.MediumDark.index;

        int[] actual = WeeklyProgressWeightsUtils.evaluate(weights);

        assertArrayEquals(expected, actual);
    }

    @Test
    public void evaluate_fourPositiveValues_shouldReturnLight_MediumLight_MediumDark_Dark() {
        int[] weights = new int[7];
        weights[0] = 1;
        weights[1] = 2;
        weights[2] = 3;
        weights[3] = 4;

        int[] expected = new int[7];
        expected[0] = WeeklyProgressWeightsUtils.Weight.Light.index;
        expected[1] = WeeklyProgressWeightsUtils.Weight.MediumLight.index;
        expected[2] = WeeklyProgressWeightsUtils.Weight.MediumDark.index;
        expected[3] = WeeklyProgressWeightsUtils.Weight.Dark.index;

        int[] actual = WeeklyProgressWeightsUtils.evaluate(weights);

        assertArrayEquals(expected, actual);
    }

    @Test
    public void evaluate_increasingUntilDarkThenDecreasing_shouldReturnAllWeights() {
        int[] weights = new int[7];
        weights[0] = 0;
        weights[1] = 1;
        weights[2] = 2;
        weights[3] = 3;
        weights[4] = 4;
        weights[5] = 3;
        weights[6] = 2;

        int[] actual = WeeklyProgressWeightsUtils.evaluate(weights);

        int[] expected = new int[7];
        expected[0] = Empty.index;
        expected[1] = Light.index;
        expected[2] = MediumLight.index;
        expected[3] = MediumDark.index;
        expected[4] = Dark.index;
        expected[5] = MediumDark.index;
        expected[6] = MediumLight.index;

        assertArrayEquals(expected, actual);
    }

    @Test
    public void evaluate_biggerNumbers_increasingUntilDarkThenDecreasing_shouldReturnAllWeights() {
        int[] weights = new int[7];
        weights[0] = 0;
        weights[1] = 5;
        weights[2] = 6;
        weights[3] = 7;
        weights[4] = 8;
        weights[5] = 7;
        weights[6] = 6;

        int[] actual = WeeklyProgressWeightsUtils.evaluate(weights);

        int[] expected = new int[7];
        expected[0] = Empty.index;
        expected[1] = Light.index;
        expected[2] = MediumLight.index;
        expected[3] = MediumDark.index;
        expected[4] = Dark.index;
        expected[5] = MediumDark.index;
        expected[6] = MediumLight.index;

        assertArrayEquals(expected, actual);
    }

    @Test
    public void evaluate_evenBiggerNumbers_increasingUntilDarkThenDecreasing_shouldReturnAllWeights() {
        int[] weights = new int[7];
        weights[0] = 0;
        weights[1] = 5;
        weights[2] = 7;
        weights[3] = 9;
        weights[4] = 11;
        weights[5] = 9;
        weights[6] = 7;

        int[] actual = WeeklyProgressWeightsUtils.evaluate(weights);

        int[] expected = new int[7];
        expected[0] = Empty.index;
        expected[1] = Light.index;
        expected[2] = MediumLight.index;
        expected[3] = MediumDark.index;
        expected[4] = Dark.index;
        expected[5] = MediumDark.index;
        expected[6] = MediumLight.index;

        assertArrayEquals(expected, actual);
    }

    @Test
    public void evaluate_smallNumbersWithBiggerNumbers_increasingUntilDark() {
        int[] weights = new int[7];
        weights[0] = 3;
        weights[1] = 4;
        weights[2] = 5;
        weights[3] = 6;

        int[] actual = WeeklyProgressWeightsUtils.evaluate(weights);

        int[] expected = new int[7];
        expected[0] = Light.index;
        expected[1] = MediumLight.index;
        expected[2] = MediumDark.index;
        expected[3] = Dark.index;

        assertArrayEquals(expected, actual);
    }


    @Test
    public void evaluate_interval5_oneWeight() {
        int[] weights = new int[7];
        weights[0] = 5;

        int[] actual = WeeklyProgressWeightsUtils.evaluate(weights);

        int[] expected = new int[7];
        expected[0] = Light.index;

        assertArrayEquals(expected, actual);
    }

    @Test
    public void evaluate_interval5_twoWeights() {
        int[] weights = new int[7];
        weights[0] = 5;
        weights[1] = 10;

        int[] actual = WeeklyProgressWeightsUtils.evaluate(weights);

        int[] expected = new int[7];
        expected[0] = Light.index;
        expected[1] = MediumLight.index;

        assertArrayEquals(expected, actual);
    }

    @Test
    public void evaluate_interval5_threeWeights() {
        int[] weights = new int[7];
        weights[0] = 5;
        weights[1] = 10;
        weights[2] = 15;

        int[] actual = WeeklyProgressWeightsUtils.evaluate(weights);

        int[] expected = new int[7];
        expected[0] = Light.index;
        expected[1] = MediumLight.index;
        expected[2] = MediumDark.index;

        assertArrayEquals(expected, actual);
    }

    @Test
    public void evaluate_interval5_fourWeights() {
        int[] weights = new int[7];
        weights[0] = 5;
        weights[1] = 10;
        weights[2] = 15;
        weights[3] = 20;

        int[] actual = WeeklyProgressWeightsUtils.evaluate(weights);

        int[] expected = new int[7];
        expected[0] = Light.index;
        expected[1] = MediumLight.index;
        expected[2] = MediumDark.index;
        expected[3] = Dark.index;

        assertArrayEquals(expected, actual);
    }

    @Test
    public void evaluate_differentNumbers() {
        // weights = { 4, 2, 6, 14, 8, 4, 11 }
        // min = 2, max = 14
        // interval = (max - min) / 4 = 3

        // 0     // empty
        // 2-5   // light
        // 6-9   // mediumLight
        // 10-13  // mediumDark
        // 14-17 // dark

        int[] weights = new int[7];
        weights[0] = 4;
        weights[1] = 2;
        weights[2] = 6;
        weights[3] = 14;
        weights[4] = 8;
        weights[5] = 4;
        weights[6] = 11;

        int[] actual = WeeklyProgressWeightsUtils.evaluate(weights);

        int[] expected = new int[7];
        expected[0] = Light.index;
        expected[1] = Light.index;
        expected[2] = MediumLight.index;
        expected[3] = Dark.index;
        expected[4] = MediumLight.index;
        expected[5] = Light.index;
        expected[6] = MediumDark.index;

        assertArrayEquals(expected, actual);
    }

    private void assertArrayEquals(int[] expected, int[] actual) {
        for (int i = 0; i < expected.length; i++) {
            WeeklyProgressWeightsUtils.Weight expectedWeight = WeeklyProgressWeightsUtils.Weight.values()[expected[i]];
            WeeklyProgressWeightsUtils.Weight actualWeight = WeeklyProgressWeightsUtils.Weight.values()[actual[i]];
            String message = String.format("\r\nindex = %d\r\nexpected: %s\r\nactual: %s",
                    i,
                    expectedWeight,
                    actualWeight);

            assertEquals(message, expected[i], actual[i]);
        }
    }
}