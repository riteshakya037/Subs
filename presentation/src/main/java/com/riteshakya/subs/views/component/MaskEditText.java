package com.riteshakya.subs.views.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.riteshakya.subs.R;

/**
 * @author Ritesh Shakya
 */

@SuppressWarnings("WeakerAccess") public class MaskEditText extends LinearLayout implements TextWatcher {
    @BindView(R.id.custom_mask_edit_text_mask) TextView tvMaskText;
    @BindView(R.id.custom_mask_edit_text_value) EditText etValue;
    private Context mContext;
    private String hintText;
    private int inputType;
    private String current = "";

    public MaskEditText(Context context) {
        super(context);
        init(context, "", "", 0);
    }

    public MaskEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MaskEditText);
        init(context, typedArray.getString(R.styleable.MaskEditText_maskText),
                typedArray.getString(R.styleable.MaskEditText_hint),
                typedArray.getInt(R.styleable.MaskEditText_android_inputType, 0));
        typedArray.recycle();
    }

    @OnClick(R.id.custom_mask_edit_text_root) void onViewClick() {
        etValue.requestFocus();
    }

    private void init(Context context, String maskText, String hintText, int inputType) {
        this.mContext = context;
        this.hintText = hintText;
        this.inputType = inputType;
        View rootView = inflate(context, R.layout.custom_mask_edit_text, this);
        ButterKnife.bind(this, rootView);

        tvMaskText.setText(maskText);
        etValue.setHint(hintText);
        etValue.setInputType(inputType);
        etValue.addTextChangedListener(this);
    }

    public void setMaskText(String maskText) {
        tvMaskText.setText(maskText);
    }

    public String getText() {
        return etValue.getText().toString();
    }

    public void setText(String text) {
        etValue.setText(text);
    }

    @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override public void onTextChanged(CharSequence text, int i, int i1, int i2) {
        if (!TextUtils.isEmpty(text)) {
            etValue.setHint("");
            formatNumberTypes(text);
        } else {
            etValue.setHint(hintText);
        }
    }

    private void formatNumberTypes(CharSequence text) {
        if (inputType == InputType.TYPE_CLASS_NUMBER) {
            etValue.removeTextChangedListener(this);
            if (!text.toString().equals(current)) {

                String cleanString = text.toString().replaceAll("[,.]", "");
                if (TextUtils.isEmpty(cleanString)) {
                    etValue.setText("");
                    etValue.setHint(hintText);
                    return;
                }
                double parsed = Double.parseDouble(cleanString);
                if (parsed == 0) {
                    etValue.setText("");
                    etValue.setHint(hintText);
                } else {
                    String formatted =
                            mContext.getString(R.string.number_format_text, parsed / 100);

                    current = formatted;
                    etValue.setText(formatted);
                    etValue.setSelection(formatted.length());
                }
            }
            etValue.addTextChangedListener(this);
        }
    }

    @Override public void afterTextChanged(Editable editable) {

    }

    public EditText getEditText() {
        return etValue;
    }
}
