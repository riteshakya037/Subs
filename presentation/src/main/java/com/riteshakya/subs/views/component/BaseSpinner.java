package com.riteshakya.subs.views.component;

/**
 * @author Ritesh Shakya
 */

public class BaseSpinner {
    private final String id;
    private final String name;

    public BaseSpinner(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override public String toString() {
        return name;
    }

    public String getId() {
        return id;
    }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass") @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        BaseSpinner that = (BaseSpinner) o;

        return id.equals(that.id) || name.equals(that.name);
    }
}
