package com.example.inventory_manager.models;

/**
 * @author Brett Kohler
 * RUNTIME ERRORS: there weren't errors from this class
 */
public class InHouse extends Part {
    private int machineId;

    /**
     * @param id id of part
     * @param name name of part
     * @param price price of part
     * @param stock amount of part in stock
     * @param min minimum amount of part that must be in stock
     * @param max max amount of part that can be in stock
     * @param machineId id of machine part can be found at
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * @param machineId the machineId to set
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * @return the machineId
     */
    public int getMachineId() {
        return machineId;
    }
}

