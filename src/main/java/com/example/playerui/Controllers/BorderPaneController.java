package com.example.playerui.Controllers;

import com.example.playerui.DataSingleton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class BorderPaneController implements Initializable {
    @FXML
    private BorderPane mainPane;

    public BorderPane getMainPane() {
        return mainPane;
    }
    //public void setMainPane(BorderPane pane) { mainPane = pane; }
    public void setPaneBottom(Pane bottomPane) {mainPane.setBottom(bottomPane);}
    public void setPaneCenter(Pane centerPane) {mainPane.setCenter(centerPane);}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
