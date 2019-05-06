package com.example.Data;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

/**
 * <b><u>CS 4800 Class Project: Medical Devices Data with Blockchain</b></u>
 * <br>
 * This is the class representing the Medical Product object. Medical products have an associated
 * SKU (or reference ID), name, supplier, order ID, and order Date. They also may have components
 * that make up the product.
 *
 * @author Lisa Chen
 */
public class MedProduct extends Component {
    private String orderID;
    private String orderDate;

    /**
     * Constructs a medical product object (has no subcomponents) with describers.
     * @param sku The medical product's SKU or reference ID
     * @param name The medical product's name
     * @param supplier The supplier of the medical product
     * @param orderID The order ID associated with the purchase of this specific medical product
     * @param orderDate The order date associated with the purchase of this specific medical product
     */
    public MedProduct(String sku, String name, String supplier, String orderID, String orderDate) {
        super(sku, name, supplier);
        this.orderID = orderID;
        this.orderDate = orderDate;
    }

    /**
     * Constructs a medical product object with its describers and its components.
     * @param sku The medical product's SKU or reference ID
     * @param name The medical product's name
     * @param supplier The supplier of the medical product
     * @param orderID The order ID associated with the purchase of this specific medical product
     * @param orderDate The order date associated with the purchase of this specific medical product
     * @param components The subcomponents of the medical product
     */
    public MedProduct(String sku, String name, String supplier, String orderID, String orderDate,
                      ArrayList<Component> components) {
        super(sku, name, supplier, components);
        this.orderID = orderID;
        this.orderDate = orderDate;
    }

    /**
     * Gets the order ID associated with this specific medical product object purchase.
     * @return The order ID used to purchase this specific medical product object
     */
    public String getOrderID() { return orderID; }

    /**
     * Gets the order date associated with this specific medical product object purchase.
     * @return The order date associated with the purchase this specific medical product object
     */
    public String getOrderDate() { return orderDate; }

    /**
     * Compares the MedProducts first by how its compared in its super method. Then, if it is still
     * considered equivalent, does more comparisons by Order ID and Order Date.
     * @param otherProduct The MedProduct to compare to this MedProduct object
     * @return Negative if this object comes before compared object, positive if after, 0 if same
     */
    @Override
    public int compareTo(@NonNull Component otherProduct) {
        int superCompare = super.compareTo(otherProduct);
        if (superCompare == 0) {

            try {
                MedProduct medProduct = (MedProduct) otherProduct;
                int orderIDCompare = this.orderID.compareTo(medProduct.orderID);

                if (orderIDCompare == 0)
                    return this.orderDate.compareTo(medProduct.orderDate);

                return orderIDCompare;
            } catch (ClassCastException e) {
                e.printStackTrace();
                Log.d("Error", "Failed to cast component to MedProduct.");
            }
        }
        return superCompare;
    }
}
