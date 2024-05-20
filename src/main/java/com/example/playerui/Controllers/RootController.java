package com.example.playerui.Controllers;

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

    public BorderPane getMainPane() {
        return mainPane;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ImageView playPauseImage = new ImageView(Objects.requireNonNull(getClass().getResource("/images/play.png")).toExternalForm());
        playPauseImage.setFitHeight(24);
        playPauseImage.setFitWidth(24);
        playPauseButtons.setGraphic(playPauseImage);

        ImageView playPrevImage = new ImageView(Objects.requireNonNull(getClass().getResource("/images/prev.png")).toExternalForm());
        playPrevImage.setFitHeight(24);
        playPrevImage.setFitWidth(24);
        playPrevButton.setGraphic(playPrevImage);

        ImageView playNextImage = new ImageView(Objects.requireNonNull(getClass().getResource("/images/next.png")).toExternalForm());
        playNextImage.setFitHeight(24);
        playNextImage.setFitWidth(24);
        playNextButton.setGraphic(playNextImage);

        ImageView repeatImage = new ImageView(Objects.requireNonNull(getClass().getResource("/images/repeat.png")).toExternalForm());
        repeatImage.setFitHeight(24);
        repeatImage.setFitWidth(24);
        repeatButton.setGraphic(repeatImage);
//        try {
//            Parent page = FXMLLoader.load(getClass().getResource("listPlaylists-view.fxml"));
//            mainPane.setCenter(page);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        try {
//            mainPane.setCenter(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("listPlaylists-view"))));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
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
