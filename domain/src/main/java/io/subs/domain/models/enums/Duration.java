package io.subs.domain.models.enums;

/**
 * @author Ritesh Shakya
 */

public enum Duration {
    INDEFINITE("Indefinite");
    private final String friendlyName;

    @SuppressWarnings("SameParameterValue") Duration(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    @Override public String toString() {
        return friendlyName;
    }
}
