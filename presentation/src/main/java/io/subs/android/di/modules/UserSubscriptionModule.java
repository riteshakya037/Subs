package io.subs.android.di.modules;

import dagger.Module;
import dagger.Provides;
import io.subs.android.di.PerActivity;
import io.subs.android.di.modules.base.InjectableModule;
import io.subs.android.views.screens.create_subscriptions.CreateSubscriptionPresenter;
import io.subs.android.views.screens.create_subscriptions.CreateSubscriptionPresenterImpl;
import io.subs.android.views.screens.main_screen.MainActivity;
import io.subs.android.views.screens.main_screen.MainActivityFragmentPresenter;
import io.subs.android.views.screens.main_screen.MainActivityFragmentPresenterImpl;
import io.subs.android.views.screens.main_screen.user_details.UserSubscriptionDetailPresenter;
import io.subs.android.views.screens.main_screen.user_details.UserSubscriptionDetailPresenterImpl;
import io.subs.android.views.screens.main_screen.user_details.additional_details.DetailBreakDownPresenter;
import io.subs.android.views.screens.main_screen.user_details.additional_details.DetailBreakDownPresenterImpl;
import io.subs.android.views.screens.main_screen.user_details.expense_detail.DetailExpensePresenter;
import io.subs.android.views.screens.main_screen.user_details.expense_detail.DetailExpensePresenterImpl;
import io.subs.android.views.screens.main_screen.user_profile.UserProfileFragmentPresenter;
import io.subs.android.views.screens.main_screen.user_profile.UserProfileFragmentPresenterImpl;
import io.subs.android.views.screens.main_screen.user_subscriptions.UserSubscriptionListPresenter;
import io.subs.android.views.screens.main_screen.user_subscriptions.UserSubscriptionListPresenterImpl;

/**
 * Dagger module that provides user related collaborators.
 */
@Module public class UserSubscriptionModule extends InjectableModule<MainActivity> {

    public UserSubscriptionModule() {
    }

    public UserSubscriptionModule(MainActivity mainActivity) {
        super(mainActivity);
    }

    @Provides @PerActivity
    MainActivityFragmentPresenter.MainActivityFlowListener provideMainActivityFlowListener() {
        return getBoundClass();
    }

    @Provides @PerActivity
    UserProfileFragmentPresenter.UserProfileFlowListener provideUserProfileFlowListener() {
        return getBoundClass();
    }

    @Provides @PerActivity UserSubscriptionListPresenter provideUserSubscriptionListPresenter(
            UserSubscriptionListPresenterImpl subscriptionListPresenter) {
        return subscriptionListPresenter;
    }

    @Provides @PerActivity CreateSubscriptionPresenter provideCreateSubscriptionPresenter(
            CreateSubscriptionPresenterImpl createSubscriptionPresenter) {
        return createSubscriptionPresenter;
    }

    @Provides MainActivityFragmentPresenter provideMainActivityPresenter(
            MainActivityFragmentPresenterImpl mainActivityPresenter) {
        return mainActivityPresenter;
    }

    @Provides UserProfileFragmentPresenter provideUserProfileFragmentPresenter(
            UserProfileFragmentPresenterImpl userProfileFragmentPresenter) {
        return userProfileFragmentPresenter;
    }

    @Provides UserSubscriptionDetailPresenter provideSubscriptionDetailPresenter(
            UserSubscriptionDetailPresenterImpl userSubscriptionDetailPresenter) {
        return userSubscriptionDetailPresenter;
    }

    @Provides DetailBreakDownPresenter providesDetailBreakDownPresenter(
            DetailBreakDownPresenterImpl detailBreakDownPresenter) {
        return detailBreakDownPresenter;
    }

    @Provides DetailExpensePresenter providesDetailExpensePresenter(
            DetailExpensePresenterImpl detailExpensePresenter) {
        return detailExpensePresenter;
    }
}
