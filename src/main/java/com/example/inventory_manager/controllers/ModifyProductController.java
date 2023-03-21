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
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Adds functionality to the UI so the user can manipulate fields and buttons in the Modify Product form.
 * @author Brett Kohler
 * LOGICAL ERROR: After closing the window or pressing 'Cancel' the program would return to the main form, but then if closing that
 *              the program would display the Modify Product form again and the user would need to re-exit the form before
 *              having to close out of the main form again. This occurred because I was creating a new stage to display the main form
 *              after closing the modify product form. To fix this I deleted the code that created a new stage and, instead,
 *              I fired a WINDOW_CLOSE_EVENT to trigger the 'onCloseRequest' I configured
 *              in the 'setOnCloseRequest' from when the user was original directed from main to the modify product form.
 * FUTURE ENHANCEMENT: Include an extra attribute for the Product class (byte[] or blob) to store image data and a button
 *                      to view a product's image. This would
 *                      provide users with a visual description of each product as well.
 */
public class ModifyProductController implements Initializable  {
    @FXML
    private TextField idTextField;
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
    private TableView<Part> associatedPartTableView;
    @FXML
    private TableColumn<Part, Integer> associatedPartIdCol;
    @FXML
    private TableColumn<Part, String> associatedPartNameCol;
    @FXML
    private TableColumn<Part, Integer> associatedPartInvCol;
    @FXML
    private TableColumn<Part, Double> associatedPartPriceCol;
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

    /**
     * sets field values for form input fields by copying attributes from input product. Fills in associated part table.
     * @param product product to modify in modify product form
     */
    public void sendProduct(Product product) {
        idTextField.setText(Integer.toString(product.getId()));
        nameTextField.setText(product.getName());
        invTextField.setText(Integer.toString(product.getStock()));
        priceTextField.setText(Double.toString(product.getPrice()));
        maxTextField.setText(Integer.toString(product.getMax()));
        minTextField.setText(Integer.toString(product.getMin()));

        associatedPartTableView.setItems(product.getAllAssociatedParts());
        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        associatedPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    /**
     * searches for parts with matching ID or similar string searched for
     * @param actionEvent event propagated from user pressing 'Enter' while focused on part search text field
     */
    public void onActionSearchPartTextField(ActionEvent actionEvent) {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idTextField.setDisable(true);

        partTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        associatedPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
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

    @FXML
    void onActionAddAssociatedPart(ActionEvent event) {
        List<Part> associatedParts;
        Part partToAdd = partTableView.getSelectionModel().getSelectedItem();

        if (!Objects.isNull(partToAdd)) {
            associatedParts = associatedPartTableView.getItems();

            if (!associatedParts.contains(partToAdd)) {
                associatedParts.add(partToAdd);

                associatedPartTableView.setItems(FXCollections.observableList(associatedParts));
            }
        }
    }

    @FXML
    void onActionSaveBtn(ActionEvent event) {
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

            List<Part> associatedParts = associatedPartTableView.getItems();

            for (Part part : associatedParts) {
                newProduct.addAssociatedPart(part);
            }
            int indexToUpdate = Inventory.getProductIndex(productId);
            Inventory.updateProduct(indexToUpdate, newProduct);
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        }
    }

    @FXML
    void onActionCancelBtn(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
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

    @FXML
    void onActionRemoveAssociatedPartBtn(ActionEvent event) {
        List<Part> associatedParts; // use List ADT to allow for list modification
        Part partToRemove = associatedPartTableView.getSelectionModel().getSelectedItem();

        associatedParts = associatedPartTableView.getItems();
        if (FxHelpers.confirmAction("Delete Alert", "Are you sure you want to remove the associated product?")) {
            associatedParts.remove(partToRemove);
            associatedPartTableView.setItems(FXCollections.observableList(associatedParts));
        }
    }
}
