package com.ccmcteam.ccmcteam.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.ccmcteam.ccmcteam.Intro.IntroViewPagerAdapter;
import com.ccmcteam.ccmcteam.Intro.ScreenItem;
import com.ccmcteam.ccmcteam.R;
import com.ccmcteam.ccmcteam.activities.signInUp.BeginActivity;
import com.ccmcteam.ccmcteam.activities.signInUp.LoginActivity;
import com.ccmcteam.ccmcteam.activities.signInUp.RegisterActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter;
    TabLayout tabIntroSlide;
    Button btn_intro_next;
    int position = 0;
    Button btnGetLastScreen;
    Animation btnAnim;
    public boolean getStartClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



//        make the activity on full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        kiểm tra intro đã mở lần nào chưa
        if(restorePrefData()){
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }

        setContentView(R.layout.activity_intro);

//        hide action bar

//        getSupportActionBar().hide();

//        ini views
        btn_intro_next = findViewById(R.id.btn_intro_next);
        btnGetLastScreen = findViewById(R.id.btn_getstarted);
        tabIntroSlide = findViewById(R.id.intro_tabLayout);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.intro_button_anim);

//        fill list screen
        final List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("健康","aaaa",R.drawable.thongbao_hsd_ic));
        mList.add(new ScreenItem("メニュー","aaaa",R.drawable.thongbao_hsd_ic));
        mList.add(new ScreenItem("食材","aaaa",R.drawable.thongbao_hsd_ic));
        mList.add(new ScreenItem("通知","aaaa",R.drawable.thongbao_hsd_ic));
        mList.add(new ScreenItem("設定","aaaa",R.drawable.thongbao_hsd_ic));
        mList.add(new ScreenItem("About","aaaa",R.drawable.thongbao_hsd_ic));

//        setup viewpager
        screenPager = findViewById(R.id.screen_viewpager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this,mList);
        screenPager.setAdapter(introViewPagerAdapter);

//        setup tablayout with viewpager
        tabIntroSlide.setupWithViewPager(screenPager);

//        next button click listner
        btn_intro_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                position = screenPager.getCurrentItem();
                if(position < mList.size()){
                    position++;
                    screenPager.setCurrentItem(position);
                }
                if(position == mList.size()-1){ // last screen
                    loadLastScreen();
                }



            }
        });
//        tablayout add change listener
        tabIntroSlide.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == mList.size()-1){
                    loadLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

//        get started button click listener
        btnGetLastScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getStartClicked = true;
//                open main activity
                RegisterActivity register = new RegisterActivity();
                boolean registered = register.registerOk;
                LoginActivity login = new LoginActivity();
                boolean logined= login.logined;

                if(registered == true || logined == true) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
//                save a boolean value to storage so next time when the user run the app
//                nếu đã mở 1 lần rồi thì thôi
                    savePrefsData();
                    finish();
                }else if(registered == false && logined == false){
                    Intent intent = new Intent(getApplicationContext(), BeginActivity.class);
                    startActivity(intent);
                    savePrefsData();
                    finish();
                }
            }
        });

    }

    public boolean restorePrefData(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean beforeOpened = pref.getBoolean("isIntroOpened",false);
        return beforeOpened;
    }


    public void savePrefsData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpened",true);
        editor.commit();

    }

    private void loadLastScreen() {
        btnGetLastScreen.setVisibility(View.VISIBLE);
        btn_intro_next.setVisibility(View.INVISIBLE);
        tabIntroSlide.setVisibility(View.INVISIBLE);
//        lets creat the button animation
//        set animation
        btnGetLastScreen.setAnimation(btnAnim);

    }
}
