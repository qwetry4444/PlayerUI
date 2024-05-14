package com.example.playerui;

import com.example.MusicPlayer.MusicPlayer;

public class DataSingleton {
    private  static  final DataSingleton instance = new DataSingleton();

    public DataSingleton(){
        musicPlayer = new MusicPlayer();
    }

    private MusicPlayer musicPlayer;

    private int PlayListId;

    public static DataSingleton getInstance(){
        return instance;
    }

    public int getPlaylistId(){
        return PlayListId;
    }

    public void setPlaylistId(int playlistId){
        this.PlayListId = playlistId;
    }

    public MusicPlayer musicPlayer(){
        return musicPlayer;
    }

    public void setMusicPlayer(MusicPlayer musicPlayer) {
        this.musicPlayer = musicPlayer;
    }
}
