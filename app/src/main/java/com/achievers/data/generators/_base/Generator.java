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
    public T single() {
        return instantiate();
    }

    @Override
    public List<T> multiple(int size) {
        List<T> data = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            data.add(single());
        }

        return data;
    }
}