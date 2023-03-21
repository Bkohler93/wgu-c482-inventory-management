package com.example.inventory_manager.controllers;

import com.example.inventory_manager.FxHelpers;
import com.example.inventory_manager.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;


/**
 * Adds functionality to the UI so the user can manipulate fields and buttons in the Add Product form.
 * @author Brett Kohler
 * RUNTIME ERROR: Exception in thread "JavaFX Application Thread" java.lang.RuntimeException: java.lang.reflect.InvocationTargetException
 *                 This was caused by a typo in addProduct.fxml where the controller being linked to the form was spelled incorrectly
 *                 To fix this I fixed the typo in order for the controller to properly load with the form.
 * FUTURE ENHANCEMENT: Adding the option for users to upload products using CSV or other file. This would help
 *                      users when they are trying to add many products at once that have already been entered
 *                      on another system.
 */
public class AddProductController implements Initializable {
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField invTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField maxTextField;
    @FXML
    private TextField minTextField;
    @FXML
    private TableView<Part> selectedPartsTableView;
    @FXML
    private TableColumn<Part, Integer> selectedPartIdCol;
    @FXML
    private TableColumn<Part, String> selectedPartNameCol;
    @FXML
    private TableColumn<Part, Integer> selectedPartInvCol;
    @FXML
    private TableColumn<Part, Double> selectedPartPriceCol;
    @FXML
    private TextField searchPartTextField;
    @FXML
    private TableView<Part> partTableView;
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Integer> partInvCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    @FXML
    private TextField idTextField;

    /**
     * @param event created by JavaFX when user taps on 'cancel' button.
     * @throws IOException
     * Navigates back to 'main.fxml'
     */
    @FXML
    protected void onActionCancelBtn(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FxHelpers.navigateTo("main.fxml", stage);
    }

    // disables the ID text field and fills/sets up the TableViews
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idTextField.setText(Integer.toString(Inventory.generateProductId()));
        idTextField.setDisable(true);

        partTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        selectedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        selectedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        selectedPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        selectedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    /**
     * uses user-entered text to search for parts with matching ID or name that the user searched for.
     * @param event propagated from user pressing 'Enter' while focused on the part search text field.
     */
    @FXML
    void onActionSearchPartTxtFld(ActionEvent event) {
        ObservableList<Part> partsToView;
        int searchId;
        String searchText;

        if (searchPartTextField.getText().isBlank()) {
            partTableView.setItems(Inventory.getAllParts());
        } else {
            // use searchPartTextField to find matching parts based on ID (int) or name (string) entered
            try {
                searchId = Integer.parseInt(searchPartTextField.getText());
                partsToView = Inventory.getAllParts().filtered(part -> part.getId() == searchId);

                if (partsToView.isEmpty()) { displayPartSearchErrorAlert(); }
                else { partTableView.setItems(partsToView); }
            } catch (Exception e) {
                searchText = searchPartTextField.getText();
                partsToView = Inventory.getAllParts().filtered(part -> part.getName().contains(searchText));

                if (partsToView.isEmpty()) { displayPartSearchErrorAlert(); }
                else { partTableView.setItems(partsToView); }
            }
        }
    }

    /**
     * informs user there was an error searching for a part and waits for user to close alert.
     */
    private void displayPartSearchErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Search error");
        alert.setContentText("No part with that ID or name can be found.");
        alert.showAndWait();
    }

    /**
     * selects part that is selected in partTableView and adds the part to the selectedPartsTableView.
     * @param event propagated when user taps 'Add' button beneath partTableView
     */
    @FXML
    void onActionAddPartBtn(ActionEvent event) {
        List<Part> associatedParts; // use List ADT to allow for modification
        Part partToAdd = partTableView.getSelectionModel().getSelectedItem();

        if (!Objects.isNull(partToAdd)) {
            associatedParts = selectedPartsTableView.getItems();

            if (!associatedParts.contains(partToAdd)) {
                associatedParts.add(partToAdd);

                selectedPartsTableView.setItems(FXCollections.observableList(associatedParts));
            }
        }
    }

    /**
     * Selects part that is selected in selectedPartsTableView and removes the part from the same list.
     * @param event propagated when user taps on 'Remove Associated Part' button
     */
    @FXML
    void onActionRemoveAssociatedPartBtn(ActionEvent event) {
        List<Part> associatedParts; // use List ADT to allow for list modification
        Part partToRemove = selectedPartsTableView.getSelectionModel().getSelectedItem();

        associatedParts = selectedPartsTableView.getItems();

        if (FxHelpers.confirmAction("Delete Alert", "Are you sure you want to remove the associated product?")) {
            associatedParts.remove(partToRemove);
            selectedPartsTableView.setItems(FXCollections.observableList(associatedParts));
        }
    }

    @FXML
    void onActionSaveBtn(ActionEvent event) throws IOException {
        String productName;
        double productPrice;
        int productId, productStock, productMin, productMax;
        Product newProduct;

        if (areFieldsValid()) {
            productId = Integer.parseInt(idTextField.getText());
            productName = nameTextField.getText();
            productPrice = Double.parseDouble(priceTextField.getText());
            productStock = Integer.parseInt(invTextField.getText());
            productMin = Integer.parseInt(minTextField.getText());
            productMax = Integer.parseInt(maxTextField.getText());
            newProduct = new Product(productId, productMin, productMax, productName, productPrice, productStock);

            if (Inventory.isInvValuesInvalid(productMin, productMax, productStock)) {
                displayInputValidationError("Inv field must be between Min and Max. Min must be less than Max. Max must be greater than Min.");
                return;
            }

            List<Part> associatedParts = selectedPartsTableView.getItems();

            for (Part part : associatedParts) {
                newProduct.addAssociatedPart(part);
            }

            Inventory.addProduct(newProduct);
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FxHelpers.navigateTo("main.fxml", stage);
        }
    }

    /**
     * checks if all text fields are valid
     * @return true if valid, false if not.
     */
    private boolean areFieldsValid() {
        try {
            Integer.parseInt(idTextField.getText());
        } catch (NumberFormatException e) {
            displayInputValidationError("ID field needs to be a whole number");
            return false;
        }
        try {
            Double.parseDouble(priceTextField.getText());
        } catch (NumberFormatException e) {
            displayInputValidationError("Price/Cost must be a valid Dollar amount formatted like '0.99'");
            return false;
        }
        try {
            Integer.parseInt(invTextField.getText());
        } catch (NumberFormatException e) {
            displayInputValidationError("Inventory field needs to be a whole number");
            return false;
        }
        try {
            Integer.parseInt(minTextField.getText());
            Integer.parseInt(maxTextField.getText());
        } catch (NumberFormatException e) {
            displayInputValidationError("Max and Min Inventory fields needs to be a whole number");
            return false;
        }

        return true;
    }

    /**
     * informs user there was an error searching for a part and waits for user to close alert.
     */
    private void displayInputValidationError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Input(s) in Form");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
