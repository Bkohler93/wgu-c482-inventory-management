module com.example.inventory_manager {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.inventory_manager to javafx.fxml;
    exports com.example.inventory_manager;
}