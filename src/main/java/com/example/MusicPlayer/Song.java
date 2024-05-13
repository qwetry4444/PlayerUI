package com.example.MusicPlayer;

import java.io.File;

public class Song {
    private final int id;
    private final String name;
    private final String artist;
    private final String text;
    private final String filePath;

    public Song(int id, String name, String artist, String text) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.text = text;
        filePath = "";
    }


    public String getName() {
        return name;
    }
    public String getArtist() {
        return artist;
    }

    public int getId() {
        return id;
    }
    public String getText() {return text; }


    public String toString() {
        return String.format("Песня %d: %s\nИсполнитель: %s\nТекст: %s\n", this.id, this.name, this.artist, this.text);
    }

    public void play(){
        System.out.println(toString());
    }
}