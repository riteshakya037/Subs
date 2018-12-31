package com.riteshakya.subs.views.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.riteshakya.subs.views.screens.add_subscription.SubscriptionListFragment;

import com.riteshakya.subs.R;
import com.riteshakya.subs.views.screens.add_subscription.SubscriptionListFragment;
import com.riteshakya.domain.models.enums.SubscriptionType;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

public class AddSubscriptionPagerAdapter extends FragmentStatePagerAdapter {
    private final Context context;

    @Inject public AddSubscriptionPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override public Fragment getItem(int position) {
        if (position == 0) {
            return SubscriptionListFragment.createInstance(SubscriptionType.POPULAR);
        } else {
            return SubscriptionListFragment.createInstance(SubscriptionType.ALL);
        }
    }

    @Override public int getCount() {
        return 2;
    }

    @Override public CharSequence getPageTitle(int position) {
        if (position == 0) return context.getString(R.string.txt_popular);
        return context.getString(R.string.txt_all);
    }
}