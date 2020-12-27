package com.ccmcteam.ccmcteam.Recipe;


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
import com.ccmcteam.ccmcteam.Recipe.Fragment_view_recipe.FragmentDessert;
import com.ccmcteam.ccmcteam.Recipe.Fragment_view_recipe.FragmentOther;
import com.ccmcteam.ccmcteam.Recipe.Fragment_view_recipe.FragmentRice;
import com.ccmcteam.ccmcteam.Recipe.Fragment_view_recipe.FragmentSalad;
import com.ccmcteam.ccmcteam.Recipe.Fragment_view_recipe.FragmentSoup;
import com.ccmcteam.ccmcteam.activities.RecipeFormInsertActivity;
import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;


    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//         Inflate the layout for this fragment
        getActivity().setTitle(R.string.menu_menu);
        View v = inflater.inflate(R.layout.fragment_recipe, container, false);


//        ini view
        tabLayout = v.findViewById(R.id.tab_layout_menu);
        viewPager = v.findViewById(R.id.viewpager_menu);

        //set up viewpager
        setupViewPager();
        tabLayout.setupWithViewPager(viewPager);

        //        set icon for tablayout
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_food_rice);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_food_soup);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_food_salad);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_food_dessert);
        tabLayout.getTabAt(4).setIcon(R.drawable.ic_other);
//        remove from action bar
        ActionBar actionBar =  ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setElevation(0);
        return v;

    }

    private void setupViewPager() {
        FragmentManager fm = getFragmentManager();
        adapter = new ViewPagerAdapter(fm);

//        Add Fragment here (luu y vi tri code)
        adapter.AddFragment(new FragmentRice(), "Rice");
        adapter.AddFragment(new FragmentSoup(), "Soup");
        adapter.AddFragment(new FragmentSalad(), "Salad");
        adapter.AddFragment(new FragmentDessert(), "Dessert");
        adapter.AddFragment(new FragmentOther(), "Others");

//        set up
        viewPager.setAdapter(adapter);
    }


    //    @Override
//    public void onPrepareOptionsMenu(Menu menu) {
//        menu.setGroupVisible(R.id.menu_overflow, false);
//        super.onPrepareOptionsMenu(menu);
//    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.recipe_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.add_recipe:
                intent = new Intent(getActivity(), RecipeFormInsertActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
