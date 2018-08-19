package com.example.shade.mymediaplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class SongView extends AppCompatActivity {


    @BindView(R.id.artistName)
    TextView artistName;
    @BindView(R.id.albumName)
    TextView albumName;
    @BindView(R.id.songName)
    TextView songName;
    @BindView(R.id.yearSong)
    TextView yearSong;
    @BindView(R.id.durationSong)
    TextView durationSong;
    @BindView(R.id.genreSong)
    TextView genreSong;
    @BindView(R.id.bitrateSong)
    TextView bitrateSong;
    @BindView(R.id.songCover)
    ImageView songCover;
    @BindView(R.id.backgroundImage2)
    ImageView songBackground;
    @BindView(R.id.detailsBackground)
    LinearLayout detailsBackground;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_view);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        String path = intent.getStringExtra("songPath");

        if (path != null) {
            Song song = SongManager.getSongDetails(path);
            Glide.with(this)
                    .load(song.getCoverByte())
                    .apply(bitmapTransform(new CircleCrop()))
                    .into(songCover);
            Glide.with(this)
                    .load(song.getCoverByte())
                    .into(songBackground);


            artistName.setText(song.getArtist());
            songName.setText(song.getName());
            albumName.setText(song.getAlbumName());
            yearSong.setText(song.getYear());
            durationSong.setText(song.getDuration());
            genreSong.setText(song.getGenre());
            bitrateSong.setText(song.getBitrate());


        }


    }
}