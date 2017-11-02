package com.achievers.utils.ui.weekly_progress;

import com.achievers.utils.ConverterUtils;
import com.achievers.utils.DifferenceTableUtils;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class WeeklyProgressWeightsEvaluator {

    private static final int WeightsSize = 7;
    private static final int DividerSize = 4;
    private static final int UsedWeight = -1;

    public enum Weight {
        Empty,
        Light,
        MediumLight,
        MediumDark,
        Dark;

        public int index = ordinal();
    }

    public int[] evaluate(int[] weights) {
        if (weights.length < WeightsSize || weights.length > WeightsSize) {
            weights = Arrays.copyOf(weights, WeightsSize); // truncate or pad
        }

        int[] uniques = getUniques(weights);
        int[] evaluation = new int[WeightsSize];

        if (uniques.length == 0) return evaluation;

        double interval = calculateInterval(uniques);
        double start = uniques[0];

        for (int i = 0; i < DividerSize; i++) {
            double end = start + interval;

            for (int j = 0; j < weights.length; j++) {
                int weight = weights[j];

                if (weight != UsedWeight && weight >= start && weight <= end) {
                    weights[j] = UsedWeight; // should not be used anymore
                    evaluation[j] = i + 1;
                }
            }

            start += interval;
        }

        return evaluation;
    }

    private double calculateInterval(int[] sequence) {
        if (sequence.length < DividerSize) {
            int elementsNeeded = DividerSize - sequence.length;
            sequence = DifferenceTableUtils.findNext(elementsNeeded, sequence);
        }

        int min = sequence[0];
        int max = sequence[sequence.length - 1];

        return (max - min) / (double) DividerSize;
    }

    private int[] getUniques(int[] weights) {
        Set<Integer> uniques = new TreeSet<>();

        for (int weight: weights) {
            if (weight > 0) uniques.add(weight);
        }

        return ConverterUtils.setToArray(uniques);
    }
}