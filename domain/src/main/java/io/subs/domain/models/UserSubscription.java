package io.subs.domain.models;

import io.subs.domain.models.enums.Currency;
import io.subs.domain.models.enums.Cycle;
import io.subs.domain.models.enums.Duration;
import io.subs.domain.models.enums.Reminder;
import java.util.Date;

/**
 * @author Ritesh Shakya
 */

public class UserSubscription {

    public String name;
    public String icon;
    public String description;
    public Cycle subCycle;
    public Date firstBill;
    public Duration subDuration;
    public Reminder subReminder;
    public Currency currency;
    public String color;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Cycle getSubCycle() {
        return subCycle;
    }

    public void setSubCycle(Cycle subCycle) {
        this.subCycle = subCycle;
    }

    public Date getFirstBill() {
        return firstBill;
    }

    public void setFirstBill(Date firstBill) {
        this.firstBill = firstBill;
    }

    public Duration getSubDuration() {
        return subDuration;
    }

    public void setSubDuration(Duration subDuration) {
        this.subDuration = subDuration;
    }

    public Reminder getSubReminder() {
        return subReminder;
    }

    public void setSubReminder(Reminder subReminder) {
        this.subReminder = subReminder;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserSubscription that = (UserSubscription) o;

        return id.equals(that.id);
    }

    @Override public int hashCode() {
        return id.hashCode();
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("UserSubscription{");
        sb.append("name='").append(name).append('\'');
        sb.append(", icon='").append(icon).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", subCycle=").append(subCycle);
        sb.append(", firstBill=").append(firstBill);
        sb.append(", subDuration=").append(subDuration);
        sb.append(", subReminder=").append(subReminder);
        sb.append(", currency=").append(currency);
        sb.append(", color='").append(color).append('\'');
        sb.append(", id='").append(id).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
