package com.example.ResultParsers;

import com.example.APIClientAccess.ComponentAPIClientUsage;
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
    private final String SUPPLIER_KEY = "previousOwner";
    private final String ORDER_ID_KEY = "orderID";
    private final String ORDER_DATE_KEY = "transactionDate";

    /**
     * Constructs the parser with the JSONArray results of MedProducts to parse.
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
        if (results != null) {
            String[] keys = {PRODUCT_ID_KEY, PRODUCT_NAME_KEY, SUPPLIER_KEY, ORDER_ID_KEY,
                    ORDER_DATE_KEY};
            String[] params = new String[keys.length];

            //retrieves each result from the array for parsing
            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.getJSONObject(i);

                //retrieves the parameter of each result to pass to MedProduct
                for (int key = 0; key < keys.length; key++) {
                    try {
                        params[key] = result.getString(keys[key]);
                    }
                    catch (JSONException e) {
                        params[key] = "";
                    }
                }

                ArrayList<Component> components = getParsedComponents(params[0]);
                MedProduct newProduct = new MedProduct(params[0],params[1],params[2], params[3],
                        params[4], components);

                parsedResults.add(newProduct);
            }
            removeDuplicateResults();
        }
    }

    /**
     * Retrieves the components of the given parent (through its parent ID).
     * @param parentID The parent ID of the product to find the subcomponents
     * @return A list of the parent's components
     */
    private ArrayList<Component> getParsedComponents(String parentID) throws JSONException {
        if (parentID == null || parentID == "")
            return null;

        //TODO bring back once component API access section is working (remove return null)
//        ComponentAPIClientUsage compAPIAccess = new ComponentAPIClientUsage();
//        ComponentResultParser subComponentParser = new
//                ComponentResultParser(compAPIAccess.getJSONResults(parentID));
//
//        return subComponentParser.getParsedResults();
        return null;
    }

    /** Removes duplicate results from the resulting parsed results. */
    private void removeDuplicateResults() {
        Collections.sort(parsedResults);

        for (int i = 0; i < parsedResults.size() - 2; i++) {
            boolean foundDup = true;
            MedProduct comp1 = (MedProduct) parsedResults.get(i);

            while (foundDup) {
                MedProduct comp2 = (MedProduct) parsedResults.get(i+1);

                if (isDuplicateResult(comp1, comp2))
                    parsedResults.remove(i+1);
                else
                    foundDup = false;
            }
        }
    }

    /**
     * Checks if the two given med products are the same (2nd one being duplicate of the first).
     * @param product1 First MedProduct to compare
     * @param product2 Second MedProduct to compare
     * @return True if the two given products are the same; false otherwise
     */
    private boolean isDuplicateResult(MedProduct product1, MedProduct product2) {
        return product1.compareTo(product2) == 0;
    }

    /**
     * Checks if there are results.
     * @return True of results exists; false otherwise.
     */
    public boolean hasResults() { return parsedResults.size() > 0; }
}