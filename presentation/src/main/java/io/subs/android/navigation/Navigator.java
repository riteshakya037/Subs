package io.subs.android.navigation;

import android.content.Context;
import android.content.Intent;
import io.subs.android.views.screens.add_subscription.AddSubscriptionActivity;
import io.subs.android.views.screens.user_subscription.UserSubscriptionActivity;
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
}
