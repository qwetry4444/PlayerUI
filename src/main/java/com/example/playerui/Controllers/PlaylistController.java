package com.example.playerui.Controllers;

import com.example.MusicPlayer.Playlist;
import com.example.MusicPlayer.Song;
import com.example.playerui.DataSingleton;
import com.example.playerui.View;
import com.example.playerui.ViewSwitcher;
import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.*;

public class PlaylistController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button playButton;

    private int playlistId;
    private Playlist playlist;
    @FXML
    private Label playlistName;

    @FXML
    private TableView<Song> songsTable;
    @FXML
    private TableColumn<Playlist, Integer> songId;
    @FXML
    private TableColumn<Playlist, String> songName;
    @FXML
    private TableColumn<Playlist, String> songArtist;

    DataSingleton data;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        data = DataSingleton.getInstance();
        playlistId = data.getPlaylistId();
        playlist = data.musicPlayer().getPlaylistById(playlistId);

        songId.setCellValueFactory(new PropertyValueFactory<>("id"));
        songName.setCellValueFactory(new PropertyValueFactory<>("name"));
        songArtist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        songsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ObservableList<Song> tableSongs = songsTable.getItems();
        tableSongs.clear();

        List<Integer> playlistSongsIds = playlist.getSongsId();
        List<Song> songs = new ArrayList<>();
        for (int songId : playlistSongsIds){
            songs.add(data.musicPlayer().getSongById(songId));
        }

        tableSongs.addAll(songs);

        playlistName.setText(playlist.getName());

    }
    @FXML
    void handleDeleteButtonAction(ActionEvent event) {
        ObservableList<Song> selectedSongs = songsTable.getSelectionModel().getSelectedItems();
        //Song selectedSong = songsTable.getSelectionModel().getSelectedItem();
        List<Integer> selectedSongsIds = new ArrayList<>();
        for (Song song : selectedSongs){
            selectedSongsIds.add(song.getId());
        }

        playlist.deleteAllSongs(selectedSongsIds);
        songsTable.getItems().removeAll(selectedSongs);
    }

    @FXML
    void handleBackButtonAction(ActionEvent event) {
        ViewSwitcher.switchTo(View.LIST_PLAYLISTS);
    }

}
