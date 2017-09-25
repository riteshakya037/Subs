package io.subs.domain.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.subs.domain.DatabaseNames;
import io.subs.domain.models.base.BaseModel;
import org.parceler.Parcel;

/**
 * @author Ritesh Shakya
 */
@Parcel public class Subscription implements BaseModel {

    @SerializedName("name") protected String subscriptionName;
    @SerializedName("icon") protected String subscriptionIcon;
    @SerializedName("color") protected String layoutColor;
    @SerializedName(DatabaseNames.POPULAR_FLAG) protected boolean isPopular;
    @Expose protected String id;

    public String getSubscriptionName() {
        return subscriptionName;
    }

    public String getSubscriptionIcon() {
        return subscriptionIcon;
    }

    public String getLayoutColor() {
        return layoutColor;
    }

    public String getId() {
        return id;
    }

    @Override public void setId(String id) {
        this.id = id;
    }

    public boolean isPopular() {
        return isPopular;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subscription that = (Subscription) o;

        return id.equals(that.getId());
    }

    @Override public int hashCode() {
        return id.hashCode();
    }

    @Override public String toString() {
        final StringBuffer sb = new StringBuffer("Subscription{");
        sb.append("subscriptionName='").append(subscriptionName).append('\'');
        sb.append(", subscriptionIcon='").append(subscriptionIcon).append('\'');
        sb.append(", layoutColor='").append(layoutColor).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
