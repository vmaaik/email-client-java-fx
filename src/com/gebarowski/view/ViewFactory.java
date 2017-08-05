package com.gebarowski.view;


//return different scens for different Layouts

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class ViewFactory {

    public Scene getMainScene() {
        Pane pane = null;
        try {
            pane = FXMLLoader.load(getClass().getResource("MainLayout.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(pane);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        return scene;
    }

    public Scene getEMailContextMenuScene() {
        Pane pane = null;
        try {
            pane = FXMLLoader.load(getClass().getResource("EmailContextMenuLayout.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(pane);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        return scene;
    }


}
