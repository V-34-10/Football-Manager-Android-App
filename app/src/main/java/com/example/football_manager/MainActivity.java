package com.example.football_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.android.ads.mediationtestsuite.activities.HomeActivity;

public class MainActivity extends AppCompatActivity {

    //final int MENU_COMBO_ID=1;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent go = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(go);
                finish();
            }
        }, 5*1000);

        //Animatin loading
        /*ImageView imageview = (ImageView) findViewById(R.id.imageView2);

        imageview.setBackgroundResource(R.drawable.myscale);

        final AnimationDrawable anim = (AnimationDrawable) imageview.getBackground();
        anim.start();*/


        //temp = (ImageView) findViewById(R.id.temp);

       /* Animation anim = null;
        anim = AnimationUtils.loadAnimation(this, R.anim.mycombo);
        temp.startAnimation(anim);*/
    }
}