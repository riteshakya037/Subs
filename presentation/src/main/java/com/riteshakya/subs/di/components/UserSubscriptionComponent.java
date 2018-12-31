package com.riteshakya.subs.di.components;

import com.riteshakya.subs.di.PerActivity;
import com.riteshakya.subs.di.modules.ActivityModule;
import com.riteshakya.subs.di.modules.UserSubscriptionModule;
import com.riteshakya.subs.views.screens.create_subscriptions.CreateSubscriptionFragment;
import com.riteshakya.subs.views.screens.main_screen.user_details.UserSubscriptionDetailFragment;
import com.riteshakya.subs.views.screens.main_screen.user_details.additional_details.DetailBreakDownFragment;
import com.riteshakya.subs.views.screens.main_screen.user_details.expense_detail.DetailExpenseFragment;
import com.riteshakya.subs.views.screens.main_screen.user_profile.UserProfileFragment;
import com.riteshakya.subs.views.screens.main_screen.user_subscriptions.UserSubscriptionListFragment;

import dagger.Component;
import com.riteshakya.subs.di.PerActivity;
import com.riteshakya.subs.di.modules.ActivityModule;
import com.riteshakya.subs.di.modules.UserSubscriptionModule;
import com.riteshakya.subs.views.screens.create_subscriptions.CreateSubscriptionFragment;
import com.riteshakya.subs.views.screens.main_screen.MainActivityFragment;
import com.riteshakya.subs.views.screens.main_screen.user_details.UserSubscriptionDetailFragment;
import com.riteshakya.subs.views.screens.main_screen.user_details.additional_details.DetailBreakDownFragment;
import com.riteshakya.subs.views.screens.main_screen.user_details.expense_detail.DetailExpenseFragment;
import com.riteshakya.subs.views.screens.main_screen.user_profile.UserProfileFragment;
import com.riteshakya.subs.views.screens.main_screen.user_subscriptions.UserSubscriptionListFragment;

/**
 * A scope {@link PerActivity} component.
 * Injects user specific Fragments.
 */
@PerActivity @Component(dependencies = ApplicationComponent.class, modules = {
        ActivityModule.class, UserSubscriptionModule.class
}) public interface UserSubscriptionComponent extends ActivityComponent {
    void inject(UserSubscriptionListFragment userSubscriptionListFragment);

    void inject(CreateSubscriptionFragment createSubscriptionFragment);

    void inject(MainActivityFragment mainActivityFragment);

    void inject(UserProfileFragment userProfileFragment);

    void inject(UserSubscriptionDetailFragment userSubscriptionDetailFragment);

    void inject(DetailBreakDownFragment detailBreakDownFragment);

    void inject(DetailExpenseFragment detailExpenseFragment);
}
