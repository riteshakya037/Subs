package io.subs.domain.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.subs.domain.models.base.BaseModel;

/**
 * @author Ritesh Shakya
 */

public class Subscription extends BaseModel {

    @Expose @SerializedName("name") private String subscriptionName;
    @SerializedName("icon") private String subscriptionIcon;
    @SerializedName("color") private String layoutColor;

    public String getSubscriptionName() {
        return subscriptionName;
    }

    public String getSubscriptionIcon() {
        return subscriptionIcon;
    }

    public String getLayoutColor() {
        return layoutColor;
    }
}
