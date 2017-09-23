package io.subs.domain.models;

import com.google.gson.annotations.SerializedName;
import io.subs.domain.models.base.BaseModel;
import io.subs.domain.models.enums.Currency;
import io.subs.domain.models.enums.Cycle;
import io.subs.domain.models.enums.Duration;
import io.subs.domain.models.enums.Reminder;
import java.util.Date;

/**
 * @author Ritesh Shakya
 */

@SuppressWarnings("unused") public class UserSubscription extends BaseModel {

    @SerializedName("name") private String subscriptionName;
    @SerializedName("icon") private String subscriptionIcon;
    @SerializedName("description") private String subscriptionDescription;
    @SerializedName("cycle") private Cycle subscriptionCycle;
    @SerializedName("firstBill") private Date firstBill;
    @SerializedName("duration") private Duration subscriptionDuration;
    @SerializedName("reminder") private Reminder subscriptionReminder;
    @SerializedName("subscriptionCurrency") private Currency subscriptionCurrency;
    @SerializedName("color") private String layoutColor;

    public String getSubscriptionName() {
        return subscriptionName;
    }

    public void setSubscriptionName(String subscriptionName) {
        this.subscriptionName = subscriptionName;
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

    @SuppressWarnings("StringBufferReplaceableByString") @Override public String toString() {
        final StringBuilder sb = new StringBuilder("UserSubscription{");
        sb.append("name='").append(subscriptionName).append('\'');
        sb.append(", icon='").append(subscriptionIcon).append('\'');
        sb.append(", description='").append(subscriptionDescription).append('\'');
        sb.append(", subscriptionCycle=").append(subscriptionCycle);
        sb.append(", firstBill=").append(firstBill);
        sb.append(", subscriptionDuration=").append(subscriptionDuration);
        sb.append(", subscriptionReminder=").append(subscriptionReminder);
        sb.append(", subscriptionCurrency=").append(subscriptionCurrency);
        sb.append(", layoutColor='").append(layoutColor).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
