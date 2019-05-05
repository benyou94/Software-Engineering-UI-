package com.example.ResultParsers;

import com.example.Data.Component;
import com.example.Data.MedProduct;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

/**
 * <b><u>CS 4800 Class Project: Medical Devices Data with Blockchain</b></u>
 * <br>
 * This is the class representing the result parser for the results of the user's query. This
 * takes the results (in JSONArray format) and creates MedProduct objects to represent the results.
 *
 * @author Lisa Chen
 */
public class QueryResultParser implements ResultParserInterface {
    ArrayList<Component> parsedResults;
    private final String PRODUCT_ID_KEY = "productID";
    private final String PRODUCT_NAME_KEY = "productName";
    private final String SUPPLIER_KEY = "previousOwner"; //TODO verify correct and not owner
    private final String ORDER_ID_KEY = "orderID"; //TODO update this to match API
    private final String ORDER_DATE_KEY = "orderDate"; //TODO update this to match API

    /**
     * Constructs the parser with the JSONArray results to parse.
     * @param results The results in JSONArray format that needs to be parsed
     * @throws JSONException
     */
    public QueryResultParser(JSONArray results) throws JSONException {
        parsedResults = new ArrayList<>();
        parseResults(results);
    }
    /**
     * Retrieves the results parsed into a list of Component objects.
     * @return The parsed results
     */
    public ArrayList<Component> getParsedResults() { return parsedResults; }

    /**
     * Parses the JSON results into Medical Product objects.
     * @param results The results in JSONArray format
     * @throws JSONException
     */
    private void parseResults(JSONArray results) throws JSONException {
        if (results != null)
        {
            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.getJSONObject(i);
                //TODO need to figure out exception for when it cannot find orderID or orderDate
                String productID = result.getString(PRODUCT_ID_KEY);
                String productName = result.getString(PRODUCT_NAME_KEY);
                String supplier = result.getString(SUPPLIER_KEY);
                String orderID = result.getString(ORDER_ID_KEY);
                String orderDate = result.getString(ORDER_DATE_KEY);
                MedProduct newProduct = new MedProduct(productID,productName,supplier, orderID,
                        orderDate);
                parsedResults.add(newProduct);
                //TODO need to add section to calculate creating the subcomponents
            }
            removeDuplicateResults();
        }
    }

    private void removeDuplicateResults() {
        Collections.sort(parsedResults);
        for (int i = 0; i < parsedResults.size() - 2; i++) {
            boolean foundDup = true;
            MedProduct comp1 = (MedProduct) parsedResults.get(i);
            while (foundDup) {
                MedProduct comp2 = (MedProduct) parsedResults.get(i+1);
                if (isDuplicateResult(comp1, comp2)) {
                    parsedResults.remove(i+1);
                }
                else
                    foundDup = false;
            }
        }
    }

    private boolean isDuplicateResult(MedProduct comp1, MedProduct comp2) {
        if (comp1.getSKU().equals(comp2.getSKU())) {
            if (comp1.getName().equals(comp2.getName())) {
                if (comp1.getSupplier().equals(comp2.getSupplier())) {
                    if (comp1.getOrderID().equals(comp2.getOrderID())) {
                        return comp1.getOrderDate().equals(comp2.getOrderDate());
                    }
                    return false;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    /**
     * Checks if there are results.
     * @return True of results exists; false otherwise.
     */
    public boolean hasResults() { return parsedResults.size() > 0; }

    /**
     * Checks if there are subcomponents of the results.
     * @param component The component to check if there are subcomponents
     * @return True of subcomponents exists; false otherwise.
     */
    public boolean hasSubResults(Component component) {
        return component.getSubComponents() != null;
    }
}
