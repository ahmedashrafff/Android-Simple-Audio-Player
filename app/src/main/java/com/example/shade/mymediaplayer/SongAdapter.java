package com.example.shade.mymediaplayer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import nl.changer.audiowife.AudioWife;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder>  {
    private ArrayList<Song> songList;
    private Context context;



    public SongAdapter(ArrayList<Song> songList, Context context) {
        this.songList=songList;
        this.context=context;

    }

    @NonNull
    @Override
    public SongAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_viewholder,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position)
    {

        holder.songTitle.setText(SongManager.getSongName(songList.get(position).getPath()));
        holder.artist.setText(SongManager.getSongArtist(songList.get(position).getPath()));
        holder.viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SongView.class);
                intent.putExtra("songPath", songList.get(position).getPath());
                context.startActivity(intent);
            }
        });
        holder.songHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,MainActivity.class);
                intent.putExtra("songPath",songList.get(position).getPath());
                ((Activity)context).finish();
                context.startActivity(intent);

            }
        });

    }



    @Override
    public int getItemCount() {
        return songList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView songTitle;
        TextView artist;
        ImageView viewDetails;
        ConstraintLayout songHolder;


         public ViewHolder(View v) {
            super(v);

             songTitle=v.findViewById(R.id.songName);
             artist=v.findViewById(R.id.artistName);
             viewDetails=v.findViewById(R.id.viewDetails);
             songHolder=v.findViewById(R.id.songDetailsHolder);


         }
    }
}
