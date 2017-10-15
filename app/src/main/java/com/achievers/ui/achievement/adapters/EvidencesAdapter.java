package com.achievers.ui.achievement.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.achievers.BR;
import com.achievers.data.entities.Evidence;
import com.achievers.databinding.EvidencesRecyclerItemBinding;
import com.achievers.ui._base.AbstractAdapter;
import com.achievers.ui._base.contracts.BaseActionHandler;

public class EvidencesAdapter extends AbstractAdapter<Evidence> {

    public EvidencesAdapter(Context context, BaseActionHandler<Evidence> actionHandler) {
        super(context, actionHandler);
    }

    @Override
    public EvidencesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        EvidencesRecyclerItemBinding binding = EvidencesRecyclerItemBinding.inflate(layoutInflater, parent, false);

        return new ViewHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Evidence evidence = mEntities.get(position);

        viewHolder.getBinding().setVariable(BR.evidence, evidence);
        viewHolder.getBinding().setVariable(BR.resources, mContext.getResources());
        viewHolder.getBinding().setVariable(BR.actionHandler, mActionHandler);
        viewHolder.getBinding().executePendingBindings();
    }
}