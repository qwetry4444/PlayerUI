package com.example.playerui.Controllers;

import com.example.MusicPlayer.Playlist;
import com.example.MusicPlayer.Song;
import com.example.playerui.DataSingleton;
import com.example.playerui.View;
import com.example.playerui.ViewSwitcher;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ListSongsController implements Initializable {

    private int playlistId;
    private Playlist playlist;
    @FXML
    private Button addButton;

    @FXML
    private Button backButton;

    @FXML
    private TableView<Song> notInPlaylistSongsTable;
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
        playlistId = data.getPlaylistId();
        playlist = data.musicPlayer().getPlaylistById(playlistId);

        songId.setCellValueFactory(new PropertyValueFactory<>("id"));
        songName.setCellValueFactory(new PropertyValueFactory<>("name"));
        songArtist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        notInPlaylistSongsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ObservableList<Song> tableSongs = notInPlaylistSongsTable.getItems();
        tableSongs.clear();

        List<Song> notInPlaylistSongsId = data.musicPlayer().getNotInPlaylistSongs(playlistId);
        tableSongs.addAll(notInPlaylistSongsId);
    }


    @FXML
    void handleAddButtonAction(ActionEvent event) {
        List<Song> selectedSongs = notInPlaylistSongsTable.getSelectionModel().getSelectedItems();
        notInPlaylistSongsTable.getItems().addAll(selectedSongs);
        playlist.addAllSongs(selectedSongs);
        ViewSwitcher.switchTo(View.EDIT_PLAYLIST);
    }

    @FXML
    void handleBackButtonAction(ActionEvent event) { ViewSwitcher.switchTo(View.PLAYLIST); }

}
