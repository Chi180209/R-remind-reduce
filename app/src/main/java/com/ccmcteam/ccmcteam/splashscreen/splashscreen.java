package com.ccmcteam.ccmcteam.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.ccmcteam.ccmcteam.R;
import com.ccmcteam.ccmcteam.activities.IntroActivity;
import com.ccmcteam.ccmcteam.activities.MainActivity;
import com.ccmcteam.ccmcteam.activities.signInUp.BeginActivity;
import com.ccmcteam.ccmcteam.activities.signInUp.LoginActivity;
import com.ccmcteam.ccmcteam.activities.signInUp.RegisterActivity;

public class splashscreen extends AppCompatActivity {

    ImageView img_wellcome;
    LinearLayout name_anf_version_wellcome;
    Animation down,up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        //set controls
        img_wellcome = findViewById(R.id.img_splash);
        name_anf_version_wellcome = findViewById(R.id.txt_splash);
        down = AnimationUtils.loadAnimation(this,R.anim.wellcome_activity_animation_downup);
        up = AnimationUtils.loadAnimation(this,R.anim.wellcome_activity_animation_updown);
        //set animation
        img_wellcome.setAnimation(up);
        name_anf_version_wellcome.setAnimation(down);
//        click button get started introClicked
        IntroActivity introActivity = new IntroActivity();
        boolean introClicked = introActivity.getStartClicked;
        RegisterActivity register = new RegisterActivity();
        boolean registered = register.registerOk;
        LoginActivity login = new LoginActivity();
        boolean logined = login.logined;
        //set splash screen
        if(introClicked == true && (registered == false || logined == false)){
            new Thread() {
                public void run() {
                    try {
                        sleep(3000);
                        startActivity(new Intent(splashscreen.this, BeginActivity.class));
                        finish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();

        }else if(introClicked == true && (registered == true || logined == true)){
            new Thread() {
                public void run() {
                    try {
                        sleep(3000);
                        startActivity(new Intent(splashscreen.this, MainActivity.class));
                        finish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        } else if (introClicked == false){
                new Thread() {
                    public void run() {
                        try {
                            sleep(3000);
                            startActivity(new Intent(splashscreen.this, IntroActivity.class));
                            finish();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        }
}
