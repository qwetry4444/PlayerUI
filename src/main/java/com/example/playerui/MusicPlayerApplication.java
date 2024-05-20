package com.example.playerui;

import com.example.playerui.Controllers.RootController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MusicPlayerApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("root.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        ViewSwitcher.setScene(scene);

        RootController controller = loader.getController();
        ViewSwitcher.setMainPane(controller.getMainPane());

        stage.setTitle("Music player");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icon.png"))));
        stage.setScene(scene);
        stage.show();

        ViewSwitcher.switchTo(View.LIST_PLAYLISTS);

    }

    public static void main(String[] args) {
        launch(args);
    }
}