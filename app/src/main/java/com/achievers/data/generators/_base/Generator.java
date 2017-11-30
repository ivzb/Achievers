package com.achievers.data.generators._base;

import com.achievers.data.entities._base.BaseModel;
import com.achievers.data.generators._base.contracts.BaseGenerator;
import com.achievers.data.generators._base.contracts.BaseGeneratorConfig;

import java.util.ArrayList;
import java.util.List;

public abstract class Generator<T extends BaseModel>
        implements BaseGenerator<T> {

    protected final BaseGeneratorConfig mConfig;

    public Generator(BaseGeneratorConfig config) {
        mConfig = config;
    }

    @Override
    public T single(long id) {
        return instantiate(id);
    }

    @Override
    public List<T> multiple(long id, int size) {
        List<T> data = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            T generated = single(id + i);
            data.add(generated);
        }

        return data;
    }
}