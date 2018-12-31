package com.riteshakya.subs.views.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.riteshakya.domain.models.UserSubscription;
import com.riteshakya.subs.R;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Ritesh Shakya
 */

public class TodaySubscriptionAdaptor
        extends RecyclerView.Adapter<TodaySubscriptionAdaptor.ViewHolder> {
    private final List<UserSubscription> mData = new ArrayList<>();
    private final Context mContext;

    @Inject
    public TodaySubscriptionAdaptor(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_subscription_today, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return this.mData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setSubscription(List<UserSubscription> subscription) {
        mData.clear();
        mData.addAll(subscription);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_subscription_text)
        TextView tvTitle;
        private UserSubscription subscription;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(UserSubscription subscription) {
            this.subscription = subscription;
            tvTitle.setText(subscription.getSubscriptionName());
        }
    }
}
