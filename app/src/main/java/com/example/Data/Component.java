package com.example.Data;

import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * <b><u>CS 4800 Class Project: Medical Devices Data with Blockchain</b></u>
 * <br>
 * This is the class representing a component, which has a name, SKU or reference ID, and supplier.
 * The component may have subcomponents that make up the component.
 *
 * @author Lisa Chen
 */
public class Component implements Comparable<Component>{
    private String sku;
    private String name;
    private String supplier;
    private ArrayList<Component> components;

    /**
     * Constructs a component (does not have any subcomponents) with its describers: SKU, name,
     * and supplier.
     * @param sku The product's SKU
     * @param name The product's name
     * @param supplier The supplier of the product
     */
    public Component(String sku, String name, String supplier) {
        this.sku = sku;
        this.name = name;
        this.supplier = supplier;
        components = null;
    }

    /**
     * Constructs a component with its subcomponents and describers: SKU, name, and supplier
     * @param sku The product's SKU
     * @param name The product's name
     * @param supplier The supplier of the product
     * @param components The components making up this component
     */
    public Component(String sku, String name, String supplier, ArrayList<Component> components) {
        this(sku, name, supplier);
        this.components = components;
    }

    /**
     * Gets the component's SKU or ID.
     * @return Component's SKU or ID
     */
    public String getSKU() { return sku; }

    /**
     * Get's the component's name.
     * @return Component's name
     */
    public String getName() { return name; }

    /**
     * Get's the supplier of the component.
     * @return Component's supplier
     */
    public String getSupplier() { return supplier; }

    /**
     * Get's the components making up of this component.
     * @return The components of this component
     */
    public ArrayList<Component> getSubComponents() { return components; }


    /**
     * Compares the components first by its SKU, then by name, then by supplier.
     * @param component
     * @return
     */
    @Override
    public int compareTo(@NonNull Component component) {
        int skuCompare = this.sku.compareTo(component.sku);
        if (skuCompare == 0) {
            int nameCompare = this.name.compareTo(component.name);
            if (nameCompare == 0) {
                return this.supplier.compareTo(component.supplier);
            }
            else
                return nameCompare;
        }
        else
            return skuCompare;
    }
}
