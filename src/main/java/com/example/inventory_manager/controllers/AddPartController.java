package com.example.inventory_manager.controllers;

import com.example.inventory_manager.FxHelpers;
import com.example.inventory_manager.models.InHouse;
import com.example.inventory_manager.models.Inventory;
import com.example.inventory_manager.models.Outsourced;
import com.example.inventory_manager.models.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.inventory_manager.Constants.IN_HOUSE_LABEL;
import static com.example.inventory_manager.Constants.OUTSOURCED_LABEL;

/**
 * @author Brett Kohler
 * LOGICAL ERROR: On initial load, neither the "InHouse" nor "Outsourced" radio buttons were selected, yet the
 *                input label for if the part was outsourced or inhouse was properly labeled. In order to load the form
 *                with the correct button being selected, i included the code `inHouseRadioBtn.setSelected(true);` in the
 *                `initialize()` overloaded method.
 * FUTURE ENHANCEMENT: Include an option to load in parts from a CSV or other file format. This would allow
 *                     users to easily upload bulk amounts of parts that may have been saved on another system.
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

            if (!FxHelpers.isInvValuesValid(partMin, partMax, partStock)) {
                displayInputValidationError("Inv field must be between Min and Max. Min must be less than Max. Max must be greater than Min.");
                return;
            }

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

        if (inHouseOutsourcedLabel.getText().equals(IN_HOUSE_LABEL)) {
            try {
                Integer.parseInt(sourceTextField.getText());
            } catch (NumberFormatException e) {
                displayInputValidationError("Machine ID field needs to be a whole number");
                return false;
            }
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
