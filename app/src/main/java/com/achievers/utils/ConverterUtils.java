package com.achievers.utils;

import java.util.Set;

public class ConverterUtils {

    public static int[] setToArray(Set<Integer> set) {
        int[] array = new int[set.size()];
        int index = 0;

        for (Integer weight: set) {
            array[index] = weight;
            index++;
        }

        return array;
    }
}
