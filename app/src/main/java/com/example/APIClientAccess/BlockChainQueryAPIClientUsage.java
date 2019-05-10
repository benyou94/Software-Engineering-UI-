package com.example.APIClientAccess;

import android.util.Log;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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
    private JSONObject data_array;

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
    public JSONObject getJSONResults(String blockchainID, String productID, String productName,
                                     String supplier) throws JSONException, InterruptedException, IOException {

        initializeQueryID(populatePOSTParams(blockchainID, productID, productName, supplier));

        //wait until queryID is available
        while (queryID == null);


        Log.d("LisaAPIConnectionTest", "queryID before data array: " + queryID);


        boolean gotQuery = false;
        while (!gotQuery) {
//            tries++;
            try {
                initializeDataArray(populateGETParams(queryID));
                if (data_array != null)
                    gotQuery = true;
            }
            catch (Exception e) {
                gotQuery = false;
            }
        }


        if (data_array != null) {
            Log.d("LisaAPIConnectionTest", data_array.toString());
        }
        else {
            Log.d("LisaAPIConnectionTest", "DATA IS STILL NULL = FAIL");
        }
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
    public void initializeDataArray(final RequestParams getParams) throws InterruptedException, UnsupportedEncodingException {
        MedDeviceAPIClient.get(getGETUrl(), new JsonHttpResponseHandler() {
            //        MedDeviceAPIClient.get(QUERY_ACCESS_URL, getParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Log.d("LIsaAPIConnection", response.toString());
                    data_array = response.getJSONObject(RESULT_DATA_KEY);
                    Log.d("LisaAPIConnectionTest", data_array.toString());
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

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);

                Log.d("LisaOnFailureStatus: ", String.valueOf(statusCode));
                if (errorResponse != null)
                    Log.d("LisaOnFailureResponse: ", errorResponse.toString());
                super.onFailure(statusCode,headers, throwable, errorResponse);
            }

        });
    }

    /**
     * Gives the URL to access the GET for the API.
     * @return The URL for the GET access.
     */
    private String getGETUrl() throws UnsupportedEncodingException {
//        return QUERY_ACCESS_URL + "?" + QUERY_ID_KEY + "=" + queryID;
        StringBuilder result = new StringBuilder();
        result.append(QUERY_ACCESS_URL + "?");
        result.append(URLEncoder.encode(QUERY_ID_KEY, "UTF-8"));
        result.append("=");
        result.append(URLEncoder.encode(queryID, "UTF-8"));
        Log.d("LisaStringBuilder", result.toString());
        return result.toString();
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
