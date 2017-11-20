package com.achievers.ui.quests.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.achievers.BR;
import com.achievers.data.entities.Quest;
import com.achievers.databinding.QuestsRecyclerItemBinding;
import com.achievers.ui._base.AbstractAdapter;
import com.achievers.ui._base._contracts.action_handlers.BaseAdapterActionHandler;

public class QuestsAdapter extends AbstractAdapter<Quest> {

    public QuestsAdapter(Context context, BaseAdapterActionHandler<Quest> actionHandler) {
        super(context, actionHandler);
    }

    @Override
    public QuestsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        QuestsRecyclerItemBinding binding = QuestsRecyclerItemBinding.inflate(layoutInflater, parent, false);

        return new ViewHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Quest quest = mEntities.get(position);

        viewHolder.getBinding().setVariable(BR.quest, quest);
        viewHolder.getBinding().setVariable(BR.resources, mContext.getResources());
        viewHolder.getBinding().setVariable(BR.actionHandler, mActionHandler);
        viewHolder.getBinding().executePendingBindings();
    }
}
