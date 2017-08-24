package com.achievers.seed.generator;

import com.achievers.entities.Category;

import java.util.List;
import java.util.Locale;

public interface Generator<T> {

    T single();
    List<T> multiple(int size);
}
