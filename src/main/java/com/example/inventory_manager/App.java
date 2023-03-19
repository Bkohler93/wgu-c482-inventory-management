package com.example.inventory_manager;

import com.example.inventory_manager.models.Inventory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Brett Kohler
 * ERROR I SOLVED:
 * Mar 17, 2023 4:34:22 PM javafx.scene.control.cell.PropertyValueFactory getCellDataReflectively
 * WARNING: Can not retrieve property 'id' in PropertyValueFactory: javafx.scene.control.cell.PropertyValueFactory@296ac8ac with provided class type: class com.example.inventory_manager.models.InHouse
 *      -- to solve I went into 'module-info.java' and opened the '...models' package to 'javafx.base'
 */
public class App extends Application {

    /**
     * @param stage where new scene is loaded onto
     * @throws IOException thrown by 'FXMLLoader'
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main.fxml"));
        Inventory.loadInitialData();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Inventory Manager");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
