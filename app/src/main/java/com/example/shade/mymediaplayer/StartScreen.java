package com.example.shade.mymediaplayer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class StartScreen extends AppCompatActivity {

    @BindView(R.id.avi)
    AVLoadingIndicatorView avLoadingIndicatorView;
    @BindView(R.id.backgroundImage4)
    ImageView backgroundImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        ButterKnife.bind(this);

        //Load Activity Blured Background
        Glide.with(this)
                .load(R.drawable.background2)
                .into(backgroundImage);

        //Start Loading Animation
        avLoadingIndicatorView.show();



        //Delay the intent half a minute to give the loading animation enough time to start
        //then launch a the new activity and destroy the current
       new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(getBaseContext(),MainActivity.class));
                finish();
            }
        }, 2000);





    }
}
