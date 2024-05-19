package com.example.playerui.Controllers;

import com.example.MusicPlayer.Playlist;
import com.example.playerui.DataSingleton;
import com.example.playerui.MusicPlayerApplication;
import com.example.playerui.View;
import com.example.playerui.ViewSwitcher;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ListPlaylistsController implements Initializable {

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TableColumn<Playlist, String> playlistName;

    @FXML
    private TableView<Playlist> playlistsTable;


    DataSingleton data;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        data = DataSingleton.getInstance();

        playlistName.setCellValueFactory(new PropertyValueFactory<>("name"));
        playlistsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ObservableList<Playlist> playlists = playlistsTable.getItems();
        playlists.clear();
        List<Playlist> loadedPlaylists = data.musicPlayer().getPlaylists();
        playlists.addAll(loadedPlaylists);

        ImageView plusImage = new ImageView(Objects.requireNonNull(getClass().getResource("/images/plus.png")).toExternalForm());
        plusImage.setFitHeight(32);
        plusImage.setFitWidth(32);
        addButton.setGraphic(plusImage);

        ImageView deleteImage = new ImageView(Objects.requireNonNull(getClass().getResource("/images/bucket.png")).toExternalForm());
        deleteImage.setFitHeight(32);
        deleteImage.setFitWidth(32);
        deleteButton.setGraphic(deleteImage);


        playlistsTable.setRowFactory( tv -> {
            TableRow<Playlist> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Playlist rowData = row.getItem();
                    data.setPlaylistId(rowData.getId());
                    ViewSwitcher.switchTo(View.PLAYLIST);
                }
            });
            return row ;
        });

        playlistsTable.setItems(playlists);

    }



    @FXML
    void handleDeleteButtonAction(ActionEvent event) {
        ObservableList<Playlist> selectedPlaylists = playlistsTable.getSelectionModel().getSelectedItems();

        List<Integer> playlistsId = new ArrayList<>();
        for (Playlist playlist : selectedPlaylists){
            playlistsId.add(playlist.getId());
        }
        playlistsTable.getItems().removeAll(selectedPlaylists);

        data.musicPlayer().deleteAllPlaylistsById(playlistsId);
    }

    @FXML
    void handleAddButtonAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MusicPlayerApplication.class.getResource("addPlaylistForm-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        ViewSwitcher.switchTo(View.ADD_PLAYLIST_FORM);
    }
}
