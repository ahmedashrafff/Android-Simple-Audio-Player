package com.example.shade.mymediaplayer;

import android.media.MediaMetadataRetriever;
import android.os.Environment;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

public class SongManager {

    private ArrayList<Song> songsList = new ArrayList<Song>();

    File dir = Environment.getExternalStorageDirectory();


    public SongManager() {

        searchForSongs(dir);

    }

    public ArrayList<Song>  getPlaylist()
    {
        return songsList;
    }



    public void searchForSongs(File directory)
    {
        File listFile[] = directory.listFiles();
        if(listFile!=null)
        {
            for(int i=0; i<listFile.length; i++)
            {
                if(listFile[i].isDirectory())
                {   searchForSongs(listFile[i]);}
                else if(listFile[i].isFile())
                {
                    if(listFile[i].getName().endsWith(".mp3"))
                    {
                        Song song= new Song();
                        song.setName(listFile[i].getName().substring(0, (listFile[i].getName().length() - 4)));
                        song.setPath(listFile[i].getPath());
                        songsList.add(song);
                    }

                    else if (listFile[i].getName().endsWith(".MP3"))
                    {
                        Song song= new Song();
                        song.setName(listFile[i].getName().substring(0, (listFile[i].getName().length() - 4)));
                        song.setPath(listFile[i].getPath());
                        songsList.add(song);
                    }

                }


            }
        }

    }


    public static String getSongName(String songPath)
    {
        MediaMetadataRetriever metaRetriver= new MediaMetadataRetriever();
        metaRetriver.setDataSource(songPath);
        if(metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE) !=null)
        return metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        else
            return "Unknown";
    }


    public static String getSongArtist(String songPath)
    {
        MediaMetadataRetriever metaRetriver= new MediaMetadataRetriever();
        metaRetriver.setDataSource(songPath);
        if(metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST) !=null)
            return metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        else
            return "Unknown";
    }



    public static Song getSongDetails(String path)
    {
        MediaMetadataRetriever metaRetriver= new MediaMetadataRetriever();
        metaRetriver.setDataSource(path);
        Song song = new Song();

        byte[] imageByte=metaRetriver.getEmbeddedPicture();
        String genre=metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE);
        String artist=metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        String albumName=metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
        String duration=metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        String songName=metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        String bitrate=metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE).substring(0,3)+"kpbs";
        String year=metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_YEAR);


        if(artist==null)
            artist="Unkown";

        else if(albumName==null)
            albumName="Unkown";

        else if(duration==null)
            duration="Unkown";

        else if(songName==null)
            songName="Unkown";

        else if(bitrate==null)
            bitrate="Unkown";

        else if(year==null)
            year="Unkown";

        else if(genre==null)
            genre="Unkown";





        song.setName(songName);
        song.setPath(path);
        song.setAlbumName(albumName);
        song.setArtist(artist);
        song.setBitrate(bitrate);
        song.setDuration(convertDuration(duration));
        song.setGenre(genre);
        song.setYear(year);
        song.setCoverByte(imageByte);

        return  song;
    }





    public static String convertDuration(String duration) {
        int dur = Integer.parseInt(duration);

        int hrs = (dur / 3600000);
        int mns = (dur / 60000) % 60000;
        int scs = dur % 60000 / 1000;

        String songTime = String.format("%02d:%02d:%02d", hrs,  mns, scs);

        return  songTime;
    }




}


