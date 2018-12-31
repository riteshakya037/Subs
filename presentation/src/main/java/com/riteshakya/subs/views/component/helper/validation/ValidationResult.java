package com.riteshakya.subs.views.component.helper.validation;

import android.support.annotation.Nullable;

/**
 * @author Ritesh Shakya
 */
public class ValidationResult<T> {

    private final boolean valid;
    private final String reason;
    private final T data;

    private ValidationResult(boolean valid, @Nullable String reason, T t) {
        this.valid = valid;
        this.reason = reason;
        this.data = t;
    }

    public static <T> ValidationResult<T> success(T t) {
        return new ValidationResult<>(true, null, t);
    }

    @SuppressWarnings("SameParameterValue")
    public static <T> ValidationResult<T> failure(@Nullable String reason, T t) {
        return new ValidationResult<>(false, reason, t);
    }

    public boolean isValid() {
        return valid;
    }

    @Nullable public String getReason() {
        return reason;
    }

    @SuppressWarnings("unused") public T getData() {
        return data;
    }
}
