package io.subs.domain.models.enums;

/**
 * @author Ritesh Shakya
 */

public enum Reminder {
    NEVER("Never");
    private final String friendlyName;

    @SuppressWarnings("SameParameterValue") Reminder(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    @Override public String toString() {
        return friendlyName;
    }
}
