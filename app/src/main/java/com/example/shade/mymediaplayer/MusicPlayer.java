package com.example.shade.mymediaplayer;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dyanamitechetan.vusikview.VusikView;
import jp.wasabeef.glide.transformations.BlurTransformation;
import nl.changer.audiowife.AudioWife;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class MusicPlayer extends Fragment {

    FragmentManager fm;
    ArrayList<Song> songList;
    Song song;
    @BindView(R.id.backgroundImage) ImageView backgroundImage;
    @BindView(R.id.songCover)       ImageView songCover;
    @BindView(R.id.songName)        TextView songName;
    @BindView(R.id.songArtist)      TextView songArtist;
    @BindView(R.id.seekBar)         SeekBar seekBar;
    @BindView(R.id.playlistButton)  ImageButton playlistButton;
    @BindView(R.id.playButton)      ImageButton playButton;
    @BindView(R.id.pauseButton)     ImageButton pauseButton;
    @BindView(R.id.nextButton)      ImageButton nextButton;
    @BindView(R.id.previousButton)      ImageButton previousButton;
    @BindView(R.id.totalTime)       TextView totalTime;
    @BindView(R.id.runTime)         TextView runTime;
    @BindView(R.id.vusik)           VusikView vusikView;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.music_player,container, false);
        ButterKnife.bind(this,view);

        initializePlaylist();

        if(getArguments()!=null)
        {
                String songPath= getArguments().getString("songPath");
                AudioWife.getInstance().release();
                intializeViews(songPath);
                vusikView.start();

                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nextSong(song);
                    }
                });

                previousButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        previousSong(song);
                    }
                });

                AudioWife.getInstance().addOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        nextSong(song);
                    }
                });



        }
        else
        {


        }



        return  view;

    }





    @OnClick(R.id.playlistButton)
    public void openPlaylist(View view)
    {
        fm=getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(getActivity().findViewById(R.id.fragmentHolder).getId(),new PlayList());
        ft.addToBackStack("playList");
        ft.commit();
    }

    public  void initializePlaylist()
    {
        SongManager songManager=new SongManager();
        songList=songManager.getPlaylist();

    }

    public void intializeViews(String songPath)
    {

        SongManager songManager = new SongManager();
        song=songManager.getSongDetails(songPath);



        if(song.getCoverByte()==null)
        {
            Glide.with(getActivity())
                    .load(R.color.colorPrimaryDark)
                    .into(backgroundImage);

            Glide.with(this)
                    .load(R.drawable.musicplaceholder)
                    .apply(bitmapTransform(new CircleCrop()))
                    .into(songCover);


        }
        else
            {
                Glide.with(getActivity())
                        .load(song.getCoverByte())
                        .into(backgroundImage);

                Glide.with(this)
                        .load(song.getCoverByte())
                        .apply(bitmapTransform(new CircleCrop()))
                        .into(songCover);
            }


        AudioWife.getInstance()
                .init(getActivity(),Uri.parse(songPath))
                .setPlayView(playButton)
                .setPauseView(pauseButton)
                .setSeekBar(seekBar)
                .setRuntimeView(runTime)
                .setTotalTimeView(totalTime);

        songArtist.setText(song.getArtist());
        songName.setText(song.getName());

        AudioWife.getInstance().play();

    }

    public void nextSong(Song currSong)
    {
        int currIndex=0;

        for(int i=0; i<songList.size(); i++)
        {
            if(songList.get(i).getPath().equals(currSong.getPath()))
                currIndex=i;
        }


        int nextIndex=currIndex+1;
        int lastIndex=songList.size()-1;
        String firstSongPath = songList.get(0).getPath();

        if(currIndex<lastIndex)
        {
            String nextSongPath = songList.get(nextIndex).getPath();
            AudioWife.getInstance().release();
            intializeViews(nextSongPath);
            song=songList.get(nextIndex);
        }

        else if (currIndex==lastIndex)
        {
            AudioWife.getInstance().release();
            intializeViews(firstSongPath);
            song=songList.get(0);

        }
    }

    public void previousSong(Song currSong)
    {
        int currIndex=0;
        for(int i=0; i<songList.size(); i++)
        {
            if(songList.get(i).getPath().equals(currSong.getPath()))
                currIndex=i;
        }

        int previousIndex=currIndex-1;
        int firstIndex=0;


        if(currIndex!=firstIndex &&  currIndex>=0)
        {
            String previousSongPath = songList.get(previousIndex).getPath();
            AudioWife.getInstance().release();
            intializeViews(previousSongPath);
            song=songList.get(previousIndex);

        }

        else if (currIndex==firstIndex)
        {
            AudioWife.getInstance().release();
            intializeViews(currSong.getPath());
            song=currSong;

        }


    }






}
