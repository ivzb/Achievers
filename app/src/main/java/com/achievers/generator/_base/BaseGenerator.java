package com.achievers.generator._base;

import java.util.List;

public interface BaseGenerator<T> {

    T single(long id);
    List<T> multiple(long id, int size);
}