package com.ccmcteam.ccmcteam.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.ccmcteam.ccmcteam.Adapter.ViewPagerAdapterCook;
import com.ccmcteam.ccmcteam.R;
import com.ccmcteam.ccmcteam.Recipe.Fragment_view_recipe.FragmentIngredient;
import com.ccmcteam.ccmcteam.Recipe.Fragment_view_recipe.FragmentHowToCook;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

public class CookActivity extends AppCompatActivity {

    private TabLayout tabLayoutCook;
    private ViewPager viewPagerCook;
    private ViewPagerAdapterCook vpAdapterCook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_activity_cook);

        tabLayoutCook = findViewById(R.id.tablayout_cook_id);
        viewPagerCook = findViewById(R.id.viewpager_cook_id);
        vpAdapterCook = new ViewPagerAdapterCook(getSupportFragmentManager());

        vpAdapterCook.AddFragCook(new FragmentIngredient(),"Ingredient");
        vpAdapterCook.AddFragCook(new FragmentHowToCook(),"How to cook");


        viewPagerCook.setAdapter(vpAdapterCook);
        tabLayoutCook.setupWithViewPager(viewPagerCook);


        // Recieve data
        Bundle bundle = getIntent().getExtras();

        String title = bundle.getString("pName");
        String idRecipe = bundle.getString("pId");

        //get image from intent
        byte[] bytes = bundle.getByteArray("pImage");
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);


        // ini views

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar_id);
        collapsingToolbarLayout.setTitleEnabled(true);
        ImageView img_recipe_detail = findViewById(R.id.recipe_detail_img_id);
        //setting values to each view
        img_recipe_detail.setImageBitmap(bitmap);
        collapsingToolbarLayout.setTitle(title);

        //send idRecipe to Ingredient fragment
//        Bundle bundle1 = new Bundle();
//        bundle1.putString("recipeId", idRecipe);
//        FragmentIngredient fragmentIngredient = new FragmentIngredient();
//        fragmentIngredient.setArguments(bundle1);


    }
}
