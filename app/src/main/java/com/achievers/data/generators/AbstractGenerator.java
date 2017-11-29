package com.achievers.data.generators;

import com.achievers.data.entities._base.BaseModel;
import com.achievers.data.generators._base.BaseGenerator;
import com.achievers.data.generators._base.BaseGeneratorConfig;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGenerator<T extends BaseModel>
        implements BaseGenerator<T> {

    final BaseGeneratorConfig mConfig;

    AbstractGenerator(BaseGeneratorConfig config) {
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