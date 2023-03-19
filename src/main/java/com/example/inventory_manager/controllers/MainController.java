package com.example.inventory_manager.controllers;

import com.example.inventory_manager.App;
import com.example.inventory_manager.FxHelpers;
import com.example.inventory_manager.models.Inventory;
import com.example.inventory_manager.models.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author Brett Kohler
 * Controls the UI on the main inventory screen
 */
public class MainController implements Initializable {
    @FXML
    private Button exitBtn;
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
    @FXML
    private Button addPartBtn;

    @FXML
    private Button modifyPartBtn;

    @FXML
    private Button deletePartBtn;

    @FXML
    private Button addProductBtn;

    @FXML
    private Button modifyProductBtn;

    @FXML
    private Button deleteProductBtn;

    /**
     * @param event the event that resulted from user pressing the 'add' button in the part pane
     * @throws IOException thrown by 'FxHelpers.navigateTo'
     */
    @FXML
    protected void onActionAddPartBtn(ActionEvent event) throws IOException {
        System.out.println("=== Pressed add part button");
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
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * @param event the event that resulted from user pressing the 'delete' button in the part pane
     */
    public void onActionDeletePartBtn(ActionEvent event) {
        System.out.println("=== Pressed delete part button");

        /* TODO
        delete selected part from Parts TableView or displays a descriptive error message in the UI/dialog box
        if a part is not deleted
         */
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Delete Part Error");
        alert.setContentText("Part was not deleted. Select a part to delete from the Parts Table.");
        alert.showAndWait();
    }

    /**
     * @param event the event that resulted from user tapping the 'add' button in the product pane
     * @throws IOException thrown by 'FxHelpers.navigateTo'
     */
    @FXML
    protected void onActionAddProductBtn(ActionEvent event) throws IOException {
        System.out.println("=== Pressed add product button");

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FxHelpers.navigateTo("addProduct.fxml", stage);
    }

    /**
     * @param event the event that resulted from user pressing the 'modify' button in the product pane
     * @throws IOException thrown by 'FxHelpers.navigateTo'
     */
    @FXML
    protected void onActionModifyProductBtn(ActionEvent event) throws IOException {
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource("modifyProduct.fxml"));
//        loader.load();
//
//        Modify

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        FxHelpers.navigateTo("modifyProduct.fxml", stage);
    }

    /**
     * @param event the event that resulted from user pressing the 'delete' button in the product pane
     */
    public void onActionDeleteProductBtn(ActionEvent event) {
        System.out.println("=== Pressed delete product button");

        /* TODO
        delete selected product from Products TableView or displays a descriptive error message in the UI/dialog box
        if a part is not deleted
         */
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Delete Product Error");
        alert.setContentText("Product was not deleted. Select a product to delete from the Product Table.");
        alert.showAndWait();
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
        //TODO fill in Product table with Inventory.allProducts
    }
}
