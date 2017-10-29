package com.achievers.ui._base.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.achievers.R;
import com.achievers.ui._base.contracts.BaseSelectableAdapter;

import java.util.List;

import static com.achievers.utils.Preconditions.checkNotNull;

public class SelectableAdapter<T>
        extends RecyclerView.Adapter<SelectableAdapter.ViewHolder>
        implements BaseSelectableAdapter<T> {

    private static final int NO_POSITION = -1;

    private final List<T> mValues;
    private final SparseArray<SelectableAdapter.ViewHolder> mViewHolders;
    private int mPreviousPosition;

    private final int mDefaultBackgroundColor;
    private final int mDefaultTextColor;

    private final int mSelectedBackgroundColor;
    private final int mSelectedTextColor;

    public SelectableAdapter(Context context, List<T> values) {
        checkNotNull(context);

        mValues = values;

        mViewHolders = new SparseArray<>(values.size());
        mPreviousPosition = NO_POSITION;

        Resources resources = context.getResources();

        mDefaultBackgroundColor = 0;
        mDefaultTextColor = ResourcesCompat.getColor(resources, R.color.accent, null);

        mSelectedBackgroundColor = ResourcesCompat.getColor(resources, R.color.accent, null);
        mSelectedTextColor = ResourcesCompat.getColor(resources, R.color.light, null);
    }

    @Override
    public T getSelection() {
        if (mPreviousPosition == NO_POSITION) return null;

        return mValues.get(mPreviousPosition);
    }

    @Override
    public int getSelectedPosition() {
        return mPreviousPosition;
    }

    @Override
    public void setSelectedPosition(int position) {
        if (position < 0 || position > mValues.size() - 1) return;

        mPreviousPosition = position;
    }

    @Override
    public SelectableAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.recycler_item_simple_value, parent, false);

        return new SelectableAdapter.ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(SelectableAdapter.ViewHolder viewHolder, int position) {
        T value = mValues.get(position);
        viewHolder.mTvValue.setText(value.toString());

        mViewHolders.put(position, viewHolder);

        if (mPreviousPosition != NO_POSITION && mPreviousPosition == position) {
            select(position);
        }
    }

    private void select(int position) {
        SelectableAdapter.ViewHolder viewHolder = mViewHolders.get(position);

        viewHolder.mTvValue.setBackgroundColor(mSelectedBackgroundColor);
        viewHolder.mTvValue.setTextColor(mSelectedTextColor);
    }

    private void deselect(int position) {
        if (position == NO_POSITION || position < 0 || position > getItemCount()) return;

        SelectableAdapter.ViewHolder viewHolder = mViewHolders.get(position);

        viewHolder.mTvValue.setBackgroundColor(mDefaultBackgroundColor);
        viewHolder.mTvValue.setTextColor(mDefaultTextColor);
    }

    @Override
    public int getItemCount() {
        if (mValues == null) return 0;
        return mValues.size();
    }

    public class ViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        TextView mTvValue;

        ViewHolder(View itemView) {
            super(itemView);

            mTvValue = itemView.findViewById(R.id.tvValue);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int currentPosition = getAdapterPosition();

            if (mPreviousPosition == NO_POSITION ||
                mValues.get(mPreviousPosition) != mValues.get(currentPosition)) {

                deselect(mPreviousPosition);
                select(currentPosition);
                mPreviousPosition = currentPosition;

                return;
            }

            deselect(mPreviousPosition);
            mPreviousPosition = NO_POSITION;
        }
    }
}