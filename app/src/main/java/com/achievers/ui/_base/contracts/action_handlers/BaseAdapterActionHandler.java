package com.achievers.ui._base.contracts.action_handlers;

import com.achievers.data.entities._base.BaseModel;

public interface BaseAdapterActionHandler<T extends BaseModel> {

    void onAdapterEntityClick(T entity);
}
