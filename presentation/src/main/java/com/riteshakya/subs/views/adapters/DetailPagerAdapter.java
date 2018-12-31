package com.riteshakya.subs.views.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.riteshakya.subs.views.screens.main_screen.user_details.additional_details.DetailBreakDownFragment;
import com.riteshakya.subs.views.screens.main_screen.user_details.expense_detail.DetailExpenseFragment;

import com.riteshakya.subs.views.screens.main_screen.user_details.additional_details.DetailBreakDownFragment;
import com.riteshakya.subs.views.screens.main_screen.user_details.expense_detail.DetailExpenseFragment;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

public class DetailPagerAdapter extends FragmentStatePagerAdapter {

    @Inject public DetailPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override public Fragment getItem(int position) {
        if (position == 0) {
            return DetailExpenseFragment.createInstance();
        } else {
            return DetailBreakDownFragment.createInstance();
        }
    }

    @Override public int getCount() {
        return 2;
    }

    @Override public CharSequence getPageTitle(int position) {
        if (position == 0) return "Expenses";
        return "Breakdown";
    }
}