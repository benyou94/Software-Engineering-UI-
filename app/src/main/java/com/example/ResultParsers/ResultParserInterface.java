package com.example.ResultParsers;

import com.example.Data.Component;

import java.util.ArrayList;

/**
 * <b><u>CS 4800 Class Project: Medical Devices Data with Blockchain</b></u>
 * <br>
 * This is the interface for the result parsers.
 *
 * @author Lisa Chen
 */
public interface ResultParserInterface {

    /**
     * Retrieves the results parsed into a list of Component objects.
     * @return The parsed results
     */
    public ArrayList<Component> getParsedResults();

    /**
     * Checks if there are results.
     * @return True of results exists; false otherwise.
     */
    public boolean hasResults();

    /**
     * Checks if there are subcomponents of the results.
     * @param component The component to check if there are subcomponents
     * @return True of subcomponents exists; false otherwise.
     */
    public boolean hasSubResults(Component component);
}
