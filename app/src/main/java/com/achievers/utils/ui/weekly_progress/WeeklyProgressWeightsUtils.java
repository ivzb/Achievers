package com.achievers.utils.ui.weekly_progress;

import android.support.annotation.VisibleForTesting;

import com.achievers.utils.DifferenceTableUtils;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

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
        int weightsSize = 7;
        int dividerSize = 4;

        if (weights.length < weightsSize || weights.length > weightsSize) {
            weights = Arrays.copyOf(weights, weightsSize); // truncate or pad
        }

        int[] evaluation = new int[weights.length];
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        Set<Integer> uniqueNumbers = new TreeSet<>();

        for (int i = 0; i < weights.length; i++) {
            if (weights[i] < 0) weights[i] = 0;
            int value = weights[i];

            if (value != 0) {
                uniqueNumbers.add(value);
                if (min > value) min = value;
                if (max < value) max = value;
            }
        }

        double divider = Math.min(uniqueNumbers.size(), dividerSize);

        if (divider < dividerSize) {
            int size = (int) divider;
            int[] sequence = new int[size];

            int index = 0;

            for (Integer weight: uniqueNumbers) {
                sequence[index] = weight;
                index++;
            }

            int[] predictedWeights = DifferenceTableUtils.findNext(dividerSize - size, sequence);

            max = predictedWeights[predictedWeights.length - 1];

            divider = dividerSize;
        }

        double interval = (max - min) / divider;
        double start = min;

        for (int i = 0; i < dividerSize; i++) {
            double end = start + interval;

            for (int j = 0; j < weights.length; j++) {
                int weight = weights[j];

                if (weight >= start && weight <= end) {
                    weights[j] = -1; // should not be used anymore
                    evaluation[j] = i + 1;
                }
            }

            start += interval;
        }

        return evaluation;
    }
}