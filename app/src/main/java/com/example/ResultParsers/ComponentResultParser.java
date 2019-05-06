package com.example.ResultParsers;

import com.example.Data.Component;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * <b><u>CS 4800 Class Project: Medical Devices Data with Blockchain</b></u>
 * <br>
 * This is the class representing the result parser for the results of the components of a given
 * parent product (which can take the form of MedProduct or Component). This takes the results
 * (in JSONArray format) and creates Component objects to represent the results.
 *
 * @author Lisa Chen and Jin Kim
 */
public class ComponentResultParser implements ResultParserInterface {
    ArrayList<Component> parsedResults;
    //TODO update to match whatever the API sets the keys to for querying against database
    private final String PRODUCT_ID_KEY = "productID";
    private final String PRODUCT_NAME_KEY = "productName";
    private final String SUPPLIER_KEY = "previousOwner";

    /**
     * Constructs the parser with the JSONArray results of components to parse.
     * @param results The results in JSONArray format that needs to be parsed
     * @throws JSONException
     */
    public ComponentResultParser(JSONArray results) throws JSONException {
        //TODO complete this section!!!!
        /*
        implement parseResults() and getParsedComponents() - similar to QueryResultParser
        Note: this uses Component class, so all you need to save is Product ID, product name, supplier
         */
    }
    /**
     * Retrieves the results parsed into a list of Component objects.
     *
     * @return The parsed results
     */
    @Override
    public ArrayList<Component> getParsedResults() { return parsedResults; }

    /**
     * Checks if there are results.
     * @return True of results exists; false otherwise.
     */
    @Override
    public boolean hasResults() {
        return parsedResults.size() > 0;
    }
}
