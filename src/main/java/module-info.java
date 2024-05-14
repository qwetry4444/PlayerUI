module com.example.playerui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens com.example.playerui to javafx.fxml;
    opens com.example.MusicPlayer to javafx.base;


    exports com.example.playerui;
    exports com.example.playerui.Controllers;
    opens com.example.playerui.Controllers to javafx.fxml;
}