package io.subs.domain.models.enums;

/**
 * @author Ritesh Shakya
 */

public enum Duration {
    INDEFINITE("Indefinite");
    private String friendlyName;

    Duration(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    @Override public String toString() {
        return friendlyName;
    }
}
