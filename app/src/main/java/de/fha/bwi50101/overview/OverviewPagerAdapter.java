package de.fha.bwi50101.overview;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Florian on 08.10.2016.
 */

public class OverviewPagerAdapter extends FragmentPagerAdapter {
    private Fragment[] fragments;

    public OverviewPagerAdapter(FragmentManager fm, Fragment[] fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Home";
            case 1:
                return "Statistic";
            default:
                return "Page " + position;
        }
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
