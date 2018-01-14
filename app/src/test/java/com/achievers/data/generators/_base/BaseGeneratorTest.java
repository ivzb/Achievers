package com.achievers.data.generators._base;

import com.achievers.data.entities._base.BaseModel;
import com.achievers.data.generators._base.contracts.BaseGenerator;

public interface BaseGeneratorTest<T extends BaseModel> {

    BaseGenerator<T> getGenerator();
    void setGenerator(BaseGenerator<T> generator);

    T instantiate(String id);
}