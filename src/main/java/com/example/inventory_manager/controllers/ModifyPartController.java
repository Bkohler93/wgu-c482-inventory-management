package com.example.inventory_manager.controllers;

import com.example.inventory_manager.FxHelpers;
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

import static com.example.inventory_manager.Constants.IN_HOUSE_LABEL;
import static com.example.inventory_manager.Constants.OUTSOURCED_LABEL;

//TODO
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
        return true;
        //TODO error checking on input fields
    }
}
