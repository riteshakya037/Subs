package com.riteshakya.domain.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.riteshakya.domain.DatabaseNames;
import com.riteshakya.domain.models.base.BaseModel;

import org.parceler.Parcel;

/**
 * @author Ritesh Shakya
 */
@SuppressWarnings({ "WeakerAccess", "unused" }) @Parcel public class Subscription implements BaseModel {

    @SerializedName("name") protected String subscriptionName;
    @SerializedName("icon") protected String subscriptionIcon;
    @SerializedName("color") protected String layoutColor;
    @SerializedName(DatabaseNames.POPULAR_FLAG) protected boolean isPopular;
    @Expose protected String id;

    public Subscription() {
    }

    public Subscription(String subscriptionIcon, String layoutColor) {
        this.subscriptionIcon = subscriptionIcon;
        this.layoutColor = "#" + layoutColor;
    }

    public String getSubscriptionName() {
        return subscriptionName;
    }

    public String getSubscriptionIcon() {
        return subscriptionIcon == null ? DatabaseNames.PATH_DEFAULT_IMAGE : subscriptionIcon;
    }

    public String getLayoutColor() {
        //safety measure
        return layoutColor.replace("##", "#");
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
        return "Subscription{"
                + "subscriptionName='"
                + subscriptionName
                + '\''
                + ", subscriptionIcon='"
                + subscriptionIcon
                + '\''
                + ", layoutColor='"
                + layoutColor
                + '\''
                + '}';
    }
}
