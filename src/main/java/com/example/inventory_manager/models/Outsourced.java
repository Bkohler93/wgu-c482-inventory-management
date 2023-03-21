package com.example.inventory_manager.models;

/**
 * @author Brett Kohler
 * RUNTIME ERROR: no errors while writing this class code
 */
public class Outsourced extends Part {
    private String companyName;

    /**
     * @param id id of part
     * @param name name of part
     * @param price price of part
     * @param stock amount of part in stock
     * @param min minimum number of parts in stock
     * @param max maximum number of parts in stock
     * @param companyName name of company who provides part
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }
}

