package com.example.ResultParsers;

import com.example.Data.Component;

import java.util.ArrayList;

public class ComponentResultParser implements ResultParserInterface {
    ArrayList<Component> parsedResults;
    //TODO update to match whatever the API sets the keys to for querying against database
    private final String PRODUCT_ID_KEY = "productID";
    private final String PRODUCT_NAME_KEY = "productName";
    private final String SUPPLIER_KEY = "previousOwner";
    /**
     * Retrieves the results parsed into a list of Component objects.
     *
     * @return The parsed results
     */
    @Override
    public ArrayList<Component> getParsedResults() {
        return parsedResults;
    }

    /**
     * Checks if there are results.
     * @return True of results exists; false otherwise.
     */
    @Override
    public boolean hasResults() {
        return parsedResults.size() > 0;
    }

    /**
     * Checks if there are subcomponents of the results.
     *
     * @param component The component to check if there are subcomponents
     * @return True of subcomponents exists; false otherwise.
     */
    @Override
    public boolean hasSubResults(Component component) {
        return component.getSubComponents() != null;
    }
}
