package com.achievers.ui.quests.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.achievers.BR;
import com.achievers.data.entities.Quest;
import com.achievers.data.entities.Reward;
import com.achievers.databinding.QuestsRecyclerItemBinding;
import com.achievers.ui._base._contracts.action_handlers.BaseAdapterActionHandler;
import com.achievers.ui._base._contracts.adapters.BaseAdapter;
import com.achievers.ui._base.adapters.SimpleActionHandlerAdapter;
import com.achievers.ui.quests.RewardsActionHandler;

public class QuestsAdapter
        extends SimpleActionHandlerAdapter<Quest>
        implements BaseAdapterActionHandler<Quest> {

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

    private void setUpQuestsRecycler(
            Context context,
            QuestsRecyclerItemBinding binding) {

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Quest quest = mEntities.get(position);
        QuestsRecyclerItemBinding binding = (QuestsRecyclerItemBinding) viewHolder.getBinding();

        binding.setVariable(BR.quest, quest);
        binding.setVariable(BR.resources, mContext.getResources());
        binding.setVariable(BR.actionHandler, mActionHandler);
        binding.executePendingBindings();

        BaseAdapter<Reward> adapter = new QuestRewardsAdapter(
                mContext,
                this,
                quest.getRewards(),
                quest);

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                mContext,
                LinearLayoutManager.HORIZONTAL,
                false);

        binding.rvRewards.setAdapter((RecyclerView.Adapter) adapter);
        binding.rvRewards.setLayoutManager(layoutManager);
    }

    @Override
    public void onAdapterEntityClick(Quest quest) {
        mRewardActionHandler.onRewardsClick(quest);
    }
}