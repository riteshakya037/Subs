package io.subs.android.views.component.helper.validation;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.subjects.PublishSubject;
import io.subs.android.views.component.MaskEditText;
import io.subs.android.views.component.helper.validation.validation_types.EmptyValidation;
import java.util.concurrent.TimeUnit;

/**
 * @author Ritesh Shakya
 */

public class ValidationHelper {

    @SuppressWarnings("SameParameterValue")
    private static Observable<Boolean> getTextValidationObservable(@NonNull final EditText editText,
            final Validation validation, final boolean showError) {
        return getTextWatcherObservable(editText).debounce(200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(s -> {
                    ValidationResult result = validation.validate(s);
                    if (showError) editText.setError(result.getReason());
                    return result.isValid();
                });
    }

    private static Observable<String> getTextWatcherObservable(@NonNull final EditText editText) {

        final PublishSubject<String> subject = PublishSubject.create();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override public void afterTextChanged(Editable s) {
                subject.onNext(s.toString());
            }
        });

        return subject;
    }

    public static Observable<Boolean> getTextValidationObservable(MaskEditText maskEditText) {
        return getTextValidationObservable(maskEditText.getEditText(), new EmptyValidation(),
                false);
    }

    public static Observable<Boolean> getTextValidationObservable(EditText editText) {
        return getTextValidationObservable(editText, new EmptyValidation(), false);
    }
}
