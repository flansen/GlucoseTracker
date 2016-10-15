package de.fha.bwi50101.create_edit;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Florian on 15.10.2016.
 */

public class CreateEditPagerAdapter extends FragmentPagerAdapter {
    private Fragment[] fragments;

    public CreateEditPagerAdapter(FragmentManager fm, Fragment[] fragments) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Glucose";
            case 1:
                return "Insulin";
            case 2:
                return "Food";
            case 3:
                return "Note";
            default:
                return "Page " + (position - 1);
        }
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
