package com.ccmcteam.ccmcteam.nguyenlieu;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.ccmcteam.ccmcteam.Adapter.ViewPagerAdapter;
import com.ccmcteam.ccmcteam.R;
import com.ccmcteam.ccmcteam.nguyenlieu.Fragment_view_ingredient.FragmentDrink;
import com.ccmcteam.ccmcteam.nguyenlieu.Fragment_view_ingredient.FragmentFish;
import com.ccmcteam.ccmcteam.nguyenlieu.Fragment_view_ingredient.FragmentFruitVegetable;
import com.ccmcteam.ccmcteam.nguyenlieu.Fragment_view_ingredient.FragmentMeat;
import com.ccmcteam.ccmcteam.nguyenlieu.Fragment_view_ingredient.FragmentOther;
import com.ccmcteam.ccmcteam.thongBao.viewPagerAdapter;
import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class IngredientFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;


    public IngredientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//         Inflate the layout for this fragment
        getActivity().setTitle(R.string.menu_nguyenlieu);
        View v = inflater.inflate(R.layout.fragment_ingredient, container, false);
//        ini view
        tabLayout = v.findViewById(R.id.tab_layout_menu);
        viewPager = v.findViewById(R.id.viewpager_menu);
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
        adapter.AddFragment(new FragmentMeat(),"");
        adapter.AddFragment(new FragmentFish(),"");
        adapter.AddFragment(new FragmentFruitVegetable(),"");
        adapter.AddFragment(new FragmentDrink(),"");
        adapter.AddFragment(new FragmentOther(),"");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.nguyenlieu_fragment_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch(item.getItemId()){
            case R.id.add_nguyenlieu :
                intent = new Intent(getActivity(),add_ingredient_activity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
