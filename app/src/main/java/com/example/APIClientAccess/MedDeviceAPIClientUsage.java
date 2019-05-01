package com.example.APIClientAccess;

import android.util.Log;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.*;
import java.util.HashMap;
import cz.msebera.android.httpclient.Header;

/**
 * <b><u>CS 4800 Class Project: Medical Devices Data with Blockchain</b></u>
 * <br>
 * This is to access and use the Medical Devices API.
 *
 * Add-on access URLs to access the API is found in:
 * https://blockchain-restful-api.herokuapp.com/documentation
 *
 * Code created based on reference: https://loopj.com/android-async-http/
 *
 * TODO Need to update methods based on class diagram
 *
 * @author Lisa Chen
 */
public class MedDeviceAPIClientUsage {
    private final String QUERY_ACCESS_URL = "query/";
    private final String BLOCKCHAIN_PARAM = "blockchainID";
    private final String QUERY_ID_PARAM = "queryID";
    private String queryID;

    /** Default constructor */
    public MedDeviceAPIClientUsage() {}

    /**
     * Gets the queryID from the web API with the given parameters.
     * @param blockchainID The ID for which blockchain to use
     * @return A queryID needed to get the results from the web API
     * @throws JSONException
     */
    public String getQueryID(String blockchainID) throws JSONException, InterruptedException {
        initializeQueryID(populatePOSTParams(blockchainID));

        //takes a while before queryID is available
        while (queryID == null) {
        /*
        TODO change this to wait for a period of time and print if takes too long
        Note that it's best to create a method for waiting because this will be reused elsewhere
         */
        }

        return queryID;
    }

    private void getJSONResults()
    {
        /*
        TODO do the get() for MedDeviceAPIClient using the queryID
        Note that this will also require to wait a period of time, so use the method you used in
        getQueryID
         */
    }

    /**
     * Initialize a new queryID as given from the web API. Parameters are sent through POST to
     * the web API, which gives back a queryID that is used to get the results through GET.
     * @param postParams The parameters posted to the web API
     * @throws JSONException
     */
    private void initializeQueryID(final RequestParams postParams) throws JSONException {
        MedDeviceAPIClient.post(QUERY_ACCESS_URL,postParams,new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    queryID = response.getString(QUERY_ID_PARAM);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("Error", "Unable to convert the queryID to String");
                }
                // If the response is JSONObject instead of expected JSONArray
            }

            //unused method
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("Error", "Somehow accessed onSuccess() method with a JSONArray " +
                        "result for post");
            }

            //Needed to avoid errors with AsyncTask in main method
            @Override
            public boolean getUseSynchronousMode() {
                return false;
            }
        });
    }

    /**
     * Creates the list of params to POST to API by adding given params to the request param.
     * @param blockchainID This is the ID for the blockchain to use for data
     * @return The list of params to post to API
     */
    private RequestParams populatePOSTParams(String blockchainID)
    {
        HashMap<String, String> hashParams = new HashMap<String, String>();
        hashParams.put(BLOCKCHAIN_PARAM, blockchainID);
        return new RequestParams(hashParams);
    }
}
