package io.subs.domain.models.enums;

/**
 * @author Ritesh Shakya
 */

public enum Currency {
    USD("USD", "$");

    private final String friendlyName;
    private final String symbol;

    @SuppressWarnings("SameParameterValue") Currency(String name, String symbol) {
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
