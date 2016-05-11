package com.pruebas.tabs.pruebatabs.Model;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.pruebas.tabs.pruebatabs.R;

/**
 * Created by hanyd on 2016-04-28.
 */
public class CVSPagerAdapter extends FragmentPagerAdapter {

    public static Context context;

    public CVSPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0: return context.getString(R.string.tab1);
            case 1: return context.getString(R.string.tab2);
            case 2: return context.getString(R.string.tab3);
            default: return "";
        }
    }

    @Override
    public Fragment getItem(int i) {

        Fragment fragment = new CVSFragment();
        Bundle args = new Bundle();
        args.putString(CVSFragment.ARG_SECTION_NAME, getPageTitle(i).toString());
        args.putInt(CVSFragment.ARG_SECTION_IMAGE, i);
        args.putInt(CVSFragment.ARG_NUMBER_PAGE,i);
        fragment.setArguments(args);
        return fragment;

    }

}
