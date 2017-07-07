package com.achievers.AddAchievement.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.achievers.R;
import com.achievers.data.Involvement;

import java.util.List;

public class InvolvementRecyclerViewAdapter extends RecyclerView.Adapter<InvolvementRecyclerViewAdapter.ViewHolder> {

    private List<Involvement> mInvolvement;
    private Context mContext;
    private Involvement mSelectedInvolvement;
    private TextView mSelectedInvolvementTextView;

    public InvolvementRecyclerViewAdapter(Context context, List<Involvement> involvement) {
        mInvolvement = involvement;
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public InvolvementRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.recycler_item_involvement, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(InvolvementRecyclerViewAdapter.ViewHolder viewHolder, int position) {
        Involvement involvement = this.mInvolvement.get(position);
        viewHolder.tvInvolvement.setText(involvement.toString());
    }

    @Override
    public int getItemCount() {
        return this.mInvolvement.size();
    }

    public Involvement getSelectedInvolvement() {
        return this.mSelectedInvolvement;
    }

    private void selectInvolvement(Involvement involvement) {
        this.mSelectedInvolvement = involvement;
    }

    private TextView getSelectedInvolvementTextView() {
        return this.mSelectedInvolvementTextView;
    }

    private void selectInvolvementTextView(TextView involvementTextView) {
        this.mSelectedInvolvementTextView = involvementTextView;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvInvolvement;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tvInvolvement = (TextView) itemView.findViewById(R.id.tvInvolvement);
            itemView.setOnClickListener(this);
        }

        // Handles the row being being clicked
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition(); // gets item position
            Involvement currentInvolvement = mInvolvement.get(position);
            TextView previousInvolvementTextView = getSelectedInvolvementTextView();

            // return previous clicked item to default state
            if (getSelectedInvolvement() != null) {
                previousInvolvementTextView.setBackgroundColor(0);
                previousInvolvementTextView.setTextColor(getContext().getResources().getColor(R.color.colorAccent));
            }

            if (getSelectedInvolvement() == null || !getSelectedInvolvement().toString().equals(currentInvolvement.toString())) {
                tvInvolvement.setBackgroundColor(getContext().getResources().getColor(R.color.colorAccent));
                tvInvolvement.setTextColor(getContext().getResources().getColor(R.color.colorLight));

                selectInvolvement(currentInvolvement);
                selectInvolvementTextView(tvInvolvement);
            } else {
                selectInvolvement(null);
                selectInvolvementTextView(null);
            }
        }
    }
}