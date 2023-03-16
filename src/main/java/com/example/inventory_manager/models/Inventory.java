package com.example.inventory_manager.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * class Inventory.java
 */
public class Inventory {
    final private static ObservableList<Part> allParts = FXCollections.observableList(new ArrayList<>());
    final private static ObservableList<Product> allProducts = FXCollections.observableList(new ArrayList<>());

    /**
     * method addPart
     * @param newPart part to add
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * method addProduct
     * @param newProduct product to add
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * method lookupPart
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
     * method lookupProduct
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
     * method lookupPart
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
     * method lookupProduct
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
     * method updatePart
     * @param index index of part to update
     * @param selectedPart Part object to update old index
     */
    public static void updatePart(int index, Part selectedPart) { allParts.add(index, selectedPart); }

    /**
     * method updateProduct
     * @param index index of product to update
     * @param newProduct Product object to insert
     */
    public static void updateProduct(int index, Product newProduct) { allProducts.add(index, newProduct); }

    /**
     * method deletePart
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
     * method deleteProduct
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
     * method getAllParts
     * @return list of all Part instances in allParts list
     */
    public static ObservableList<Part> getAllParts() { return allParts; }

    /**
     * method getAllProducts
     * @return list of all Product instances in allProducts list
     */
    public static ObservableList<Product> getAllProducts() { return allProducts; }
}

