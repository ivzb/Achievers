package com.achievers.generator;

import java.util.List;

public interface Generator<T> {

    T single(int id, Integer parentId);
    List<T> multiple(int[] sizes);
}
