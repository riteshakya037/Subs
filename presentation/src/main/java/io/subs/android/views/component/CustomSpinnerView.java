package io.subs.android.views.component;

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
import io.subs.android.R;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ritesh Shakya
 */
@SuppressWarnings("WeakerAccess") public class CustomSpinnerView extends LinearLayout {

    @Nullable @BindView(R.id.custom_spinner_view_spinner_main) Spinner mSpinner;
    private Context mContext;
    private List<BaseSpinner> mData = new ArrayList<>();
    private PublishSubject<String> _observer = PublishSubject.create();

    public CustomSpinnerView(Context context) {
        super(context);
        init(context, new CharSequence[] {});
    }

    public CustomSpinnerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomView);
        init(context, typedArray.getTextArray(R.styleable.CustomView_android_entries));
        typedArray.recycle();
    }

    @OnItemSelected(R.id.custom_spinner_view_spinner_main)
    public void spinnerItemSelected(int position) {
        _observer.onNext(mData.get(position).id);
    }

    @Optional @OnClick(R.id.custom_spinner_view_root_view) void onClickDrop() {
        if (mSpinner != null) {
            mSpinner.performClick();
        }
    }

    private void init(Context context, CharSequence[] textArray) {
        mContext = context;
        View rootView = inflate(context, R.layout.custom_spinner_view, this);
        ButterKnife.bind(this, rootView);

        ArrayAdapter<BaseSpinner> adapter;
        if (textArray != null && textArray.length > 0) {
            for (CharSequence sequence : textArray) {
                mData.add(new BaseSpinner(sequence.toString(),
                        sequence.toString().equals("0") ? "None" : sequence.toString()));
            }
            adapter = new ArrayAdapter<>(mContext, R.layout.spinner_row_selected, mData);
            adapter.setDropDownViewResource(R.layout.spinner_row_dropdown);
            if (mSpinner != null) {
                mSpinner.setAdapter(adapter);
            }
        }
    }

    @SuppressWarnings("ConstantConditions") public void setData(List<BaseSpinner> data) {
        mData = data;
        ArrayAdapter<BaseSpinner> adapter =
                new ArrayAdapter<>(mContext, R.layout.spinner_row_selected, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
    }

    @SuppressWarnings("ConstantConditions") public void setSelection(String id) {
        if (!TextUtils.isEmpty(id) && mData.contains(new BaseSpinner(id, ""))) {
            int pos = mData.indexOf(new BaseSpinner(id, ""));
            mSpinner.setSelection(pos);
        }
    }

    @SuppressWarnings("ConstantConditions") public String getValue() {
        return mSpinner.getSelectedItem() == null ? new BaseSpinner("", "").id
                : ((BaseSpinner) (mSpinner.getSelectedItem())).id;
    }

    public Observable<String> getChangeObservable() {
        return _observer;
    }
}
