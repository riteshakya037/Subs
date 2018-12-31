package com.riteshakya.subs.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.riteshakya.subs.views.screens.add_subscription.AddSubscriptionActivity;
import com.riteshakya.subs.views.screens.create_subscriptions.CreateSubscriptionActivity;
import com.riteshakya.subs.views.screens.main_screen.MainActivity;

import com.riteshakya.subs.views.screens.add_subscription.AddSubscriptionActivity;
import com.riteshakya.subs.views.screens.create_subscriptions.CreateSubscriptionActivity;
import com.riteshakya.subs.views.screens.login.LoginActivity;
import com.riteshakya.subs.views.screens.main_screen.MainActivity;
import com.riteshakya.subs.views.screens.settings.SettingActivity;
import com.riteshakya.domain.models.Subscription;
import com.riteshakya.domain.models.UserSubscription;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Class used to navigate through the application.
 */
@Singleton public class Navigator {

    @Inject public Navigator() {
        //empty
    }

    public void navigateToSubscriptionList(Context context) {
        if (context != null) {
            Intent intentToLaunch = AddSubscriptionActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToMainScreen(Context context) {
        if (context != null) {
            Intent intentToLaunch = MainActivity.getCallingIntent(context);
            intentToLaunch.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intentToLaunch);
        }
    }

    @SuppressWarnings("SameParameterValue")
    public void navigateToUpdateSubscription(Activity context, Subscription subscription,
            int requestCode) {
        if (context != null) {
            Intent intentToLaunch =
                    CreateSubscriptionActivity.getCallingIntent(context, subscription);
            context.startActivityForResult(intentToLaunch, requestCode);
        }
    }

    public void navigateToUpdateSubscription(Activity context, UserSubscription subscription) {
        if (context != null) {
            Intent intentToLaunch =
                    CreateSubscriptionActivity.getCallingIntent(context, subscription);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToLoginScreen(Activity context) {
        if (context != null) {
            Intent intentToLaunch = LoginActivity.getCallingIntent(context);
            intentToLaunch.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToSettingActivity(Activity context) {
        if (context != null) {
            Intent intentToLaunch = SettingActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }
}
