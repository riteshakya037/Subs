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
import io.subs.domain.models.UserSubscription;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

public class UserSubscriptionAdaptor
        extends RecyclerView.Adapter<UserSubscriptionAdaptor.ViewHolder> {
    private List<UserSubscription> mData = new ArrayList<>();
    private Context mContext;
    private IImageLoader iImageLoader;
    private OnItemClickListener onItemClickListener;

    @Inject public UserSubscriptionAdaptor(Context context, IImageLoader iImageLoader) {
        mContext = context;
        this.iImageLoader = iImageLoader;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_subscription, parent, false);
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

    public void addSubscription(UserSubscription subscription) {
        if (!mData.contains(subscription)) {
            this.mData.add(subscription);
            this.notifyDataSetChanged();
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void updateSubscription(UserSubscription subscription) {
        if (mData.contains(subscription)) {
            this.mData.set(mData.indexOf(subscription), subscription);
            this.notifyDataSetChanged();
        }
    }

    public void removeSubscription(UserSubscription subscription) {
        if (mData.contains(subscription)) {
            this.mData.remove(subscription);
            this.notifyDataSetChanged();
        }
    }

    public void clearData() {
        this.mData.clear();
        this.notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClicked(UserSubscription subscription);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_subscription_icon) ImageView ivIcon;
        @BindView(R.id.item_subscription_text) TextView tvTitle;
        @BindView(R.id.item_subscription_amount) TextView tvAmount;
        @BindView(R.id.item_subscription_root) CardView cvRootView;
        private UserSubscription subscription;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.item_subscription_root) public void addSubscription() {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClicked(subscription);
            }
        }

        public void bind(UserSubscription subscription) {
            this.subscription = subscription;
            iImageLoader.loadFirebaseImage(subscription.getSubscriptionIcon(), ivIcon);
            tvTitle.setText(subscription.getSubscriptionName());
            tvAmount.setText(mContext.getString(R.string.number_format_text_with_currency,
                    subscription.getSubscriptionCurrency().getSymbol(),
                    subscription.getSubscriptionAmount()));
            cvRootView.setCardBackgroundColor(Color.parseColor(subscription.getLayoutColor()));
        }
    }
}
