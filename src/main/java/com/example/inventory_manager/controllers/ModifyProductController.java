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

//TODO
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

            List<Part> associatedParts = associatedPartTableView.getItems();

            for (Part part : associatedParts) {
                newProduct.addAssociatedPart(part);
            }
            Inventory.updateProduct(productId, newProduct);
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FxHelpers.navigateTo("main.fxml", stage);
        } else {
            //TODO notify user there is an error in the form
        }
    }

    @FXML
    void onActionCancelBtn(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FxHelpers.navigateTo("main.fxml", stage);
    }

    /**
     * @return true if all fields are valid, false if they are not.
     */
    private boolean areFieldsValid() {
        return true;
        //TODO error checking on input fields
    }

    @FXML
    void onActionRemoveAssociatedPartBtn(ActionEvent event) {
        List<Part> associatedParts; // use List ADT to allow for list modification
        Part partToRemove = associatedPartTableView.getSelectionModel().getSelectedItem();

        associatedParts = associatedPartTableView.getItems();

        associatedParts.remove(partToRemove);
        associatedPartTableView.setItems(FXCollections.observableList(associatedParts));
    }
}
