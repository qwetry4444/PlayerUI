package com.example.MusicPlayer;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Song {
    private final int id;
    private final String name;
    private final String artist;
    private final String text;
    private final String filePath;

    public Song(int id, String name, String artist, String text, String filePath) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.text = text;
        this.filePath = filePath;
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
    public String getFilePath() { return filePath; }

    public String toString() {
        return String.format("Песня %d: %s\nИсполнитель: %s\nТекст: %s\n", this.id, this.name, this.artist, this.text);
    }

    public void play(){
        System.out.println(toString());

//        Media sound = new Media(new File(filePath).toURI().toString());
//        MediaPlayer mediaPlayer = new MediaPlayer(sound);
//        mediaPlayer.play();
    }
}