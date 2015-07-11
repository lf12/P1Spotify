package com.lf12.p1spotify.app;

/**
 * Created by lcf on 17/06/2015.
 */

// TODO: Criar um SongAdapter
public class Song {

    public String name;
    public String id;
    public String album;
    public String album_thumb;
    public String preview_url;

    public Song(){
    }

    public Song(String name, String id, String album, String album_thumb, String preview_url){
        this.name = name;
        this.id = id;
        this.album = album;
        this.album_thumb= album_thumb;
        this.preview_url = preview_url;
    }
}