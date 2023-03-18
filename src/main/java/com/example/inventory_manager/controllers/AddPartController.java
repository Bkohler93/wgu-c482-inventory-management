package com.example.inventory_manager.controllers;

import com.example.inventory_manager.models.InHouse;
import com.example.inventory_manager.models.Inventory;
import com.example.inventory_manager.models.Outsourced;
import com.example.inventory_manager.models.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Brett Kohler
 */
public class AddPartController implements Initializable {
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private TextField sourceTextField;
    @FXML
    private TextField minTextField;
    @FXML
    private TextField maxTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField invTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField idTextField;
    @FXML
    private RadioButton inHouseRadioBtn;

    @FXML
    private RadioButton outsourcedRadioBtn;

    @FXML
    private Text inHouseOutsourcedLabel;

    private Stage stage;
    final private String IN_HOUSE_LABEL = "Machine ID";
    final private String OUTSOURCED_LABEL = "Company Name";

    /**
     * @param event event populated when user taps on either 'In-House' or 'Outsourced' radio buttons
     * Deselects the radio button opposite to what the user tapped on, and updates the sourceTextLabel
     * to 'Machine ID' for an In-House part or 'Company Name' for an Outsourced part
     */
    @FXML
    void onActionRadioBtn(ActionEvent event) {
        RadioButton pressedBtn = (RadioButton) event.getSource();
        if (pressedBtn.equals(inHouseRadioBtn)) {
            outsourcedRadioBtn.setSelected(false);
            inHouseOutsourcedLabel.setText(IN_HOUSE_LABEL);
        } else {
            inHouseRadioBtn.setSelected(false);
            inHouseOutsourcedLabel.setText(OUTSOURCED_LABEL);
        }
    }

    /**
     * @param url TODO
     * @param resourceBundle TODO
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inHouseRadioBtn.setSelected(true);
        idTextField.setDisable(true);
        idTextField.setText(String.valueOf(Inventory.generatePartId()));
    }

    /**
     * @param event populated when user taps on 'cancel' button
     * @throws IOException
     * Navigates back to 'main.fxml'
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FxHelpers.navigateTo("main.fxml", stage);
    }

    /**
     * @param event event that resulted from user tapping 'save' button
     * Checks if all input fields are valid. If they are not valid, user is notified of their mistake.
     * If it is valid a new part is created and user is returned to main form.
     */
    @FXML
    void onActionSaveBtn(ActionEvent event) throws IOException {
        String partName, partCompanyName;
        double partPrice;
        int partId, partStock, partMin, partMax, machineId;
        Part newPart;

        if (areFieldsValid()) {
            partId = Integer.parseInt(idTextField.getText());
            partName = nameTextField.getText();
            partPrice = Double.parseDouble(priceTextField.getText());
            partStock = Integer.parseInt(invTextField.getText());
            partMin = Integer.parseInt(minTextField.getText());
            partMax = Integer.parseInt(maxTextField.getText());

            if (inHouseOutsourcedLabel.getText().equals(OUTSOURCED_LABEL)) {
                partCompanyName = sourceTextField.getText();
                newPart = new Outsourced(partId, partName, partPrice, partStock, partMin, partMax, partCompanyName);
            } else {
                machineId = Integer.parseInt(sourceTextField.getText());
                newPart = new InHouse(partId, partName, partPrice, partStock, partMin, partMax, machineId);
            }

            Inventory.addPart(newPart);
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FxHelpers.navigateTo("main.fxml", stage);
        } else {
            //TODO notify user there is an error in the form
        }
    }

    /**
     * @return true if all fields are valid, false if they are not.
     */
    private boolean areFieldsValid() {
        return true;
        //TODO error checking on input fields
    }
}
