module com.example.inventory_manager {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.inventory_manager to javafx.fxml;
    opens com.example.inventory_manager.controllers to javafx.fxml;
    opens com.example.inventory_manager.models to javafx.base;
    exports com.example.inventory_manager;
}