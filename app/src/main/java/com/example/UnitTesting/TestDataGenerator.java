package com.example.UnitTesting;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.example.Data.Component;
import com.example.ResultParsers.ComponentResultParser;
import com.example.ResultParsers.QueryResultParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * <b><u>CS 4800 Class Project: Medical Devices Data with Blockchain</b></u>
 * <br>
 * This is the class that generates the list of MedProducts and Components from the .json test data
 * files made for unit testing and quality checking. The MedProduct and Component objects created
 * have no subcomponents due to the nature of how the files were created. This is simply for testing
 * the display of information and checking the functionality of ResultParser classes.
 *
 * Referenced this website for reading JSON files:
 * https://stackoverflow.com/questions/13814503/reading-a-json-file-in-android/13814551#13814551
 *
 * @author Lisa Chen
 */
public class TestDataGenerator {
    private static final String RESULT_DATA_KEY = "data";
    private static final String QUERY_TEST_DATA_FILE = "blockchain.json";
    private static String COMPONENT_TEST_DATA_FILE = "database.json";

    /**
     * Gets the data that represents the format from querying the blockchain for a product through
     * the API.
     * @param context The context of the UI
     * @return The list of MedProducts (as Component objects) from the test data file
     */
    public static ArrayList<Component> getQueryData(Context context) {
        JSONObject queryObject = changeToJSON(QUERY_TEST_DATA_FILE, context);
        if (queryObject == null)
            return null;
        JSONArray dataArray = retrieveDataArray(queryObject);

        try {
            QueryResultParser queryParser = new QueryResultParser(dataArray);
            return queryParser.getParsedResults();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets the data that represents the format from querying the database for a product through
     * the API.
     * @param context The context of the UI
     * @return The list of Components from the test data file
     */
    public static ArrayList<Component> getComponentData(Context context) {
        JSONObject componentObject = changeToJSON(COMPONENT_TEST_DATA_FILE, context);
        if (componentObject == null)
            return null;
        JSONArray dataArray = retrieveDataArray(componentObject);

        try {
            ComponentResultParser componentParser = new ComponentResultParser(dataArray);
            return componentParser.getParsedResults();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method changes the test data file to a JSONObject.
     * @param fileName The filename of the test data file to convert
     * @param context The UI's context (for finding the location of the data file)
     * @return The JSONObject representing the data from the data file
     */
    private static JSONObject changeToJSON(String fileName, Context context) {
        try {
            final AssetManager assets = context.getAssets();
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];

            is.read(buffer);
            is.close();

            String json = new String(buffer, "UTF-8");

            return new JSONObject(json);

        } catch (JSONException | IOException e) {
            e.printStackTrace();
            Log.d("Error", "Unable to change the unit test JSON file to a JSONObject");
            return null;
        }
    }

    /**
     * This retrieves the data array as a JSONArray from the JSONObject representing the test file
     * @param dataObject The JSONObject representing the contents of the test data file
     * @return The JSONArray containing the data of interest
     */
    private static JSONArray retrieveDataArray(JSONObject dataObject) {
        try {
            return dataObject.getJSONArray(RESULT_DATA_KEY);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("Error", "Unable to change to JSONArray in retrieveDataArray()");
            return null;
        }
    }
}
