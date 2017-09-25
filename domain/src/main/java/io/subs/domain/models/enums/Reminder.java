package io.subs.domain.models.enums;

/**
 * @author Ritesh Shakya
 */

public enum Reminder {
    NEVER("Never");
    private String friendlyName;

    Reminder(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    @Override public String toString() {
        return friendlyName;
    }
}
