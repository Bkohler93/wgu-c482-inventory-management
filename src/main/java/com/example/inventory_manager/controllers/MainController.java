package com.example.inventory_manager.controllers;

import com.example.inventory_manager.App;
import com.example.inventory_manager.FxHelpers;
import com.example.inventory_manager.models.Inventory;
import com.example.inventory_manager.models.Part;
import com.example.inventory_manager.models.Product;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controls the UI on the main form. Populates part and product tables.
 * @author Brett Kohler
 * Controls the UI on the main inventory screen
 * LOGICAL ERROR: After setting up fx:id's and writing code to place parts into the TableView, the program
 *                  wouldn't display the parts in the table. I used print statements to verify that the Inventory
 *                  had a populated ObservableList and it did, and the rows in the table would even become selectable
 *                  by clicking on a row, but none of the data was there. After returning to the webinar, I realized I
 *                  skipped the step when I needed to set the PropertyValue for each column. After writing the code to do
 *                  this the table displayed values as planned.
 * FUTURE ENHANCEMENT: Add a button to expand the view for each of the tables. All of the data (Company name/ machineID for example_
 * is not being shown in the tables, and adding buttons to expand these views would allow users to view all data for all parts/products.
 */
public class MainController implements Initializable {
    @FXML
    public TextField productSearchTextField;
    @FXML
    private TextField partSearchTextField;
    @FXML
    private TableView<Product> productTableView;
    @FXML
    private TableColumn<Product, Integer> productIdCol;
    @FXML
    private TableColumn<Product, String> productNameCol;
    @FXML
    private TableColumn<Product, Integer> productInvCol;
    @FXML
    private TableColumn<Product, Double> productPriceCol;
    @FXML
    private TableView<Part> partTableView;
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Integer> partInvCol;
    @FXML
    private TableColumn<Part, Double> partCostCol;
    Stage stage;

    /**
     * @param event the event that resulted from user pressing the 'add' button in the part pane
     * @throws IOException thrown by 'FxHelpers.navigateTo'
     */
    @FXML
    protected void onActionAddPartBtn(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FxHelpers.navigateTo("addPart.fxml", stage);
    }

    /**
     * @param event the event that resulted from user pressing the 'modify' button in the part pane
     * @throws IOException thrown by 'FxHelpers.navigateTo'
     */
    @FXML
    protected void onActionModifyPartBtn(ActionEvent event) throws IOException {
        Part partToSend = partTableView.getSelectionModel().getSelectedItem();

        if (!Objects.isNull(partToSend)) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("modifyPart.fxml"));
            loader.load();

            ModifyPartController modifyPartController = loader.getController();
            modifyPartController.sendPart(partTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FxHelpers.navigateToWithData(stage, loader);
        }
    }

    /**
     * @param event the event that resulted from user pressing the 'delete' button in the part pane
     */
    public void onActionDeletePartBtn(ActionEvent event) {
        Part partToDelete = partTableView.getSelectionModel().getSelectedItem();

        if (!Objects.isNull(partToDelete)) {
            if (FxHelpers.confirmAction("Delete Alert", "Are you sure you want to delete the selected part?")) {
                Inventory.deletePart(partToDelete);
                partTableView.setItems(Inventory.getAllParts());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Delete Part Error");
            alert.setContentText("Part was not deleted. Select a part to delete from the Parts Table.");
            alert.showAndWait();
        }
    }

    /**
     * @param event the event that resulted from user tapping the 'add' button in the product pane
     * @throws IOException thrown by 'FxHelpers.navigateTo'
     */
    @FXML
    protected void onActionAddProductBtn(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FxHelpers.navigateTo("addProduct.fxml", stage);
    }

    /**
     * @param event the event that resulted from user pressing the 'modify' button in the product pane
     * @throws IOException thrown by 'FxHelpers.navigateTo'
     */
    @FXML
    protected void onActionModifyProductBtn(ActionEvent event) throws IOException {
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();

        if (!Objects.isNull(selectedProduct)) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("modifyProduct.fxml"));
            loader.load();

            ModifyProductController modifyProductController = loader.getController();
            modifyProductController.sendProduct(productTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FxHelpers.navigateToWithData(stage, loader);
        }
    }

    /**
     * @param event the event that resulted from user pressing the 'delete' button in the product pane
     */
    public void onActionDeleteProductBtn(ActionEvent event) {
        Product productToDelete = productTableView.getSelectionModel().getSelectedItem();

        if (!Objects.isNull(productToDelete)) {

            if (productToDelete.hasAssociatedParts()) {
                displayDeleteProductErrorAlert("Cannot delete product with associated parts.");
                return;
            }
            if (FxHelpers.confirmAction("Delete Alert", "Delete selected product?")) {
                Inventory.deleteProduct(productToDelete);
                productTableView.setItems(Inventory.getAllProducts());
            }
        } else {
            displayDeleteProductErrorAlert("Product was not deleted. Select a product to delete from the Product Table.");
        }
    }

    /**
     * @param event the event caused by user tapping the 'exit' button
     * Quits the application
     */
    @FXML
    protected void onActionExitBtn(ActionEvent event) {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * @param url TODO
     * @param resourceBundle TODO
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        productTableView.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    @FXML
    void onActionPartSearchTextField(ActionEvent event) {
        ObservableList<Part> partsToView;
        int searchId;
        String searchText;

        if (partSearchTextField.getText().isBlank()) {
            partTableView.setItems(Inventory.getAllParts());
        } else {
            try {
                searchId = Integer.parseInt(partSearchTextField.getText());
                Part partToView = Inventory.lookupPart(searchId);

                if (partToView == null) { displayPartSearchErrorAlert(); }
                else { partTableView.getSelectionModel().select(partToView); }
            } catch (Exception e) {
                searchText = partSearchTextField.getText().toLowerCase();
                partsToView = Inventory.lookupPart(searchText);

                if (partsToView.isEmpty()) { displayPartSearchErrorAlert(); }
                else if (partsToView.size() == 1) { partTableView.getSelectionModel().select(partsToView.get(0)); }
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

    private void displayDeleteProductErrorAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Delete Product Error");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    /**
     * informs user there was an error searching for a product and waits for user to close alert.
     */
    private void displayProductSearchErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Search error");
        alert.setContentText("No product with that ID or name can be found.");
        alert.showAndWait();
    }

    @FXML
    void onActionProductSearchTextField(ActionEvent event) {
        ObservableList<Product> productsToView;
        int searchId;
        String searchText;

        if (productSearchTextField.getText().isBlank()) {
            productTableView.setItems(Inventory.getAllProducts());
        } else {
            try {
                searchId = Integer.parseInt(productSearchTextField.getText());
                Product productToView = Inventory.lookupProduct(searchId);
                if (productToView == null) { displayProductSearchErrorAlert(); }
                else { productTableView.getSelectionModel().select(productToView); }
            } catch (Exception e) {
                searchText = productSearchTextField.getText().toLowerCase();
                productsToView = Inventory.lookupProduct(searchText);
                if (productsToView.isEmpty()) { displayProductSearchErrorAlert(); }
                else if (productsToView.size() == 1) { productTableView.getSelectionModel().select(productsToView.get(0)); }
                else { productTableView.setItems(productsToView); }
            }
        }
    }
}
