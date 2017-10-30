package com.achievers.utils.ui.weekly_progress;

import android.support.annotation.VisibleForTesting;

import java.util.Arrays;

import static com.achievers.utils.ui.weekly_progress.WeeklyProgressWeightsUtils.Weight.Dark;
import static com.achievers.utils.ui.weekly_progress.WeeklyProgressWeightsUtils.Weight.Light;
import static com.achievers.utils.ui.weekly_progress.WeeklyProgressWeightsUtils.Weight.MediumDark;
import static com.achievers.utils.ui.weekly_progress.WeeklyProgressWeightsUtils.Weight.MediumLight;

@VisibleForTesting
public class WeeklyProgressWeightsUtils {

    public enum Weight {
        Empty,
        Light,
        MediumLight,
        MediumDark,
        Dark;

        @VisibleForTesting
        public int index = ordinal();
    }

    @VisibleForTesting
    public static int[] evaluate(int[] weights) {
        if (weights.length < 7 || weights.length > 7) { // truncate or pad
            weights = Arrays.copyOf(weights, 7);
        }

        int[] evaluation = new int[weights.length];
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        int avg = 0;
        int zeros = 0;

        for (int i = 0; i < weights.length; i++) {
            if (weights[i] < 0) weights[i] = 0;
            int value = weights[i];

            if (value == 0) zeros++;
            if (min > value && value > 0) min = value;
            if (max < value) max = value;
            avg += value;
        }

        int nonZerosLength = weights.length - zeros;
        if (nonZerosLength > 0) avg /= nonZerosLength;

        for (int i = 0; i < weights.length; i++) {
            int weight = weights[i];

            if (weight >= 0 && weight < Weight.values().length) {
                evaluation[i] = Weight.values()[weight].index;
                continue;
            }

            if (weight == min) {
                evaluation[i] = Light.index;
                continue;
            }

            if (weight == max) {
                evaluation[i] = Dark.index;
                continue;
            }

            if (weight <= avg) {
                evaluation[i] = MediumLight.index;
                continue;
            }

            if (weight > avg) {
                evaluation[i] = MediumDark.index;
                continue;
            }
        }

        return evaluation;
    }
}
