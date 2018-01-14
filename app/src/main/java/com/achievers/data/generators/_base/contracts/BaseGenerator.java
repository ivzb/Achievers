package com.achievers.data.generators._base.contracts;

import com.achievers.data.entities._base.BaseModel;

import java.util.List;

public interface BaseGenerator<T extends BaseModel> {

    T single();
    List<T> multiple(int size);

    T instantiate();
}