package com.reference.maczak.referenceapplication.splash;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Adam on 7/20/2015.
 */
public class SplashFragmentPagerAdapter extends FragmentPagerAdapter {

    private static int NUM_ITEMS = 2;

    public SplashFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TitleFragment();
            case 1:
                return new LoremFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
