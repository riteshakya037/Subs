package com.riteshakya.subs.views.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.riteshakya.subs.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Ritesh Shakya
 */

@SuppressWarnings("WeakerAccess")
public class ExpenseView extends LinearLayout {

    @BindView(R.id.custom_expense_view_title)
    TextView tvExpenseTitle;
    @BindView(R.id.custom_expense_view_value)
    TextView tvExpenseValue;
    @BindView(R.id.root_view)
    View rootView;

    public ExpenseView(Context context) {
        super(context);
    }

    public ExpenseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ExpenseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ExpenseView);
        init(context, typedArray.getString(R.styleable.ExpenseView_android_text), typedArray.getResourceId(R.styleable.ExpenseView_android_background, -1));
        typedArray.recycle();
    }

    private void init(Context context, String title, int background) {
        View rootView = getView(context);
        ButterKnife.bind(this, rootView);
        this.rootView.setBackgroundResource(background);
        tvExpenseTitle.setText(title);
        setExpenseValue(0);
    }

    public void setExpenseValue(float value) {
        this.tvExpenseValue.setText(getContext().getString(R.string.number_format_text, value));
    }

    private View getView(Context context) {
        return inflate(context, R.layout.custom_expense_view, this);
    }
}
