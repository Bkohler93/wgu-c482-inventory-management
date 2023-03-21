package com.example.inventory_manager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Brett Kohler
 * Contains helper methods that shorten tasks that use JavaFX API.
 * RUNTIME ERROR:
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

    /**
     *
     * @param min minimum integer value
     * @param max maximum integer value
     * @param inv current inventory number, needs to be between min/max
     * @return
     */
    public static boolean isInvValuesValid(int min, int max, int inv) {
        return max >= inv && min <= inv;
    }

    public static boolean confirmAction(String action, String msg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText(action);
        alert.setContentText(msg);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            return true;
        }
        return false;
    }
}
