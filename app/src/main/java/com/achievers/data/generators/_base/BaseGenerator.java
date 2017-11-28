package com.achievers.data.generators._base;

import com.achievers.data.entities._base.BaseModel;

import java.util.List;

public interface BaseGenerator<T extends BaseModel> {

    T single(long id);
    List<T> multiple(long id, int size);
    List<T> getAmong(List<T> entities);

    T instantiate(long id);
}