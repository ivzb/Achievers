package com.achievers.ui._base;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;

import com.achievers.data.entities._base.BaseModel;
import com.achievers.ui._base.contracts.action_handlers.BaseAdapterActionHandler;
import com.achievers.ui._base.contracts.adapters.BaseAdapter;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAdapter<T extends BaseModel>
        extends RecyclerView.Adapter<AbstractAdapter.ViewHolder>
        implements BaseAdapter<T> {

    protected Context mContext;
    protected List<T> mEntities;
    protected BaseAdapterActionHandler<T> mActionHandler;

    public AbstractAdapter(Context context, BaseAdapterActionHandler<T> actionHandler) {
        mContext = context;
        mEntities = new ArrayList<>();
        mActionHandler = actionHandler;
    }

    @Override
    public void add(List<T> entities) {
        if (entities == null) return;

        int start = getItemCount();
        mEntities.addAll(entities);
        notifyItemRangeInserted(start, entities.size());
    }

    @Override
    public Parcelable onSaveInstanceState() {
        return Parcels.wrap(mEntities);
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {
        List<T> entities = Parcels.unwrap(parcelable);
        add(entities);
    }

    @Override
    public int getItemCount() {
        return mEntities.size();
    }

    public class ViewHolder<B extends ViewDataBinding> extends RecyclerView.ViewHolder {

        private B mBinding;

        public ViewHolder(B binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public B getBinding() {
            return mBinding;
        }
    }
}