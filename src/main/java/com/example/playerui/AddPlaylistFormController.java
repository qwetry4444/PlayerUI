package com.example.playerui;

import com.example.MusicPlayer.MusicPlayer;
import com.example.MusicPlayer.Playlist;
import com.example.MusicPlayer.Song;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.w3c.dom.events.Event;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddPlaylistFormController implements Initializable {

    @FXML
    private Button addButton;

    @FXML
    private TextField playlistNameField;

    @FXML
    private TableView<Song> songsTable;
    @FXML
    private TableColumn<Playlist, Integer> songId;
    @FXML
    private TableColumn<Playlist, String> songName;
    @FXML
    private TableColumn<Playlist, String> songArtist;

    DataSingleton data = DataSingleton.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        songId.setCellValueFactory(new PropertyValueFactory<>("id"));
        songName.setCellValueFactory(new PropertyValueFactory<>("name"));
        songArtist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        songsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


        ObservableList<Song> songs = songsTable.getItems();
        List<Song> loadedSongs = data.musicPlayer().getSongs();
        songs.addAll(loadedSongs);
        songsTable.setItems(songs);
    }
    @FXML
    void handleBackButtonAction(ActionEvent event) {
        ViewSwitcher.switchTo(View.PLAYLISTS);
    }

    @FXML
    void handleAddButtonAction(ActionEvent event) throws IOException {
        List<Song> selectedSongs = songsTable.getSelectionModel().getSelectedItems();
        data.musicPlayer().addPlaylist(playlistNameField.getText(), selectedSongs);
        ViewSwitcher.switchTo(View.PLAYLISTS);
    }
}
