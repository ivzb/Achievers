package com.achievers.ui.quests.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.achievers.BR;
import com.achievers.data.entities.Quest;
import com.achievers.data.entities.Reward;
import com.achievers.databinding.QuestsRecyclerItemBinding;
import com.achievers.ui._base._contracts.action_handlers.BaseAdapterActionHandler;
import com.achievers.ui._base.adapters.ActionHandlerAdapter;
import com.achievers.ui.quests.RewardsActionHandler;

import java.util.List;

public class QuestsAdapter
        extends ActionHandlerAdapter<Quest> {

    private RewardsActionHandler mRewardActionHandler;

    public QuestsAdapter(
            Context context,
            BaseAdapterActionHandler<Quest> questActionHandler,
            RewardsActionHandler rewardActionHandler) {

        super(context, questActionHandler);

        mRewardActionHandler = rewardActionHandler;
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
        QuestsRecyclerItemBinding binding = (QuestsRecyclerItemBinding) viewHolder.getBinding();

        binding.setVariable(BR.quest, quest);
        binding.setVariable(BR.actionHandler, mActionHandler);
        binding.setVariable(BR.rewardsActionHandler, mRewardActionHandler);
        binding.executePendingBindings();

        List<Reward> rewards = quest.getRewards();
        Uri[] rewardsUris = new Uri[rewards.size()];

        for (int i = 0; i < rewards.size(); i++) {
            rewardsUris[i] = rewards.get(i).getPictureUri();
        }

        binding.mdvRewards.setUris(rewardsUris);
    }
}