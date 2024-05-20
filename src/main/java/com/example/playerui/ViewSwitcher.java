package com.example.playerui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Objects;

public class ViewSwitcher {

    public static Scene scene;
    public static BorderPane mainPane;

    public static void setScene(Scene scene){
        ViewSwitcher.scene = scene;
    }

    public static void setMainPane(BorderPane pane){
        ViewSwitcher.mainPane = pane;
    }

    public static void switchTo(View view){
        if (scene == null){
            return;
        }
        try {
            Parent page = FXMLLoader.load(ViewSwitcher.class.getResource(view.getFileName()));
            mainPane.setCenter(page);

            //scene.setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
