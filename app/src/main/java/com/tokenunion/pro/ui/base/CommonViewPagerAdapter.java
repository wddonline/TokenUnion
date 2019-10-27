package com.tokenunion.pro.ui.base;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class CommonViewPagerAdapter<T extends Fragment> extends FragmentPagerAdapter {

    private List<T> fragments;
    private String[] titles;

    public CommonViewPagerAdapter(FragmentManager fm, List<T> fragments) {
        this(fm, fragments, null);
    }

    public CommonViewPagerAdapter(FragmentManager fm, List<T> fragments, String[] titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (titles != null) {
            return titles[position];
        }
        return super.getPageTitle(position);
    }
}
