package com.achievers.ui._base.adapters;

import android.content.Context;

import com.achievers.data.entities._base.BaseModel;
import com.achievers.ui._base.AbstractAdapter;
import com.achievers.ui._base._contracts.action_handlers.BaseAdapterActionHandler;

public abstract class ActionHandlerAdapter<T extends BaseModel, K extends BaseModel>
        extends AbstractAdapter<T> {

    protected BaseAdapterActionHandler<K> mActionHandler;

    public ActionHandlerAdapter(
            Context context,
            BaseAdapterActionHandler<K> actionHandler) {

        super(context);
        mActionHandler = actionHandler;
    }
}