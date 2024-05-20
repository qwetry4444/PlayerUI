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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class PlaylistController implements Initializable {

    @FXML
    private Button backButton;


    @FXML
    private Button editButton;

    @FXML
    private Button playButton;

    private int playlistId;
    private Playlist playlist;
    @FXML
    private Label playlistName;

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
        playlistId = data.getPlaylistId();
        playlist = data.musicPlayer().getPlaylistById(playlistId);

        setSongsTable();
        handleDoubleClickOnSong();


        ImageView editImage = new ImageView(Objects.requireNonNull(getClass().getResource("/images/pencil.png")).toExternalForm());
        editImage.setFitHeight(24);
        editImage.setFitWidth(24);
        editButton.setGraphic(editImage);

        playlistName.setText(playlist.getName());

    }

    void handleDoubleClickOnSong() {
        songsTable.setRowFactory( tv -> {
            TableRow<Song> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Song rowData = row.getItem();
                    data.musicPlayer().playPlaylist(playlistId);
                    data.musicPlayer().playSong(rowData.getId());

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("root.fxml"));
                    try {
                        Parent root = loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                    RootController rootController = loader.getController();
                    rootController.setCurrentSong(rowData);
                }
            });
            return row ;
        });
    }

    void setSongsTable(){
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
    }
//    @FXML
//    void handleDeleteButtonAction(ActionEvent event) {
//        ObservableList<Song> selectedSongs = songsTable.getSelectionModel().getSelectedItems();
//        //Song selectedSong = songsTable.getSelectionModel().getSelectedItem();
//        List<Integer> selectedSongsIds = new ArrayList<>();
//        for (Song song : selectedSongs){
//            selectedSongsIds.add(song.getId());
//        }
//
//        playlist.deleteAllSongs(selectedSongsIds);
//        songsTable.getItems().removeAll(selectedSongs);
//    }

    @FXML
    void handleBackButtonAction(ActionEvent event) {
        ViewSwitcher.switchTo(View.LIST_PLAYLISTS);
    }

    @FXML
    void handleEditButtonAction(ActionEvent event){ ViewSwitcher.switchTo(View.EDIT_PLAYLIST); }
}
