package com.achievers.utils;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class DifferenceTableUtilsTest {

    @Test
    public void bigSequence() {
        int[] expected = new int[] { 9, 73, 241, 561, 1081, 1849, 2913 };
        int[] actual = DifferenceTableUtils.findNext(new int[] { 9, 73, 241, 561, 1081, 1849 });

        assertArrayEquals(expected, actual);
    }

    @Test
    public void oneToThree_next() {
        int[] expected = new int[] { 1, 2, 3, 4 };
        int[] actual = DifferenceTableUtils.findNext(new int[] { 1, 2, 3 });

        assertArrayEquals(expected, actual);
    }

    @Test
    public void oneToThree_nextOne() {
        int[] expected = new int[] { 1, 2, 3, 4 };
        int[] actual = DifferenceTableUtils.findNext(1, new int[] { 1, 2, 3 });

        assertArrayEquals(expected, actual);
    }

    @Test
    public void oneToTwo_nextTwo() {
        int[] expected = new int[] { 1, 2, 3, 4 };
        int[] actual = DifferenceTableUtils.findNext(2, new int[] { 1, 2 });

        assertArrayEquals(expected, actual);
    }

    @Test
    public void one_nextThree() {
        int[] expected = new int[] { 1, 2, 3, 4 };
        int[] actual = DifferenceTableUtils.findNext(3, new int[] { 1 });

        assertArrayEquals(expected, actual);
    }

    @Test
    public void oneThreeFive_next() {
        int[] expected = new int[] { 1, 3, 5, 7 };
        int[] actual = DifferenceTableUtils.findNext(new int[] { 1, 3, 5 });

        assertArrayEquals(expected, actual);
    }

    @Test
    public void oneThreeFive_nextOne() {
        int[] expected = new int[] { 1, 3, 5, 7 };
        int[] actual = DifferenceTableUtils.findNext(1, new int[] { 1, 3, 5 });

        assertArrayEquals(expected, actual);
    }

    @Test
    public void oneThree_nextTwo() {
        int[] expected = new int[] { 1, 3, 5, 7 };
        int[] actual = DifferenceTableUtils.findNext(2, new int[] { 1, 3 });

        assertArrayEquals(expected, actual);
    }

    @Test
    public void oneFiveNine_next() {
        int[] expected = new int[] { 1, 5, 9, 13 };
        int[] actual = DifferenceTableUtils.findNext(new int[] { 1, 5, 9 });

        assertArrayEquals(expected, actual);
    }

    @Test
    public void oneFiveNine_nextOne() {
        int[] expected = new int[] { 1, 5, 9, 13 };
        int[] actual = DifferenceTableUtils.findNext(1, new int[] { 1, 5, 9 });

        assertArrayEquals(expected, actual);
    }

    @Test
    public void fiveTenFifteen_next() {
        int[] expected = new int[] { 5, 10, 15, 20 };
        int[] actual = DifferenceTableUtils.findNext(new int[] { 5, 10, 15 });

        assertArrayEquals(expected, actual);
    }

    @Test
    public void fiveTenFifteen_nextOne() {
        int[] expected = new int[] { 5, 10, 15, 20 };
        int[] actual = DifferenceTableUtils.findNext(1, new int[] { 5, 10, 15 });

        assertArrayEquals(expected, actual);
    }

    @Test
    public void fiveTen_nextTwo() {
        int[] expected = new int[] { 5, 10, 15, 20 };
        int[] actual = DifferenceTableUtils.findNext(2, new int[] { 5, 10 });

        assertArrayEquals(expected, actual);
    }

    @Test
    public void five_nextThree() {
        int[] expected = new int[] { 5, 10, 15, 20 };
        int[] actual = DifferenceTableUtils.findNext(3, new int[] { 5 });

        assertArrayEquals(expected, actual);
    }
}