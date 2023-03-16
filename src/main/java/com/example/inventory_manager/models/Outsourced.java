package com.example.inventory_manager.models;

/**
 * class Outsourced.java
 */
public class Outsourced extends Part {
    private String companyName;
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * method setCompanyName
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * method getCompanyName
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }
}

