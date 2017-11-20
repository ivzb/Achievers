package com.achievers.ui._base._contracts.adapters;

import com.achievers.data.entities._base.BaseModel;

public interface BaseMultimediaAdapter<T extends BaseModel> extends BaseAdapter<T> {

    void releaseMedia();
}