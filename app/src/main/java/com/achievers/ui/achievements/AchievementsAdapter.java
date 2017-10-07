package com.achievers.ui.achievements;

import android.content.Context;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.achievers.BR;
import com.achievers.data.entities.Achievement;
import com.achievers.databinding.AchievementsRecyclerItemBinding;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class AchievementsAdapter
        extends RecyclerView.Adapter<AchievementsAdapter.ViewHolder>
        implements AchievementsContracts.Adapter {

    private Context mContext;
    private List<Achievement> mAchievements;
    private AchievementsActionHandler mActionHandler;

    AchievementsAdapter(Context context, AchievementsActionHandler actionHandler) {
        mContext = context;
        mAchievements = new ArrayList<>();
        mActionHandler = actionHandler;
    }

    @Override
    public void add(List<Achievement> achievements) {
        int start = getItemCount();
        mAchievements.addAll(achievements);
        notifyItemRangeInserted(start, achievements.size());
    }

    @Override
    public Parcelable onSaveInstanceState() {
        return Parcels.wrap(mAchievements);
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {
        List<Achievement> achievements = Parcels.unwrap(parcelable);
        add(achievements);
    }

    @Override
    public AchievementsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        AchievementsRecyclerItemBinding binding = AchievementsRecyclerItemBinding.inflate(layoutInflater, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Achievement achievement = mAchievements.get(position);

        viewHolder.getBinding().setVariable(BR.achievement, achievement);
        viewHolder.getBinding().setVariable(BR.resources, mContext.getResources());
        viewHolder.getBinding().setVariable(BR.actionHandler, mActionHandler);
        viewHolder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mAchievements.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private AchievementsRecyclerItemBinding mBinding;

        ViewHolder(AchievementsRecyclerItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public AchievementsRecyclerItemBinding getBinding() {
            return mBinding;
        }
    }
}