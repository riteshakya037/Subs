package io.subs.domain.models.enums;

/**
 * @author Ritesh Shakya
 */

public enum Cycle {
    WEEKLY("Weekly"), MONTHLY("Monthly"), YEARLY("Yearly");

    private final String friendlyName;

    Cycle(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    @Override public String toString() {
        return friendlyName;
    }
}
