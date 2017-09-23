package io.subs.domain.models.enums;

import com.google.gson.annotations.SerializedName;

/**
 * @author Ritesh Shakya
 */

public enum Cycle {
    @SerializedName("week")WEEKLY("Weekly"), @SerializedName("month")MONTHLY(
            "Monthly"), @SerializedName("year")YEARLY("Yearly");

    private String friendlyName;

    Cycle(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    @Override public String toString() {
        return friendlyName;
    }
}
