package com.achievers.ui.quests.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.achievers.BR;
import com.achievers.data.entities.Quest;
import com.achievers.data.entities.Reward;
import com.achievers.databinding.QuestRewardsRecyclerItemBinding;
import com.achievers.ui._base._contracts.action_handlers.BaseAdapterActionHandler;
import com.achievers.ui._base.adapters.ActionHandlerAdapter;

import java.util.List;

public class QuestRewardsAdapter extends ActionHandlerAdapter<Reward, Quest> {

    private Quest mQuest;

    QuestRewardsAdapter(
            Context context,
            BaseAdapterActionHandler<Quest> actionHandler,
            List<Reward> rewards,
            Quest quest) {

        super(context, actionHandler);

        add(rewards);
        mQuest = quest;
    }

    @Override
    public QuestRewardsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        QuestRewardsRecyclerItemBinding binding = QuestRewardsRecyclerItemBinding.inflate(layoutInflater, parent, false);

        return new ViewHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Reward reward = mEntities.get(position);

        viewHolder.getBinding().setVariable(BR.reward, reward);
        viewHolder.getBinding().setVariable(BR.quest, mQuest);
        viewHolder.getBinding().setVariable(BR.resources, mContext.getResources());
        viewHolder.getBinding().setVariable(BR.actionHandler, mActionHandler);
        viewHolder.getBinding().executePendingBindings();
    }
}