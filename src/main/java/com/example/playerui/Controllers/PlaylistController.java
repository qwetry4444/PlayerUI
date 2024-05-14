package com.example.playerui.Controllers;

import com.example.MusicPlayer.Playlist;
import com.example.MusicPlayer.Song;
import com.example.playerui.DataSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class PlaylistController implements Initializable {

    @FXML
    private Button deleteButton;

    @FXML
    private Button playButton;

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

        songId.setCellValueFactory(new PropertyValueFactory<>("id"));
        songName.setCellValueFactory(new PropertyValueFactory<>("name"));
        songArtist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        songsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }
    @FXML
    void handleDeleteButtonAction(ActionEvent event) {

    }

}
