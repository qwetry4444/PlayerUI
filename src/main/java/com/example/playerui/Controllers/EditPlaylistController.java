package com.example.playerui;

import com.example.MusicPlayer.Song;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EditPlaylistController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private TextField playlistNameField;

    @FXML
    private Button saveButton;

    @FXML
    private TableView<Song> songsTable;
    @FXML
    private TableColumn<Song, Integer> songId;
    @FXML
    private TableColumn<Song, String> songName;
    @FXML
    private TableColumn<Song, String> songArtist;
    DataSingleton data;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        data = DataSingleton.getInstance();

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
        ViewSwitcher.switchTo(View.LIST_PLAYLISTS);
    }

    @FXML
    void handleSaveButtonAction(ActionEvent event) {

    }

}
