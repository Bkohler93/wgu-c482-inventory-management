package com.example.inventory_manager;

/**
 * @author Brett Kohler
 * @author  <a href="https://stackoverflow.com/questions/12517978/java-constant-examples-create-a-java-file-having-only-constants">reference</a>
 */
public final class Constants {

    /**
     * prevents instantiation of constants
     */
    private Constants() {
        // restrict instantiation
    }

    /**
     * value of Part source label when part is inhouse
     */
    public static final String IN_HOUSE_LABEL = "Machine ID";

    /**
     * value of Part source label when part is outsourced
     */
    public static final String OUTSOURCED_LABEL = "Company Name";
}
