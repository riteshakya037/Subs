package io.subs.domain.models.base;

import io.subs.domain.models.Subscription;

/**
 * @author Ritesh Shakya
 */

public class BaseModel {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subscription that = (Subscription) o;

        return id.equals(that.getId());
    }

    @Override public int hashCode() {
        return id.hashCode();
    }
}
