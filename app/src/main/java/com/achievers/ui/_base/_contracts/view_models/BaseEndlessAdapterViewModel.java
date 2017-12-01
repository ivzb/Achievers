package com.achievers.ui._base._contracts.view_models;

import com.achievers.data.entities._base.BaseModel;
import com.achievers.ui._base._contracts.BaseViewModel;
import com.achievers.ui._base._contracts.adapters.BaseAdapter;

public interface BaseEndlessAdapterViewModel<T extends BaseModel>
        extends BaseViewModel {

    int getPage();
    void setPage(int page);

    boolean hasMore();
    void setMore(boolean more);

    BaseAdapter<T> getAdapter();
    void setAdapter(BaseAdapter<T> adapter);

    Long getContainerId();
}