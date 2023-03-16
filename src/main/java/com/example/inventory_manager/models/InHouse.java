package com.example.inventory_manager.models;

/**
 * class InHouse.java
 */
public class InHouse extends Part {
    private int machineId;
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * method setMachineId
     * @param machineId the machineId to set
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * method getMachineId
     * @return the machineId
     */
    public int getMachineId() {
        return machineId;
    }
}

