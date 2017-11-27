package com.achievers.ui.quests.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.achievers.BR;
import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Quest;
import com.achievers.databinding.QuestsRecyclerItemBinding;
import com.achievers.ui._base._contracts.action_handlers.BaseAdapterActionHandler;
import com.achievers.ui._base.adapters.ActionHandlerAdapter;

import java.util.List;

public class QuestsAdapter
        extends ActionHandlerAdapter<Quest> {

    public QuestsAdapter(
            Context context,
            BaseAdapterActionHandler<Quest> questActionHandler) {

        super(context, questActionHandler);
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
        binding.executePendingBindings();

        List<Achievement> achievements = quest.getAchievements();
        Uri[] rewardsUris = new Uri[achievements.size()];

        for (int i = 0; i < achievements.size(); i++) {
            rewardsUris[i] = achievements.get(i).getPictureUri();
        }

        binding.mdvAchievements.setUris(rewardsUris);
    }
}