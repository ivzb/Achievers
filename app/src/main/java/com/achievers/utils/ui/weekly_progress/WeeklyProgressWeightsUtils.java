package com.achievers.utils.ui.weekly_progress;

import android.support.annotation.VisibleForTesting;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
        Set<Integer> uniqueNumbers = new HashSet<>();

        for (int i = 0; i < weights.length; i++) {
            if (weights[i] < 0) weights[i] = 0;
            int value = weights[i];

            if (value != 0) uniqueNumbers.add(value);
            if (min > value && value != 0) min = value;
            if (max < value) max = value;
        }

        double divider = uniqueNumbers.size();
        if (divider > 4) divider = 4;

        double interval = (max - min) / divider;

        for (int i = 0; i < weights.length; i++) {
            if (weights[i] == 0) {
                evaluation[i] = Weight.Empty.index;
                continue;
            }

            double start = min;

            for (int j = 1; j < 5; ++j) {
                double end = start + interval;
                int weight = weights[i];

                if (weight >= start && weight <= end) {
                    evaluation[i] = Weight.values()[j].index;
                    break;
                }

                start += interval;
            }
        }

        return evaluation;
    }
}
