package com.example.inventory_manager.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * @author Brett Kohler
 * RUNTIME ERROR: no errors while writing this class code.
 */
public class Product {
    final private ObservableList<Part> associatedParts =  FXCollections.observableList(new ArrayList<>());
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * @param id id of product
     * @param min minimum number of product in stock
     * @param max maximum number of product in stock
     * @param name name of product
     * @param price price of product in dollars and cents
     * @param stock amount of product in stock
     */
    public Product(int id, int min, int max, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) { this.id = id; }

    /**
     * @param name the name to set
     */
    public void setName(String name) { this.name = name; }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) { this.price = price; }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) { this.stock = stock; }

    /**
     * @param min the min to set
     */
    public void setMin(int min) { this.min = min;}

    /**
     * @param max the max to set
     */
    public void setMax(int max) { this.max = max;}

    /**
     * @return the id
     */
    public int getId() { return this.id; }

    /**
     * @return the name
     */
    public String getName() { return this.name;}

    /**
     * @return the price
     */
    public double getPrice() { return this.price; }

    /**
     * @return the stock
     */
    public int getStock() { return this.stock;}

    /**
     * @return the min
     */
    public int getMin() { return this.min;}

    /**
     * @return the max
     */
    public int getMax() {return this.max;}

    /**
     * @param part the part to add
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
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
     * @return ObservableList(Part) the associated parts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    /**
     * @return true if associated parts list is not empty, false if it is
     */
    public boolean hasAssociatedParts() {
        return !getAllAssociatedParts().isEmpty();
    }
}

