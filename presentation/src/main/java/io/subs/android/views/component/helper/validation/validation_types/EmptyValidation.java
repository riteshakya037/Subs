package io.subs.android.views.component.helper.validation.validation_types;

import android.text.TextUtils;
import io.subs.android.views.component.helper.validation.Validation;
import io.subs.android.views.component.helper.validation.ValidationResult;

/**
 * @author Ritesh Shakya
 */

public class EmptyValidation implements Validation {
    @Override public ValidationResult validate(String s) {
        if (s == null || TextUtils.isEmpty(s.replaceAll("[0.]", ""))) {
            return ValidationResult.failure("Empty String", s);
        }
        return ValidationResult.success(s);
    }
}
