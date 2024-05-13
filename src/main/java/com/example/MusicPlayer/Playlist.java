package com.example.MusicPlayer;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private final int id;
    private final String name;
    public List<Integer> songsId;


    public Playlist(int id, String name) {
        this.id = id;
        this.name = name;
        this.songsId = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }
    public List<Integer> getSongsId() { return songsId; }

    public void addSong(int songId) {
        songsId.add(songId);
    }

    public boolean deleteSong(Integer songId){
        return songsId.remove(songId);
    }

    public int getFirstSongId(){ return songsId.getFirst(); }

    public int getNextSongId(int currentSongId){
        for (int i = 0; i < songsId.size() - 1; i++){
            if (songsId.get(i) == currentSongId){
                return songsId.get(i + 1);
            }
        }
        return getFirstSongId();
    }

    public int getPrevSongId(int currentSongId){
        for (int i = 1; i < songsId.size(); i++){
            if (songsId.get(i) == currentSongId){
                return songsId.get(i - 1);
            }
        }
        return songsId.getLast();
    }

    public String toString() {
        StringBuilder string = new StringBuilder(String.format("Плейлист %d: %s\n", id, name));
        if (songsId.isEmpty()){
            string.append("Песен нет");
        } else
        {
            string.append("Список песен:\n");
            for (int songId : songsId){
                string.append(String.format("Песня %d\n", songId));
            }
        }

        return string.toString();
    }
}