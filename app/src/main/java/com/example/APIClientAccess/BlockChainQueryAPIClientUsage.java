package com.example.APIClientAccess;

import android.util.Log;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.*;
import cz.msebera.android.httpclient.Header;

/**
 * <b><u>CS 4800 Class Project: Medical Devices Data with Blockchain</b></u>
 * <br>
 * This is to access and use the Medical Devices API to retrieve data from the blockchains that it
 * links to.
 *
 * Add-on access URLs to access the API is found in:
 * https://blockchain-restful-api.herokuapp.com/documentation
 *
 * Code created based on reference: https://loopj.com/android-async-http/
 *
 * @author Lisa Chen and Johnson Wei
 */
public class BlockChainQueryAPIClientUsage {
    private final String QUERY_ACCESS_URL = "query/";
    private final String BLOCKCHAIN_KEY = "blockchainID";
    private final String QUERY_ID_KEY = "queryID";
    private static final String PRODUCT_ID_KEY = "productID";
    private static final String PRODUCT_NAME_KEY = "productName";
    private static final String SUPPLIER_KEY = "previousOwner";
    private static final String RESULT_DATA_KEY = "data";
    private String queryID;
    private JSONArray data_array;

    /** Default constructor */
    public BlockChainQueryAPIClientUsage() {}

    /**
     * FOR DEBUGGING AND TESTING PURPOSES ONLY. CALL THIS ONLY AFTER getJSONResults().
     * @return A queryID needed to get the results from the web API
     */
    public String getQueryID() {
        //if null, getJSONResults() was not called or did not complete its task
        while (queryID == null);
//            return "Did not use getQueryID properly. Null result.";
        Log.d("LisaQuery", queryID);
        return queryID;
    }


    /**
     * Retrieves the results from the chosen blockchain from the API as a JSON array with the
     * given parameters.
     * @param blockchainID This is the ID for the blockchain to use for retrieving data
     * @param productID This is the ID for the product you are trying to find
     * @param productName This is the name of the product you are trying to find
     * @param supplier This is the supplier of the product you are trying to find
     * @return The data from the API as a JSONArray
     */
    public JSONArray getJSONResults(String blockchainID, String productID, String productName,
                                    String supplier) {

        initializeQueryID(populatePOSTParams(blockchainID, productID, productName, supplier));

        //wait until queryID is available
        Log.d("LisaWhileLoop1", "Entered while loop in blockchain- check for infinite loop");
        while (queryID == null);
        Log.d("LisaWhileLoop1", "Ended loop - not infinite");

        initializeDataArray(populateGETParams(queryID));

        return data_array;
    }

    /**
     * Initialize a new queryID as given from the web API. Parameters are sent through POST to
     * the web API, which gives back a queryID that is used to get the results through GET.
     * @param postParams The parameters posted to the web API
     */
    private void initializeQueryID(final RequestParams postParams) {
        MedDeviceAPIClient.post(QUERY_ACCESS_URL,postParams,new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    queryID = response.getString(QUERY_ID_KEY);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("Error", "Unable to retrieve queryID from API");
                    queryID = "Error";
                }
            }
            //should be unused method for debugging
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("Error", "Somehow accessed onSuccess() method with a JSONArray " +
                        "result for post");
                queryID = "Error";
            }
        });
    }

    /**
     * Initializes an JSONArray with the data attained from the API through GET.
     * @params getParams The parameters needed to access GET in the API
     */
    public void initializeDataArray(final RequestParams getParams) {
        MedDeviceAPIClient.get(QUERY_ACCESS_URL,getParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    data_array = response.getJSONArray(RESULT_DATA_KEY);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("Error", "Cannot initialize blockchain data with given params");
                    data_array = null;
                }
            }
            //should be unused method for debugging
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("Error", "Somehow accessed onSuccess() method with a JSONArray " +
                        "result for post");
                data_array = null;
            }
        });
    }

    /**
     * Creates the list of params to POST to API by adding given params to the request param.
     * @param blockchainID This is the ID for the blockchain to use for retrieving data
     * @param productID This is the ID for the product you are trying to find
     * @param productName This is the name of the product you are trying to find
     * @param supplier This is the supplier of the product you are trying to find
     * @return The list of params to post to API
     */
    private RequestParams populatePOSTParams(String blockchainID, String productID,
                                             String productName, String supplier) {

        RequestParams postParam = new RequestParams();
        String[] inputs = {blockchainID, productID, productName, supplier};
        String[] keys = {BLOCKCHAIN_KEY, PRODUCT_ID_KEY, PRODUCT_NAME_KEY, SUPPLIER_KEY};

        for (int i = 0; i < inputs.length; i++) {
            if (!(inputs[i] == null && inputs[i] == ""))
                postParam.add(keys[i], inputs[i]);
        }
        Log.d("LisaTestPOSTParams", postParam.toString());
        return postParam;
    }

    /**
     * Creates the parameter object to access the API using GET.
     * @param queryID The id for the query retrieved from POST
     * @return The list of params to GET from API
     */
    private RequestParams populateGETParams(String queryID) {
        RequestParams getParam = new RequestParams();
        getParam.add(QUERY_ID_KEY, queryID);
        return getParam;
    }
}
