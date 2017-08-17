package com.achievers.ui.addachievement.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.achievers.R;
import com.achievers.entities.Involvement;

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

        return new ViewHolder(contactView);
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

            Resources resources = getContext().getResources();

            // return previous clicked item to default state
            if (getSelectedInvolvement() != null) {
                previousInvolvementTextView.setBackgroundColor(0);

                int textColor = ResourcesCompat.getColor(resources, R.color.colorAccent, null);
                previousInvolvementTextView.setTextColor(textColor);
            }

            if (getSelectedInvolvement() == null || !getSelectedInvolvement().toString().equals(currentInvolvement.toString())) {

                int backgroundColor = ResourcesCompat.getColor(resources, R.color.colorAccent, null);
                tvInvolvement.setBackgroundColor(backgroundColor);

                int textColor = ResourcesCompat.getColor(resources, R.color.colorLight, null);
                tvInvolvement.setTextColor(textColor);

                selectInvolvement(currentInvolvement);
                selectInvolvementTextView(tvInvolvement);
            } else {
                selectInvolvement(null);
                selectInvolvementTextView(null);
            }
        }
    }
}