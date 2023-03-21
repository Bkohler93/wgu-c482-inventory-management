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
 * RUNTIME ERROR: no runtime errors, this class was a refactor from methods developed in controllers
 */
public class FxHelpers {

    /**
     * navigates to a form and sets up redirect on close request back to main form
     * @param resourceId the name of the fxml file to load
     * @param stage the stage the main form is the scene for
     * @throws IOException thrown when resource with resourceId doesn't exist
     */
    public static void navigateTo(String resourceId, Stage stage) throws IOException {
        Parent scene = FXMLLoader.load(Objects.requireNonNull(App.class.getResource(resourceId)));
        stage.hide();
        Stage newStage = new Stage();
        newStage.setScene(new Scene(scene));
        newStage.setOnCloseRequest(windowEvent -> stage.show());
        newStage.show();
    }

    /**
     * open a new form in a new window so that when the new form's window is exited, the user is returned
     * to the main form.
     * @param stage TODO
     * @param loader the FXMLLoader with loaded resource
     */
    public static void navigateToWithData(Stage stage, FXMLLoader loader) {
        Parent scene = loader.getRoot();
        stage.hide();
        Stage newStage = new Stage();
        newStage.setScene(new Scene(scene));
        newStage.setOnCloseRequest(windowEvent -> stage.show());
        newStage.show();
    }



    /**
     * Opens an alert dialog and waits for user to confirm or cancel the pending action
     * @param action describes what action is about to take place
     * @param msg the question to ask the user
     * @return true if user confirmed action or false if user cancelled
     */
    public static boolean confirmAction(String action, String msg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText(action);
        alert.setContentText(msg);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}
