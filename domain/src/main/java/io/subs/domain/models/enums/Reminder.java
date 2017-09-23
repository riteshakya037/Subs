package io.subs.domain.models.enums;

import com.google.gson.annotations.SerializedName;

/**
 * @author Ritesh Shakya
 */

public enum Reminder {
    @SerializedName("never")NEVER("Never");
    private String friendlyName;

    Reminder(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    @Override public String toString() {
        return friendlyName;
    }
}