package com.example.playerui;

import com.example.MusicPlayer.MusicPlayer;
import com.example.MusicPlayer.Playlist;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PlaylistsController implements Initializable {

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TableColumn<Playlist, String> playlistName;

    @FXML
    private TableView<Playlist> playlistsTable;

    DataSingleton data = DataSingleton.getInstance();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playlistName.setCellValueFactory(new PropertyValueFactory<>("name"));
        playlistsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


        MusicPlayer musicPlayer = new MusicPlayer();
        data.setMusicPlayer(musicPlayer);

        ObservableList<Playlist> playlists = playlistsTable.getItems();
        List<Playlist> loadedPlaylists = musicPlayer.getPlaylists();
        playlists.addAll(musicPlayer.getPlaylists());
        playlistsTable.setItems(playlists);
    }

    @FXML
    void handleDeleteButtonAction(ActionEvent event) {
        List<Playlist> selectedPlaylists = playlistsTable.getSelectionModel().getSelectedItems();
        playlistsTable.getItems().removeAll(selectedPlaylists);
    }

    @FXML
    void handleAddButtonAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MusicPlayerApplication.class.getResource("addPlaylistForm-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        ViewSwitcher.switchTo(View.ADD_PLAYLIST_FORM);
    }
}
