package io.subs.android.views.screens.create_subscriptions;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import butterknife.BindView;
import com.fernandocejas.arrow.checks.Preconditions;
import io.subs.android.R;
import io.subs.android.di.components.UserSubscriptionComponent;
import io.subs.android.imageloader.IImageLoader;
import io.subs.android.views.base.BaseFragment;
import io.subs.android.views.component.CustomDateView;
import io.subs.android.views.component.CustomSpinnerView;
import io.subs.android.views.component.MaskEditText;
import io.subs.domain.models.UserSubscription;
import javax.inject.Inject;
import org.parceler.Parcels;

/**
 * @author Ritesh Shakya
 */
public class CreateSubscriptionFragment extends BaseFragment
        implements CreateSubscriptionPresenter.CreateSubscriptionView {
    private static final String ARGS_USER_SUBSCRIPTION = "user_subscription";
    @BindView(R.id.fragment_create_subscription_icon) ImageView ivSubscriptionImage;
    @BindView(R.id.fragment_create_subscription_amount) MaskEditText etSubscriptionAmount;
    @BindView(R.id.fragment_create_subscription_name) EditText etSubscriptionName;
    @BindView(R.id.fragment_create_subscription_description) EditText etSubscriptionDescription;
    @BindView(R.id.fragment_create_subscription_cycle) CustomSpinnerView svSubscriptionCycle;
    @BindView(R.id.fragment_create_subscription_first_bill) CustomDateView dvFirstBill;
    @BindView(R.id.fragment_create_subscription_duration) CustomSpinnerView svSubscriptionDuration;
    @BindView(R.id.fragment_create_subscription_reminder) CustomSpinnerView svSubscriptionReminder;
    @BindView(R.id.fragment_create_subscription_currency) CustomSpinnerView svSubscriptionCurrency;
    @BindView(R.id.fragment_create_subscription_delete) Button btnDeleteSubscription;

    @BindView(R.id.fragment_create_subscription_card_view) CardView cvRootView;
    @BindView(R.id.fragment_create_subscription_add) Button btnAddSubscription;

    @Inject CreateSubscriptionPresenter createSubscriptionPresenter;
    @Inject IImageLoader iImageLoader;

    public static Fragment forSubscription(UserSubscription userSubscription) {
        CreateSubscriptionFragment createSubscriptionFragment = new CreateSubscriptionFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGS_USER_SUBSCRIPTION, Parcels.wrap(userSubscription));
        createSubscriptionFragment.setArguments(bundle);
        return createSubscriptionFragment;
    }

    @Override protected int getLayout() {
        return R.layout.fragment_create_subscription;
    }

    @Override protected void initializeViews(Bundle savedInstanceState) {
        this.createSubscriptionPresenter.setView(this);
        this.registerPresenter(createSubscriptionPresenter);
        if (savedInstanceState == null) {
            this.initializeData();
            this.loadTemplate(currentLoadType());
        }
    }

    private void initializeData() {
        svSubscriptionCycle.setData(this.createSubscriptionPresenter.getCycleList());
        svSubscriptionDuration.setData(this.createSubscriptionPresenter.getDurationList());
        svSubscriptionReminder.setData(this.createSubscriptionPresenter.getReminderList());
        svSubscriptionCurrency.setData(this.createSubscriptionPresenter.getCurrencyList());
    }

    private void loadTemplate(UserSubscription userSubscription) {
        this.createSubscriptionPresenter.initializeFields(userSubscription);
    }

    @Override protected void injectDagger() {
        getComponent(UserSubscriptionComponent.class).inject(this);
    }

    private UserSubscription currentLoadType() {
        final Bundle arguments = getArguments();
        Preconditions.checkNotNull(arguments, "Fragment arguments cannot be null");
        return Parcels.unwrap(arguments.getParcelable(ARGS_USER_SUBSCRIPTION));
    }

    @Override public void setName(String subscriptionName) {
        etSubscriptionName.setText(subscriptionName);
    }

    @Override public void setIcon(String subscriptionIcon) {
        iImageLoader.loadFirebaseImage(subscriptionIcon, ivSubscriptionImage);
    }

    @Override public void setDescription(String subscriptionDescription) {
        etSubscriptionDescription.setText(subscriptionDescription);
    }

    @Override public void setCycle(String subscriptionCycle) {
        svSubscriptionCycle.setSelection(subscriptionCycle);
    }

    @Override public void setFirstBill(String firstBill) {
        dvFirstBill.setValue(firstBill);
    }

    @Override public void setDuration(String subscriptionDuration) {
        svSubscriptionDuration.setSelection(subscriptionDuration);
    }

    @Override public void setReminder(String subscriptionReminder) {
        svSubscriptionReminder.setSelection(subscriptionReminder);
    }

    @Override public void setSubscriptionCurrency(String subscriptionCurrency) {
        svSubscriptionCurrency.setSelection(subscriptionCurrency);
    }

    @Override public void setColor(String layoutColor) {
        if (!TextUtils.isEmpty(layoutColor)) {
            cvRootView.setCardBackgroundColor(Color.parseColor(layoutColor));
            btnAddSubscription.setTextColor(Color.parseColor(layoutColor));
        }
    }
}
