package com.achievers.data.generators._base.contracts;

import com.achievers.data.entities._base.BaseModel;

import java.util.List;

public interface BaseGenerator<T extends BaseModel> {

    T single(long id);
    List<T> multiple(long id, int size);

    T instantiate(long id);
}