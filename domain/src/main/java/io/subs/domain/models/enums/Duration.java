package io.subs.domain.models.enums;

import com.google.gson.annotations.SerializedName;

/**
 * @author Ritesh Shakya
 */

public enum Duration {
    @SerializedName("indefinite")INDEFINITE("Indefinite");
    private String friendlyName;

    Duration(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    @Override public String toString() {
        return friendlyName;
    }
}
