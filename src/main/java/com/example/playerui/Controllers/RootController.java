package com.example.playerui.Controllers;

import com.example.MusicPlayer.Playlist;
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
import javafx.scene.media.Media;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.util.Duration;

import static jdk.jfr.internal.Utils.formatDuration;


public class RootController implements Initializable {

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

    @FXML
    private Label songDuration;

    DataSingleton data;


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

    public void setCurrentSong(Song song, int playlistId) {
        if (song != null) {
            songName.setText(song.getName());
            songArtist.setText(song.getArtist());
            data.musicPlayer().playPlaylistSong(playlistId, song.getId());
            //songDuration.setText(data.musicPlayer().getPlayer().getStopTime().toString());
            data.musicPlayer().getPlayer().setOnReady(() -> {
                Duration duration = data.musicPlayer().getPlayer().getMedia().getDuration();
                songDuration.setText(formatDuration(duration));
            });
        }
    }
    public String formatDuration(Duration time){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((int)time.toMinutes());
        stringBuilder.append(" :  ");
        stringBuilder.append((int)time.toSeconds() % 60);
        return stringBuilder.toString();
    }

    @FXML
    void HandlePlayNextButtonAction(ActionEvent event) throws IOException {
        int currentSongId = data.musicPlayer().getCurrentSongId();
        int nextSongId = data.musicPlayer().getCurrentPlaylist().getNextSongId(currentSongId);
        setCurrentSong(data.musicPlayer().getSongById(nextSongId), data.musicPlayer().getCurrentPlaylistId());
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/playerui/playlist.fxml"));
        //Parent root = loader.load();
        //PlaylistController playlistController = loader.getController();

        //playlistController.tableSelectNext();
        //data.musicPlayer().playNext();
    }



    @FXML
    void HandlePlayPauseButtonAction(ActionEvent event) {
        data.musicPlayer().playOrStopSong();
    }

    @FXML
    void HandlePlayPrevButtonAction(ActionEvent event) throws IOException {
        int currentSongId = data.musicPlayer().getCurrentSongId();
        int prevSongId = data.musicPlayer().getCurrentPlaylist().getPrevSongId(currentSongId);
        setCurrentSong(data.musicPlayer().getSongById(prevSongId), data.musicPlayer().getCurrentPlaylistId());

        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/playerui/playlist.fxml"));
        //Parent root = loader.load();
        //PlaylistController playlistController = loader.getController();

        //playlistController.tableSelectPrev();
        //data.musicPlayer().playNext();
    }

    @FXML
    void HandleRepeatButtonAction(ActionEvent event) {
        setCurrentSong(data.musicPlayer().getCurrentSong(), data.musicPlayer().getCurrentPlaylistId());
    }

}
