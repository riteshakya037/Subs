package io.subs.android;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
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
import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.StorageReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ritesh Shakya
 */

class SubscriptionAdaptor extends RecyclerView.Adapter<SubscriptionAdaptor.ViewHolder> {
    private List<Subscription> mData = new ArrayList<>();
    private Context mContext;
    private StorageReference storageReference;

    public SubscriptionAdaptor(Context mContext, StorageReference storageReference) {
        this.mContext = mContext;
        this.storageReference = storageReference;
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
        return mData.size();
    }

    public void updateData(Subscription subscription) {
        this.mData.add(subscription);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_subscription_icon) ImageView ivIcon;
        @BindView(R.id.item_subscription_text) TextView tvTitle;
        @BindView(R.id.item_subscription_root) CardView cvRootView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.item_subscription_add) public void addSubscription() {

        }

        public void bind(Subscription subscription) {
            Glide.with(mContext)
                    .using(new FirebaseImageLoader())
                    .load(storageReference.child(subscription.getIcon()))
                    .into(ivIcon);
            tvTitle.setText(subscription.getName());
            cvRootView.setCardBackgroundColor(Color.parseColor(subscription.getColor()));
        }
    }
}
