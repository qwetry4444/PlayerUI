package com.example.playerui.Controllers;

import com.example.MusicPlayer.Song;
import com.example.playerui.DataSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class RootController implements Initializable {
    @FXML
    private BorderPane mainPane;

    @FXML
    private Button playNextButton;

    @FXML
    private Button playPauseButtons;

    @FXML
    private Button playPrevButton;

    @FXML
    private Button repeatButton;

    @FXML
    private Label songArtist;

    @FXML
    private Label songName;

    DataSingleton data;

    public BorderPane getMainPane() {
        return mainPane;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        data = DataSingleton.getInstance();
        loadImages();
    }

    void loadImages(){
        ImageView playPauseImage = setImage("play");
        playPauseButtons.setGraphic(playPauseImage);

        ImageView playPrevImage = setImage("prev");
        playPrevButton.setGraphic(playPrevImage);

        ImageView playNextImage = setImage("next");
        playNextButton.setGraphic(playNextImage);

        ImageView repeatImage = setImage("repeat");
        repeatButton.setGraphic(repeatImage);
    }

    ImageView setImage(String imageName){
        ImageView imageView = new ImageView(Objects.requireNonNull(getClass().getResource(String.format("/images/%s.png", imageName) )).toExternalForm());
        imageView.setFitHeight(24);
        imageView.setFitWidth(24);
        return imageView;
    }

    void setCurrentSong(Song song) {
        if (song != null) {
            songName.setText(song.getName());
            songArtist.setText(song.getArtist());
        }
    }

    @FXML
    void HandlePlayNextButtonAction(ActionEvent event) {

    }

    @FXML
    void HandlePlayPauseButtonAction(ActionEvent event) {

    }

    @FXML
    void HandlePlayPrevButtonAction(ActionEvent event) {

    }

    @FXML
    void HandleRepeatButtonAction(ActionEvent event) {

    }

}
