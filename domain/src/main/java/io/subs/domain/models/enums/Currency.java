package io.subs.domain.models.enums;

import com.google.gson.annotations.SerializedName;

/**
 * @author Ritesh Shakya
 */

public enum Currency {
    @SerializedName("usd")USD("USD", "$");

    private String friendlyName;
    private String symbol;

    Currency(String name, String symbol) {
        this.symbol = symbol;
        this.friendlyName = name + " (" + symbol + ")";
    }

    @Override public String toString() {
        return friendlyName;
    }

    public String getSymbol() {
        return symbol;
    }
}
