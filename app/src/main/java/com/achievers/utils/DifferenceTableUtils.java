package com.achievers.utils;

import java.util.Arrays;

public class DifferenceTableUtils {

    private DifferenceTableUtils() {

    }

    public static int[] findNext(int n, int[] sequence) {
        for (int i = 0; i < n; i++) {
            sequence = findNext(sequence);
        }

        return sequence;
    }

    public static int[] findNext(int[] sequence) {
        if (sequence.length == 0) return new int[] { 0 };
        if (sequence.length == 1) return new int[] { sequence[0], sequence[0] * 2 };

        int[] diff = new int[sequence.length - 1];

        for (int i = 0; i < sequence.length - 1; i++) {
            diff[i] = sequence[i + 1] - sequence[i];
        }

        boolean same = true;

        for (int i = 0; i < diff.length - 1; i++) {
            same &= diff[i] == diff[i + 1];
        }

        if (same) {
            diff = Arrays.copyOf(diff, diff.length + 1);
            diff[diff.length - 1] = diff[0];
        } else {
            diff = findNext(diff);
        }

        int next = sequence[sequence.length - 1] + diff[diff.length - 1];

        sequence = Arrays.copyOf(sequence, sequence.length + 1);
        sequence[sequence.length - 1] = next;

        return sequence;
    }
}