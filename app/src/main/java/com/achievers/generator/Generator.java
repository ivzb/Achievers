package com.achievers.generator;

import java.util.List;

public interface Generator<T> {

    T single(long id, Long parentId);
    List<T> multiple(int[] sizes);
}
