package com.ccmcteam.ccmcteam.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapterCook extends FragmentPagerAdapter {

    private final List<Fragment> lstFragCook = new ArrayList<>();
    private final List<String> lstTitleCook = new ArrayList<>();


    public ViewPagerAdapterCook(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return lstFragCook.get(position);
    }

    @Override
    public int getCount() {
        return lstTitleCook.size();
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        return lstTitleCook.get(position);
    }

    public void AddFragCook (Fragment fragment, String title){
        lstFragCook.add(fragment);
        lstTitleCook.add(title);
    }
}