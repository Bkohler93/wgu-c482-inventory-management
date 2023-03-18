package com.example.inventory_manager.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Brett Kohler
 */
public class AddProductController {
    private Stage stage;

    @FXML
    private Button cancelBtn;

    /**
     * @param event created by JavaFX when user taps on 'cancel' button.
     * @throws IOException
     * Navigates back to 'main.fxml'
     */
    @FXML
    protected void onActionCancelBtn(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FxHelpers.navigateTo("main.fxml", stage);
    }
}
