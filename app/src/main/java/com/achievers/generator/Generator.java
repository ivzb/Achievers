package com.achievers.generator;

import com.achievers.generator._base.BaseGenerator;

import java.util.ArrayList;
import java.util.List;

public abstract class Generator<T> implements BaseGenerator<T> {

    public List<T> multiple(long id, int size) {
        List<T> data = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            T generated = single(id + i);
            data.add(generated);
        }

        return data;
    }
}
