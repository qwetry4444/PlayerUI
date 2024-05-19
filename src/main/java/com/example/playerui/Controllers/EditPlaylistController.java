package com.example.playerui;

import com.example.MusicPlayer.Playlist;
import com.example.MusicPlayer.Song;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class EditPlaylistController implements Initializable {

    private Playlist playlist;
    @FXML
    private Button backButton;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button saveButton;

    @FXML
    private TextField playlistNameField;
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
        int playlistId = data.getPlaylistId();
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

        ImageView addImage = new ImageView(Objects.requireNonNull(getClass().getResource("/images/plus.png")).toExternalForm());
        addImage.setFitHeight(24);
        addImage.setFitWidth(24);
        addButton.setGraphic(addImage);

        ImageView deleteImage = new ImageView(Objects.requireNonNull(getClass().getResource("/images/bucket.png")).toExternalForm());
        deleteImage.setFitHeight(24);
        deleteImage.setFitWidth(24);
        deleteButton.setGraphic(deleteImage);

        playlistNameField.setText(playlist.getName());
    }

    @FXML
    void handleBackButtonAction(ActionEvent event) { ViewSwitcher.switchTo(View.LIST_PLAYLISTS); }

    @FXML
    void handleSaveButtonAction(ActionEvent event) {
        playlist.setName(playlistNameField.getText());
    }

    @FXML
    void handleAddButtonAction(ActionEvent event) { ViewSwitcher.switchTo(View.LIST_SONGS);}

    @FXML
    void handleDeleteButtonAction(ActionEvent event) {
        ObservableList<Song> selectedSongs = songsTable.getSelectionModel().getSelectedItems();

        List<Integer> songsId = new ArrayList<>();
        for (Song song : selectedSongs){
            songsId.add(song.getId());
        }
        songsTable.getItems().removeAll(selectedSongs);

        data.musicPlayer().deleteAllSongsFromPlaylistByIds(songsId, playlist.getId());
    }

}
