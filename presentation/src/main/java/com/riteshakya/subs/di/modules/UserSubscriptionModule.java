package com.riteshakya.subs.di.modules;

import com.riteshakya.subs.views.screens.main_screen.MainActivity;
import com.riteshakya.subs.views.screens.main_screen.MainActivityFragmentPresenter;
import com.riteshakya.subs.views.screens.main_screen.user_details.UserSubscriptionDetailPresenter;
import com.riteshakya.subs.views.screens.main_screen.user_details.UserSubscriptionDetailPresenterImpl;
import com.riteshakya.subs.views.screens.main_screen.user_details.additional_details.DetailBreakDownPresenter;
import com.riteshakya.subs.views.screens.main_screen.user_details.additional_details.DetailBreakDownPresenterImpl;
import com.riteshakya.subs.views.screens.main_screen.user_details.expense_detail.DetailExpensePresenter;
import com.riteshakya.subs.views.screens.main_screen.user_details.expense_detail.DetailExpensePresenterImpl;
import com.riteshakya.subs.views.screens.main_screen.user_subscriptions.UserSubscriptionListPresenter;

import dagger.Module;
import dagger.Provides;
import com.riteshakya.subs.di.PerActivity;
import com.riteshakya.subs.di.modules.base.InjectableModule;
import com.riteshakya.subs.views.screens.create_subscriptions.CreateSubscriptionPresenter;
import com.riteshakya.subs.views.screens.create_subscriptions.CreateSubscriptionPresenterImpl;
import com.riteshakya.subs.views.screens.main_screen.MainActivity;
import com.riteshakya.subs.views.screens.main_screen.MainActivityFragmentPresenter;
import com.riteshakya.subs.views.screens.main_screen.MainActivityFragmentPresenterImpl;
import com.riteshakya.subs.views.screens.main_screen.user_details.UserSubscriptionDetailPresenter;
import com.riteshakya.subs.views.screens.main_screen.user_details.UserSubscriptionDetailPresenterImpl;
import com.riteshakya.subs.views.screens.main_screen.user_details.additional_details.DetailBreakDownPresenter;
import com.riteshakya.subs.views.screens.main_screen.user_details.additional_details.DetailBreakDownPresenterImpl;
import com.riteshakya.subs.views.screens.main_screen.user_details.expense_detail.DetailExpensePresenter;
import com.riteshakya.subs.views.screens.main_screen.user_details.expense_detail.DetailExpensePresenterImpl;
import com.riteshakya.subs.views.screens.main_screen.user_profile.UserProfileFragmentPresenter;
import com.riteshakya.subs.views.screens.main_screen.user_profile.UserProfileFragmentPresenterImpl;
import com.riteshakya.subs.views.screens.main_screen.user_subscriptions.UserSubscriptionListPresenter;
import com.riteshakya.subs.views.screens.main_screen.user_subscriptions.UserSubscriptionListPresenterImpl;

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

    @Provides @PerActivity
    UserSubscriptionListPresenter provideUserSubscriptionListPresenter(
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

    @Provides
    UserSubscriptionDetailPresenter provideSubscriptionDetailPresenter(
            UserSubscriptionDetailPresenterImpl userSubscriptionDetailPresenter) {
        return userSubscriptionDetailPresenter;
    }

    @Provides
    DetailBreakDownPresenter providesDetailBreakDownPresenter(
            DetailBreakDownPresenterImpl detailBreakDownPresenter) {
        return detailBreakDownPresenter;
    }

    @Provides
    DetailExpensePresenter providesDetailExpensePresenter(
            DetailExpensePresenterImpl detailExpensePresenter) {
        return detailExpensePresenter;
    }
}
