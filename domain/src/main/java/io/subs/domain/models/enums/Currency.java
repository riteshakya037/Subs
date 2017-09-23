package io.subs.domain.models.enums;

import com.google.gson.annotations.SerializedName;

/**
 * @author Ritesh Shakya
 */

public enum Currency {
    @SerializedName("usd")USD("USD", "$");

    private String friendlyName;

    Currency(String name, String symbol) {
        this.friendlyName = name + " (" + symbol + ")";
    }

    @Override public String toString() {
        return friendlyName;
    }
}
