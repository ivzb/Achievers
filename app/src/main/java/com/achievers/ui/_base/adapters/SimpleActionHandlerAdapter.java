package com.achievers.ui._base.adapters;

import android.content.Context;

import com.achievers.data.entities._base.BaseModel;
import com.achievers.ui._base._contracts.action_handlers.BaseAdapterActionHandler;

public abstract class SimpleActionHandlerAdapter<T extends BaseModel>
        extends ActionHandlerAdapter<T, T> {

    public SimpleActionHandlerAdapter(
            Context context,
            BaseAdapterActionHandler<T> actionHandler) {

        super(context, actionHandler);
    }
}
