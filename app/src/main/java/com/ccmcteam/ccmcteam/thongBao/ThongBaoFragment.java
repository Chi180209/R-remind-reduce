package com.ccmcteam.ccmcteam.thongBao;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.ccmcteam.ccmcteam.R;
import com.ccmcteam.ccmcteam.thongBao.Fragment_view_notification.Fragment_noti_drink;
import com.ccmcteam.ccmcteam.thongBao.Fragment_view_notification.Fragment_noti_fish;
import com.ccmcteam.ccmcteam.thongBao.Fragment_view_notification.Fragment_noti_meat;
import com.ccmcteam.ccmcteam.thongBao.Fragment_view_notification.Fragment_noti_other;
import com.ccmcteam.ccmcteam.thongBao.Fragment_view_notification.Fragment_noti_vegetable;
import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThongBaoFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public ThongBaoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//         Inflate the layout for this fragment
        getActivity().setTitle(R.string.menu_thongbao);
        View v = inflater.inflate(R.layout.fragment_thongbao, container, false);
//        init view
        tabLayout = v.findViewById(R.id.tab_layout_thongbao);
        viewPager = v.findViewById(R.id.viewpager_thongbao);
//        setup viewpager
        setupViewPager();
        tabLayout.setupWithViewPager(viewPager);
//        set icon for tablayout
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_meat);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_fish);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_vegetable);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_drink);
        tabLayout.getTabAt(4).setIcon(R.drawable.ic_other);
//        remove from action bar
        ActionBar actionBar =  ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setElevation(0);

        return v;
    }

    private void setupViewPager() {
        FragmentManager fm = getFragmentManager();
        viewPagerAdapter adapter = new viewPagerAdapter(fm);
        adapter.AddFragment(new Fragment_noti_meat(),"");
        adapter.AddFragment(new Fragment_noti_fish(),"");
        adapter.AddFragment(new Fragment_noti_vegetable(),"");
        adapter.AddFragment(new Fragment_noti_drink(),"");
        adapter.AddFragment(new Fragment_noti_other(),"");
        viewPager.setAdapter(adapter);
    }

}
