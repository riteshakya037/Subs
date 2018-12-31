package com.riteshakya.subs.views.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.Optional;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import com.riteshakya.subs.R;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ritesh Shakya
 */
@SuppressWarnings("WeakerAccess") public class CustomSpinnerView extends LinearLayout {

    @Nullable @BindView(R.id.custom_spinner_view_spinner_main) Spinner mSpinner;
    private Context mContext;
    private int styleType;
    private List<BaseSpinner> mData = new ArrayList<>();
    private final PublishSubject<String> _observer = PublishSubject.create();

    public CustomSpinnerView(Context context) {
        super(context);
        init(context, new CharSequence[] {}, 0);
    }

    public CustomSpinnerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomSpinnerView);
        init(context, typedArray.getTextArray(R.styleable.CustomSpinnerView_android_entries),
                typedArray.getInt(R.styleable.CustomSpinnerView_styleType, 0));
        typedArray.recycle();
    }

    @OnItemSelected(R.id.custom_spinner_view_spinner_main)
    public void spinnerItemSelected(int position) {
        _observer.onNext(mData.get(position).getId());
    }

    @Optional @OnClick(R.id.custom_spinner_view_root_view) void onClickDrop() {
        if (mSpinner != null) {
            mSpinner.performClick();
        }
    }

    private void init(Context context, CharSequence[] textArray, int styleType) {
        mContext = context;
        this.styleType = styleType;
        View rootView = inflate(context, R.layout.custom_spinner_view, this);
        ButterKnife.bind(this, rootView);
        if (textArray != null && textArray.length > 0) {
            for (CharSequence sequence : textArray) {
                mData.add(new BaseSpinner(sequence.toString(),
                        sequence.toString().equals("0") ? "None" : sequence.toString()));
            }
            setData(mData);
        }
    }

    @SuppressWarnings("ConstantConditions") public void setData(List<BaseSpinner> data) {
        mData = data;
        ArrayAdapter<BaseSpinner> adapter = new ArrayAdapter<>(mContext,
                styleType == 0 ? R.layout.spinner_row_selected : R.layout.spinner_row_selected_alt,
                data);
        adapter.setDropDownViewResource(
                styleType == 0 ? R.layout.spinner_row_dropdown : R.layout.spinner_row_dropdown_alt);
        mSpinner.setAdapter(adapter);
    }

    @SuppressWarnings("ConstantConditions") public void setSelection(String id) {
        if (!TextUtils.isEmpty(id) && mData.contains(new BaseSpinner(id, ""))) {
            int pos = mData.indexOf(new BaseSpinner(id, ""));
            mSpinner.setSelection(pos);
        }
    }

    @SuppressWarnings("ConstantConditions") public String getValue() {
        return mSpinner.getSelectedItem() == null ? new BaseSpinner("", "").getId()
                : ((BaseSpinner) (mSpinner.getSelectedItem())).getId();
    }

    public Observable<String> getChangeObservable() {
        return _observer;
    }
}
