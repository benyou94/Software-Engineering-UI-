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
    public MedProduct(String sku, String name, String supplier, String orderID, String orderDate) {
        super(sku, name, supplier);
        this.orderID = orderID;
        this.orderDate = orderDate;
    }
    public MedProduct(String sku, String name, String supplier, ArrayList<Component> components,
                     String orderID, String orderDate) {
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
     * Compares the components first by its SKU, then by name, then by supplier.
     * @param component
     * @return
     */
    @Override
    public int compareTo(@NonNull Component component) {
        int supplierCompare = super.compareTo(component);
        if (supplierCompare == 0) {
            try {
                MedProduct medProduct = (MedProduct) component;
                int orderIDCompare = this.orderID.compareTo(medProduct.orderID);
                if (orderIDCompare == 0) {
                    return this.orderDate.compareTo(medProduct.orderDate);
                }
                else
                    return orderIDCompare;
            }
            catch (ClassCastException e) {
                e.printStackTrace();
                Log.d("Error", "Failed to cast component to MedProduct.");

            }
            finally {
                return supplierCompare;
            }
        }
        else
            return supplierCompare;
    }
}
