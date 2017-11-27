package com.achievers.ui.rewards.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.achievers.BR;
import com.achievers.data.entities.Reward;
import com.achievers.databinding.RewardsRecyclerItemBinding;
import com.achievers.ui._base._contracts.action_handlers.BaseAdapterActionHandler;
import com.achievers.ui._base.adapters.ActionHandlerAdapter;

public class RewardsAdapter
        extends ActionHandlerAdapter<Reward> {

    public RewardsAdapter(
            Context context,
            BaseAdapterActionHandler<Reward> rewardActionHandler) {

        super(context, rewardActionHandler);
    }

    @Override
    public RewardsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        RewardsRecyclerItemBinding binding = RewardsRecyclerItemBinding.inflate(layoutInflater, parent, false);

        return new ViewHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Reward reward = mEntities.get(position);
        RewardsRecyclerItemBinding binding = (RewardsRecyclerItemBinding) viewHolder.getBinding();

        binding.setVariable(BR.reward, reward);
        binding.setVariable(BR.actionHandler, mActionHandler);
        binding.executePendingBindings();
    }
}