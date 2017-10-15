package com.achievers.ui._base.contracts;

import com.achievers.data.entities._base.BaseModel;

public interface BaseActionHandler<T extends BaseModel> {

    void onAdapterEntityClick(T entity);
}
