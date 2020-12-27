package com.ccmcteam.ccmcteam.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.ccmcteam.ccmcteam.Model.Firebase.userInfo;
import com.ccmcteam.ccmcteam.R;
import com.ccmcteam.ccmcteam.about.AboutFragment;
import com.ccmcteam.ccmcteam.Recipe.MenuFragment;
import com.ccmcteam.ccmcteam.nguyenlieu.IngredientFragment;
import com.ccmcteam.ccmcteam.setting.SettingFragment;
import com.ccmcteam.ccmcteam.thongBao.ThongBaoFragment;
import com.ccmcteam.ccmcteam.user.UserFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;

    //firebase
    private FirebaseDatabase headData;
    private DatabaseReference readHeader;
    FirebaseAuth firebaseAuth;
    public String uId;
    private byte[] bImage;

    //class
    userInfo user;
    //view
    ImageView headerImage;
    TextView headerName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // friends for toolbar we use v7 package

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.textOnPrimary));
        setSupportActionBar(toolbar);

        mDrawerLayout = findViewById(R.id.drawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        // so friends my item listener is not working beacouse of some line of code i miss..
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //set header image and mail
        View headerView = navigationView.getHeaderView(0);
        headerImage = headerView.findViewById(R.id.header_image);
        headerName = headerView.findViewById(R.id.header_gmail);

        user = new userInfo();
        headData = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser fireUser = firebaseAuth.getCurrentUser();
        uId = fireUser.getUid();
        readHeader = headData.getReference("Users/" + uId);

        readHeader.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                readDataHeader(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        // friends now create fragments

        UserFragment fragment = new UserFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment,"Home");
        fragmentTransaction.commit();

        // so now implement onNavigationItemselected

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();

        if (id == R.id.user) {
            UserFragment fragment = new UserFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment, "User");
            fragmentTransaction.commit();
        }
        else if (id == R.id.menu) {
            MenuFragment fragment = new MenuFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment, "メニュー");
            fragmentTransaction.commit();
        }
        else if (id == R.id.nguyenlieu) {
            IngredientFragment fragment = new IngredientFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment, "食材");
            fragmentTransaction.commit();
        }
        else if (id == R.id.thongbao) {
            ThongBaoFragment fragment = new ThongBaoFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment, "通知");
            fragmentTransaction.commit();
        }
        else if (id == R.id.setting) {
            SettingFragment fragment = new SettingFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment, "設定");
            fragmentTransaction.commit();
        }
        else if (id == R.id.about) {
            AboutFragment fragment = new AboutFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment, "About");
            fragmentTransaction.commit();
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    private void readDataHeader(DataSnapshot dataSnapshot) {
//        xuat du lieu tu firebase ra
        user = new userInfo();
        user = dataSnapshot.child("infomation").getValue(userInfo.class);
//        convert string to image
        if (dataSnapshot.hasChild("infomation")) {
            String sImage = user.getImage();
            bImage = Base64.decode(sImage, Base64.DEFAULT);

            Bitmap decodedImage = BitmapFactory.decodeByteArray(bImage, 0, bImage.length);
            headerImage.setImageBitmap(decodedImage);
        }
//        set text for text view
        if (dataSnapshot.hasChild("infomation")) {
            headerName.setText(user.getName() + " 様");
        }
    }
}
