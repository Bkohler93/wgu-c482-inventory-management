package com.example.inventory_manager.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * class Product.java
 */
public class Product {
    private ObservableList<Part> associatedParts =  FXCollections.observableList(new ArrayList<>());;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    public Product(int id, int min, int max, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * method setId
     * @param id the id to set
     */
    public void setId(int id) { this.id = id; }

    /**
     * method setName
     * @param name the name to set
     */
    public void setName(String name) { this.name = name; }

    /**
     * method setPrice
     * @param price the price to set
     */
    public void setPrice(double price) { this.price = price; }

    /**
     * method setStock
     * @param stock the stock to set
     */
    public void setStock(int stock) { this.stock = stock; }

    /**
     * method setMin
     * @param min the min to set
     */
    public void setMin(int min) { this.min = min;}

    /**
     * method setMax
     * @param max the max to set
     */
    public void setMax(int max) { this.max = max;}

    /**
     * method getId
     * @return the id
     */
    public int getId() { return this.id; }

    /**
     * method getName
     * @return the name
     */
    public String getName() { return this.name;}

    /**
     * method getPrice
     * @return the price
     */
    public double getPrice() { return this.price; }

    /**
     * method getStock
     * @return the stock
     */
    public int getStock() { return this.stock;}

    /**
     * method getMin
     * @return the min
     */
    public int getMin() { return this.min;}

    /**
     * method getMax
     * @return the max
     */
    public int getMax() {return this.max;}

    /**
     * method addAssociatedPart
     * @param part the part to add
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * method deleteAssociatedPart
     * @param selectedAssociatedPart the part to delete from associatedParts
     * @return boolean true if part was deleted, false otherwise
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if (associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        return false;
    }

    /**
     * method getAllAssociatedParts
     * @return ObservableList<Part> the associated parts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}

