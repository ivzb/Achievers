package com.achievers.ui._base.adapters;

import android.content.Context;

import com.achievers.data.entities._base.BaseModel;
import com.achievers.ui._base.AbstractAdapter;
import com.achievers.ui._base._contracts.action_handlers.BaseAdapterActionHandler;

public abstract class ActionHandlerAdapter<T extends BaseModel>
        extends AbstractAdapter<T> {

    protected BaseAdapterActionHandler<T> mActionHandler;

    public ActionHandlerAdapter(
            Context context,
            BaseAdapterActionHandler<T> actionHandler) {

        super(context);
        mActionHandler = actionHandler;
    }
}