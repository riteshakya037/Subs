package io.subs.android.views.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import io.subs.android.views.screens.main_screen.user_details.UserSubscriptionDetailFragment;
import io.subs.android.views.screens.main_screen.user_profile.UserProfileFragment;
import io.subs.android.views.screens.main_screen.user_subscriptions.UserSubscriptionListFragment;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

public class MainScreenPagerAdapter extends FragmentStatePagerAdapter {

    @Inject public MainScreenPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override public Fragment getItem(int position) {
        if (position == 0) {
            return UserSubscriptionDetailFragment.createInstance();
        } else if (position == 1) {
            return UserSubscriptionListFragment.createInstance();
        }
        return UserProfileFragment.createInstance();
    }

    @Override public int getCount() {
        return 3;
    }
}