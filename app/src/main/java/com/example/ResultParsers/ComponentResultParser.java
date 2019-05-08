package com.example.ResultParsers;

import com.example.APIClientAccess.ComponentAPIClientUsage;
import com.example.Data.Component;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        parsedResults = new ArrayList<>();
        parseResults(results);
    }

    /**
     * Retrieves the results parsed into a list of Component objects.
     *
     * @return The parsed results
     */
    @Override
    public ArrayList<Component> getParsedResults() { return parsedResults; }

    /**
     * Parses the JSON results into Component objects.
     * @param results The results in JSONArray format
     * @throws JSONException
     */
    private void parseResults(JSONArray results) throws JSONException {
        if (results != null) {
            String[] keys = {PRODUCT_ID_KEY, PRODUCT_NAME_KEY, SUPPLIER_KEY};
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
                Component newComponent = new Component(params[0],params[1],params[2]);
                parsedResults.add(newComponent);
                //TODO need to add section to calculate creating the subcomponents
            }
        }
    }

    /**
     * Retrieves the components of the given parent (through its parent ID).
     * @param parentID The parent ID of the component to find the subcomponents
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

    /**
     * Checks if there are results.
     * @return True of results exists; false otherwise.
     */
    @Override
    public boolean hasResults() {
        return parsedResults.size() > 0;
    }
}
