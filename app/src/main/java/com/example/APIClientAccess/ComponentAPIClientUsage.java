package com.example.APIClientAccess;

import android.util.Log;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * <b><u>CS 4800 Class Project: Medical Devices Data with Blockchain</b></u>
 * <br>
 * This is to access and use the API to access the database containing information of the
 * components of the medical products.
 *
 * Add-on access URLs to access the API is found in:
 * https://blockchain-restful-api.herokuapp.com/documentation
 * TODO update with API's requirements for access; current implementation predicts based on /query
 *
 * Code created based on reference: https://loopj.com/android-async-http/
 *
 * @author Lisa Chen and Johnson Wei
 */
public class ComponentAPIClientUsage {
    //TODO update with API's requirements for accessing the database
    private final String DB_ACCESS_URL = "database/";
    private final String PARENT_ID_KEY = "componentOf";
    private final String DB_QUERY_ID_KEY = "dbQueryID";
    private static final String RESULT_DATA_KEY = "data";
    private String dbQueryID;
    private JSONArray data_array;

    /** Default constructor */
    public ComponentAPIClientUsage() {}

    /**
     * Retrieves the JSON results from the API for the components of the parent product.
     * @param parentID The ID/SKU of the parent product
     * @return The results as a JSONArray containing all the components, if any
     */
    public JSONArray getJSONResults(String parentID) {
        initializeDBQueryID(populatePOSTParams(parentID));

        //wait until dbQueryID is available
        Log.d("LisaWhileLoop2", "Entered while loop in ComponentAPI- check for infinite loop");
        while (dbQueryID == null);
        Log.d("LisaWhileLoop2", "Ended loop - not infinite");

        initializeDataArray(populateGETParams(dbQueryID));

        return data_array;
    }

    /**
     * Initialize a new database queryID as given from the web API. Parameters are sent through
     * POST to the web API, which gives back a dbQueryID that is used to get the results through
     * GET.
     * @param postParams The parameters posted to the web API
     */
    private void initializeDBQueryID(final RequestParams postParams) {
        MedDeviceAPIClient.post(DB_ACCESS_URL,postParams,new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    dbQueryID = response.getString(DB_QUERY_ID_KEY);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("Error", "Unable to retrieve queryID from API");
                    dbQueryID = "Error";
                }
            }

            //should be unused method for debugging
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("Error", "Somehow accessed onSuccess() method with a JSONArray " +
                        "result for post");
                dbQueryID = "Error";
            }
        });
    }

    /**
     * Initializes an JSONArray with the data attained from the API through GET.
     * @params getParams This is the parameters passed through to the API with GET
     */
    private void initializeDataArray(RequestParams getParams) {
//        MedDeviceAPIClient.get(DB_ACCESS_URL,getParams, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                try {
//                    data_array = response.getJSONArray(RESULT_DATA_KEY);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Log.d("Error", "Cannot initialize database data with given params");
//                    data_array = null;
//                }
//            }
//
//            //should be unused method for debugging
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                Log.d("Error", "Somehow accessed onSuccess() method with a JSONArray " +
//                        "result for post");
//                data_array = null;
//            }
//        });
    }

    /**
     * Creates the list of params to POST to API by adding given params to the request param.
     * @param parentID This is the ID for the parent of the components
     * @return The list of params to post to API
     */
    private RequestParams populatePOSTParams(String parentID)
    {
        RequestParams postParam = new RequestParams();
        postParam.add(PARENT_ID_KEY, parentID);
        return postParam;
    }

    /**
     * Creates the parameter object to access the API using GET.
     * @param dbQueryID The id for the query retrieved from POST
     * @return The list of params to GET from API
     */
    private RequestParams populateGETParams(String dbQueryID)
    {
        RequestParams getParam = new RequestParams();
        getParam.add(DB_QUERY_ID_KEY, dbQueryID);
        return getParam;
    }
}