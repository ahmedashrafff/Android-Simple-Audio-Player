package com.example.shade.mymediaplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;


import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import nl.changer.audiowife.AudioWife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fragmentHolder) FrameLayout fragmentHolder;
    FragmentManager fm ;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        String path=getIntent().getStringExtra("songPath");

        if(path!=null)
        {
            Log.d("pathpath",path);
            Bundle bundle=new Bundle();
            bundle.putString("songPath",path);
            addMusicPlayerFragment(bundle);

        }

        else
        {
            addMusicPlayerFragment(null);


        }


    }


    public  void addMusicPlayerFragment(Bundle bundle)
    {
            fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            MusicPlayer musicPlayer = new MusicPlayer();
            musicPlayer.setArguments(bundle);
            ft.add(R.id.fragmentHolder, musicPlayer);
            ft.addToBackStack("musicPlayer");
            ft.commit();


    }

    @Override
    public void onBackPressed() {

        if(fm.findFragmentById(R.id.fragmentHolder) instanceof MusicPlayer)
        {
            MusicPlayer mYourFragment = (MusicPlayer) fm.findFragmentById(R.id.fragmentHolder);

            if(mYourFragment!=null){

                AudioWife.getInstance().release();
                finish();
            }


        }
        else
            super.onBackPressed();


}
}
