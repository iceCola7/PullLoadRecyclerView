package com.cxz.pullloadrecyclerview.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenxz on 2015/12/27.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragments=new ArrayList<Fragment>();
    private final List<String> mFragmentTitleList=new ArrayList<String>();
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    public void addFragment(Fragment fg, String title){
        mFragments.add(fg);
        mFragmentTitleList.add(title);
    }
}
