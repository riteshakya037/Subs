package io.subs.android.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import io.subs.android.views.screens.add_subscription.AddSubscriptionActivity;
import io.subs.android.views.screens.create_subscriptions.CreateSubscriptionActivity;
import io.subs.android.views.screens.user_subscription.UserSubscriptionActivity;
import io.subs.domain.models.Subscription;
import io.subs.domain.models.UserSubscription;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Class used to navigate through the application.
 */
@Singleton public class Navigator {

    @Inject public Navigator() {
        //empty
    }

    /**
     * Goes to the add subscription list screen.
     *
     * @param context A Context needed to open the destiny activity.
     */
    public void navigateToSubscriptionList(Context context) {
        if (context != null) {
            Intent intentToLaunch = AddSubscriptionActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToUserSubscriptionList(Context context) {
        if (context != null) {
            Intent intentToLaunch = UserSubscriptionActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }

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
}
