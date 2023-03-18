package com.example.inventory_manager.controllers;

import com.example.inventory_manager.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Brett Kohler
 * Contains helper methods that shorten tasks that use JavaFX API.
 */
public class FxHelpers {

    /**
     * @param resourceID the file id of the fxml file to change to
     * @param stage the stage that will display the new scene
     * @throws IOException FXMLLoader throws an exception if unable to load scene
     */
    public static void navigateTo(String resourceID, Stage stage) throws IOException {
        Parent scene = FXMLLoader.load(Objects.requireNonNull(App.class.getResource(resourceID)));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
