package com.riteshakya.subs.views.screens.main_screen.user_profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.riteshakya.subs.R;
import com.riteshakya.subs.di.components.UserSubscriptionComponent;
import com.riteshakya.subs.views.base.BaseFragment;
import java.util.Locale;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

@SuppressWarnings("WeakerAccess") public class UserProfileFragment extends BaseFragment
        implements UserProfileFragmentPresenter.UserProfileView {
    @BindView(R.id.fragment_user_full_name) TextView tvFullName;
    @BindView(R.id.fragment_user_email) TextView tvUserEmail;
    @BindView(R.id.fragment_user_progress_ratio) TextView tvUserProgress;
    @BindView(R.id.fragment_user_subscription_progress) RoundCornerProgressBar
            pbSubscriptionProgress;
    @Inject UserProfileFragmentPresenter userProfileFragmentPresenter;

    public static Fragment createInstance() {
        return new UserProfileFragment();
    }

    @OnClick(R.id.fragment_user_setting) public void openSettings() {
        userProfileFragmentPresenter.openSettings();
    }

    @Override protected int getLayout() {
        return R.layout.fragment_user_profile;
    }

    @Override protected void initializeViews(Bundle savedInstanceState) {
        if (userProfileFragmentPresenter != null) {
            userProfileFragmentPresenter.setView(this);
            userProfileFragmentPresenter.initialize();
        }
    }

    @Override protected void injectDagger() {
        getComponent(UserSubscriptionComponent.class).inject(this);
    }

    @Override public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.registerPresenter(userProfileFragmentPresenter);
    }

    @Override public void setName(String userFullName) {
        tvFullName.setText(userFullName);
    }

    @Override public void setEmail(String userEmail) {
        tvUserEmail.setText(userEmail);
    }

    @Override public void setMaxSubs(int currentSubs, int subAvailable) {
        pbSubscriptionProgress.setMax(subAvailable);
        pbSubscriptionProgress.setProgress(currentSubs);
        tvUserProgress.setText(
                String.format(Locale.getDefault(), "%1$d / %2$d", currentSubs, subAvailable));
    }
}