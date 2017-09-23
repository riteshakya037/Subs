package io.subs.android.views.adapters;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.subs.android.R;
import io.subs.android.imageloader.IImageLoader;
import io.subs.domain.models.Subscription;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

public class AddSubscriptionAdaptor
        extends RecyclerView.Adapter<AddSubscriptionAdaptor.ViewHolder> {
    private List<Subscription> mData = new ArrayList<>();
    private IImageLoader iImageLoader;
    private OnItemClickListener onItemClickListener;

    @Inject public AddSubscriptionAdaptor(IImageLoader iImageLoader) {
        this.iImageLoader = iImageLoader;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_subscription, parent, false);
        return new ViewHolder(v);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override public int getItemCount() {
        return (this.mData != null) ? this.mData.size() : 0;
    }

    @Override public long getItemId(int position) {
        return position;
    }

    public void addSubscription(Subscription subscription) {
        if (!mData.contains(subscription)) {
            this.mData.add(subscription);
            this.notifyDataSetChanged();
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void updateSubscription(Subscription subscription) {
        if (mData.contains(subscription)) {
            this.mData.set(mData.indexOf(subscription), subscription);
            this.notifyDataSetChanged();
        }
    }

    public void removeSubscription(Subscription subscription) {
        if (mData.contains(subscription)) {
            this.mData.remove(subscription);
            this.notifyDataSetChanged();
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(Subscription subscription);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_subscription_icon) ImageView ivIcon;
        @BindView(R.id.item_subscription_text) TextView tvTitle;
        @BindView(R.id.item_subscription_root) CardView cvRootView;
        private Subscription subscription;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.item_subscription_add) public void addSubscription() {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClicked(subscription);
            }
        }

        public void bind(Subscription subscription) {
            this.subscription = subscription;
            iImageLoader.loadFirebaseImage(subscription.getSubscriptionIcon(), ivIcon);
            tvTitle.setText(subscription.getSubscriptionName());
            cvRootView.setCardBackgroundColor(Color.parseColor(subscription.getLayoutColor()));
        }
    }
}
