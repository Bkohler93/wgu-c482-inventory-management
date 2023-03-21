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
 *
 */
public class ModifyPartController implements  Initializable {
    public Button saveBtn;
    public Button cancelBtn;
    @FXML
    private Text sourceTextLabel;
    @FXML
    private RadioButton inHouseRadBtn;
    @FXML
    private RadioButton outsourcedRadBtn;
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
    private TextField sourceTextField;

    /**
     * @param part the part being sent to the controller
     * RUNTIME ERROR: Exception in thread "JavaFX Application Thread" java.lang.RuntimeException: java.lang.reflect.InvocationTargetException
     *             This error occurred when trying to open the modifyPart form with some parts, but not all of them. The issue was happening
     *             with only the Outsourced products because I was casting all parts as InHouse inside the `sendPart()` method. To fix this
     *             I checked whether the part was InHouse or Outsourced first, and then proceeded with the code (lines 67-71).
     * FUTURE ENHANCEMENT:
     */
    public void sendPart(Part part) {
        idTextField.setText(Integer.toString(part.getId()));
        nameTextField.setText(part.getName());
        invTextField.setText(Integer.toString(part.getStock()));
        priceTextField.setText(Double.toString(part.getPrice()));
        maxTextField.setText(Integer.toString(part.getMax()));
        minTextField.setText(Integer.toString(part.getMin()));

        if (part instanceof InHouse) {
            setSource((InHouse) part);
        } else {
            setSource((Outsourced) part);
        }
    }

    /**
     * @param part the InHouse part being set
     */
    private void setSource(InHouse part) {
        sourceTextField.setText(Integer.toString(part.getMachineId()));
        sourceTextLabel.setText(IN_HOUSE_LABEL);
        inHouseRadBtn.setSelected(true);
        outsourcedRadBtn.setSelected(false);
    }

    /**
     * @param part the Outsourced part being set
     */
    private void setSource(Outsourced part) {
        sourceTextField.setText(part.getCompanyName());
        sourceTextLabel.setText(OUTSOURCED_LABEL);
        inHouseRadBtn.setSelected(false);
        outsourcedRadBtn.setSelected(true);
    }

    /**
     * @param event event propagated from user tapping on one of the InHouse or Outsourced radio buttons
     */
    public void onActionSourceRadBtn(ActionEvent event) {
        if (event.getSource().equals(inHouseRadBtn)) {
            inHouseRadBtn.setSelected(true);
            outsourcedRadBtn.setSelected(false);
            sourceTextLabel.setText(IN_HOUSE_LABEL);

        } else {
            inHouseRadBtn.setSelected(false);
            outsourcedRadBtn.setSelected(true);
            sourceTextLabel.setText(OUTSOURCED_LABEL);
        }
    }

    /**
     * @param url TODO
     * @param resourceBundle TODO
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idTextField.setDisable(true);
    }

    /**
     * @param actionEvent event propagated from user tapping on 'save' button
     */
    public void onActionSaveBtn(ActionEvent actionEvent) throws IOException {
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

            if (sourceTextLabel.getText().equals(OUTSOURCED_LABEL)) {
                partCompanyName = sourceTextField.getText();
                newPart = new Outsourced(partId, partName, partPrice, partStock, partMin, partMax, partCompanyName);
            } else {
                machineId = Integer.parseInt(sourceTextField.getText());
                newPart = new InHouse(partId, partName, partPrice, partStock, partMin, partMax, machineId);
            }

            Inventory.updatePart(partId, newPart);
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            FxHelpers.navigateTo("main.fxml", stage);
        } else {
            //TODO notify user there is an error in the form
        }
    }

    /**
     * @param actionEvent propagated when user taps on 'cancel' button
     * @throws IOException thrown by FxHelpers.navigateTo
     */
    public void onActionCancelBtn(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        FxHelpers.navigateTo("main.fxml", stage);
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

        if (sourceTextLabel.getText().equals(IN_HOUSE_LABEL)) {
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
