package com.example.inventory_manager.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brett Kohler
 */
public class Inventory {
    final private static ObservableList<Part> allParts = FXCollections.observableList(new ArrayList<>());
    final private static ObservableList<Product> allProducts = FXCollections.observableList(new ArrayList<>());

    /**
     * @param newPart part to add
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * @param newProduct product to add
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * @param partId id of part to look up
     * @return the matching part or null
     */
    public static Part lookupPart(int partId) {
        for (Part part : allParts) {
            if (part.getId() == partId) return part;
        }
        return null;
    }

    /**
     * @param productId id of product to lookup
     * @return the matching product or null
     */
    public static Product lookupProduct(int productId) {
        for (Product product: allProducts) {
            if (product.getId() == productId) return product;
        }
        return null;
    }

    /**
     * @param partName part name to lookup
     * @return list of parts matching the name
     */
    public static ObservableList<Part> lookupPart(String partName) {
        List<Part> matchingParts = new ArrayList<>();
        ObservableList<Part> matchingPartObservableList = FXCollections.observableList(matchingParts);

        for (Part part : allParts) {
            if (part.getName().contains(partName)) matchingPartObservableList.add(part);
        }
        return matchingPartObservableList;
    }

    /**
     * @param productName product name to look up
     * @return list of products matching product name
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        List<Product> matchingProducts = new ArrayList<>();
        ObservableList<Product> matchingProductObservableList = FXCollections.observableList(matchingProducts);

        for (Product product : allProducts) {
            if (product.getName().contains(productName)) matchingProductObservableList.add(product);
        }
        return matchingProductObservableList;
    }

    /**
     * @param index index of part to update
     * @param selectedPart Part object to update old index
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.remove(index);
        allParts.add(index, selectedPart);
    }

    /**
     * @param index index of product to update
     * @param newProduct Product object to insert
     */
    public static void updateProduct(int index, Product newProduct) { allProducts.add(index, newProduct); }

    /**
     * @param selectedPart part to delete
     * @return true if part was deleted or false if not found
     */
    public static boolean deletePart(Part selectedPart) {
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        }
        return false;
    }

    /**
     * @param selectedProduct product to delete
     * @return true if product was deleted or false if not found
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        }
        return false;
    }

    /**
     * @return list of all Part instances in allParts list
     */
    public static ObservableList<Part> getAllParts() { return allParts; }

    /**
     * @return list of all Product instances in allProducts list
     */
    public static ObservableList<Product> getAllProducts() { return allProducts; }

    /**
     * @return a unique identifier to identify the part
     */
    public static int generatePartId() {
        return allParts.size();
    }

    /**
     * places dummy data into tables
     */
    public static void loadInitialData() {
        addPart(new Outsourced(generatePartId(), "wheel", 3.49, 10, 2, 20, "Peter's Parts"));
        addPart(new Outsourced(generatePartId(), "screw", 0.79, 2000, 100, 10000, "Peter's Parts"));
        addPart(new Outsourced(generatePartId(), "ball-bearing", 3.29, 340, 100, 1000, "Peter's Parts"));
        addPart(new Outsourced(generatePartId(), "rubber pad", 6.20, 46, 10, 100, "Textiles R Us"));
        addPart(new InHouse(generatePartId(), "drill bit", 1.20, 37, 10, 100, 46));
    }

    public static void deletePartAtIndex(int partId) {
        allParts.remove(partId);
    }
}

