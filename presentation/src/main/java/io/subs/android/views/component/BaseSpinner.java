package io.subs.android.views.component;

/**
 * @author Ritesh Shakya
 */

public class BaseSpinner {
    public String id;
    public String name;

    public BaseSpinner(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override public String toString() {
        return name;
    }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass") @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        BaseSpinner that = (BaseSpinner) o;

        return id.equals(that.id) || name.equals(that.name);
    }
}
