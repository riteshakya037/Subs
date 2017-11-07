package io.subs.android.views.component;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import io.subs.android.R;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import static io.subs.domain.models.constants.Constants.DATE_FORMAT;

/**
 * @author Ritesh Shakya
 */
@SuppressWarnings({ "WeakerAccess", "ConstantConditions" }) public class CustomDateView
        extends LinearLayout {
    private final Context mContext;
    @BindView(R.id.custom_date_view_text_main) TextView dateText;

    public CustomDateView(Context context) {
        super(context);
        this.mContext = context;
        init(context);
    }

    public CustomDateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(context);
    }

    @Optional @OnClick(R.id.custom_date_view_text_main) void onClick() {
        final Calendar calendar = new GregorianCalendar();
        DatePickerDialog datePickerDialog =
                new DatePickerDialog(mContext, (datePicker, i, i1, i2) -> {
                    calendar.set(i, i1, i2);
                    SimpleDateFormat format1 =
                            new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
                    dateText.setText(format1.format(calendar.getTime()));
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setTitle("Select Date");
        datePickerDialog.show();
    }

    private void init(Context context) {
        View rootView = inflate(context, R.layout.custom_date_view, this);
        ButterKnife.bind(this, rootView);
    }

    @SuppressWarnings("ConstantConditions") public void setValue(String date_of_birth) {
        if (!TextUtils.isEmpty(date_of_birth)) {
            dateText.setText(date_of_birth);
        }
    }

    public String getText() {
        return dateText.getText().toString();
    }

    public Date getDate() {
        DateFormat formatter = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        Date date = new Date();
        try {
            date = formatter.parse(getText());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
