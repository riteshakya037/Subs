package io.subs.android.views.adapters;

import android.content.Context;
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
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

public class AddSubscriptionAdaptor
        extends RecyclerView.Adapter<AddSubscriptionAdaptor.ViewHolder> {
    private List<Subscription> mData = new ArrayList<>();
    private Context mContext;
    private IImageLoader iImageLoader;
    private OnItemClickListener onItemClickListener;

    @Inject public AddSubscriptionAdaptor(Context mContext, IImageLoader iImageLoader) {
        this.mContext = mContext;
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

    public void setSubscriptionCollection(Subscription subscriptions) {
        this.mData.add(subscriptions);
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void validateCollection(Collection<Subscription> subscriptions) {
        if (subscriptions == null) {
            throw new IllegalArgumentException("The list cannot be null");
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
            iImageLoader.loadFirebaseImage(subscription.getIcon(), ivIcon);
            tvTitle.setText(subscription.getName());
            cvRootView.setCardBackgroundColor(Color.parseColor(subscription.getColor()));
        }
    }
}
