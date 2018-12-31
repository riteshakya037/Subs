package com.riteshakya.domain.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.riteshakya.domain.DatabaseNames;
import com.riteshakya.domain.models.base.BaseModel;
import com.riteshakya.domain.models.enums.Currency;
import com.riteshakya.domain.models.enums.Cycle;
import com.riteshakya.domain.models.enums.Duration;
import com.riteshakya.domain.models.enums.Reminder;

import org.joda.time.DateTime;
import org.parceler.Parcel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.riteshakya.domain.models.constants.Constants.DATE_FORMAT;

/**
 * @author Ritesh Shakya
 */
@Parcel @SuppressWarnings("unused") public class UserSubscription implements BaseModel {
    @Expose protected String id;
    @SerializedName("name") protected String subscriptionName = "";
    @SerializedName("amount") protected float subscriptionAmount = 0;
    @SerializedName("icon") protected String subscriptionIcon;
    @SerializedName("description") protected String subscriptionDescription = "";
    @SerializedName("cycle") protected Cycle subscriptionCycle = Cycle.MONTHLY;
    @SerializedName("firstBill") protected Date firstBill = new Date();
    @SerializedName("duration") protected Duration subscriptionDuration = Duration.INDEFINITE;
    @SerializedName("reminder") protected Reminder subscriptionReminder = Reminder.NEVER;
    @SerializedName("subscriptionCurrency") protected Currency subscriptionCurrency = Currency.USD;
    @SerializedName("color") protected String layoutColor;
    @SerializedName(DatabaseNames.DELETED_FLAG)
    protected final Boolean isDeleted = false;

    public UserSubscription() {
        // required by parcel
    }

    public UserSubscription(String id, String name, String amount, String icon, String description,
            String cycle, Date firstBill, String duration, String reminder,
            String subscriptionCurrency, String color) {
        this.id = id;
        this.subscriptionName = name;
        this.subscriptionAmount = Float.parseFloat(amount.replace(",", ""));
        this.subscriptionIcon = icon;
        this.subscriptionDescription = description;
        this.subscriptionCycle = Cycle.valueOf(cycle);
        this.firstBill = firstBill;
        this.subscriptionDuration = Duration.valueOf(duration);
        this.subscriptionReminder = Reminder.valueOf(reminder);
        this.subscriptionCurrency = Currency.valueOf(subscriptionCurrency);
        this.layoutColor = color;
    }

    public UserSubscription(Subscription subscription) {
        this.subscriptionName = subscription.getSubscriptionName();
        this.subscriptionIcon = subscription.getSubscriptionIcon();
        this.layoutColor = subscription.getLayoutColor();
    }

    public String getSubscriptionName() {
        return subscriptionName;
    }

    public void setSubscriptionName(String subscriptionName) {
        this.subscriptionName = subscriptionName;
    }

    public float getSubscriptionAmount() {
        return subscriptionAmount;
    }

    public void setSubscriptionAmount(float subscriptionAmount) {
        this.subscriptionAmount = subscriptionAmount;
    }

    public String getSubscriptionIcon() {
        return subscriptionIcon;
    }

    public void setSubscriptionIcon(String subscriptionIcon) {
        this.subscriptionIcon = subscriptionIcon;
    }

    public String getLayoutColor() {
        return layoutColor;
    }

    public void setLayoutColor(String layoutColor) {
        this.layoutColor = layoutColor;
    }

    public String getSubscriptionDescription() {
        return subscriptionDescription;
    }

    public void setSubscriptionDescription(String subscriptionDescription) {
        this.subscriptionDescription = subscriptionDescription;
    }

    public Cycle getSubscriptionCycle() {
        return subscriptionCycle;
    }

    public void setSubscriptionCycle(Cycle subscriptionCycle) {
        this.subscriptionCycle = subscriptionCycle;
    }

    public Date getFirstBill() {
        return firstBill;
    }

    public void setFirstBill(Date firstBill) {
        this.firstBill = firstBill;
    }

    public DateTime getJodaFirstBill() {
        return new DateTime(firstBill);
    }

    public Duration getSubscriptionDuration() {
        return subscriptionDuration;
    }

    public void setSubscriptionDuration(Duration subscriptionDuration) {
        this.subscriptionDuration = subscriptionDuration;
    }

    public Reminder getSubscriptionReminder() {
        return subscriptionReminder;
    }

    public void setSubscriptionReminder(Reminder subscriptionReminder) {
        this.subscriptionReminder = subscriptionReminder;
    }

    public Currency getSubscriptionCurrency() {
        return subscriptionCurrency;
    }

    public void setSubscriptionCurrency(Currency subscriptionCurrency) {
        this.subscriptionCurrency = subscriptionCurrency;
    }

    public String getId() {
        return id;
    }

    @Override public void setId(String id) {
        this.id = id;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserSubscription that = (UserSubscription) o;

        return id.equals(that.getId());
    }

    @SuppressWarnings("StringBufferReplaceableByString") @Override public String toString() {
        final StringBuilder sb = new StringBuilder("UserSubscription{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(subscriptionName).append('\'');
        sb.append(", subscriptionAmount='").append(subscriptionAmount).append('\'');
        sb.append(", subscriptionIcon='").append(subscriptionIcon).append('\'');
        sb.append(", subscriptionDescription='").append(subscriptionDescription).append('\'');
        sb.append(", subscriptionCycle=").append(subscriptionCycle);
        sb.append(", firstBill=").append(firstBill);
        sb.append(", subscriptionDuration=").append(subscriptionDuration);
        sb.append(", subscriptionReminder=").append(subscriptionReminder);
        sb.append(", subscriptionCurrency=").append(subscriptionCurrency);
        sb.append(", layoutColor='").append(layoutColor).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", subscriptionName);
        result.put("amount", subscriptionAmount);
        result.put("icon", subscriptionIcon);
        result.put("description", subscriptionDescription);
        result.put("cycle", subscriptionCycle.name());
        result.put("firstBill",
                new SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(getFirstBill()));
        result.put("duration", subscriptionDuration);
        result.put("reminder", subscriptionReminder);
        result.put("subscriptionCurrency", subscriptionCurrency);
        result.put("color", layoutColor);
        result.put(DatabaseNames.DELETED_FLAG, isDeleted);
        return result;
    }
}
