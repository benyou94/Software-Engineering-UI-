package com.example.practiceone;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.APIClientAccess.BlockChainQueryAPIClientUsage;
import com.example.Data.Component;
import com.example.Data.MedProduct;
import com.example.ResultParsers.QueryResultParser;
import com.example.UnitTesting.TestDataGenerator;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * @author Lisa Chen and Ben You
 */
public class SearchResultPage extends AppCompatActivity {

    private SearchTask queryTask;
    private final String LOADING_DIALOG_TEXT = "Loading Results. Please wait.";
    private ArrayAdapter arrayAdapter;
    ListView listview;
    Context context;


    //Dummy test data1. can remove once we get the official data.
    String[] productSKUStringArray = {"0000000000000000000000","11111111111111111111112222222222222222222222222222222222222222","2","3","4","5"};
    String[] productNameStringArray = {"Computer","Pokemon","Tomogachi","Jojo's Bizarre Adventure: HOLY SHIT I GOTA SHIT TON OF TEXT I GATTA REWWWEKK", "Maple Story","Weed"};
    String[] supplierStringArray = {"Sensei","Ash","Ben","GIOGIO","Nexon","Snoop Dog"};


    //These variables are to hold the information from the search parameter variables that passed over
    String productSKUString,productNameString,supplierNameString,blockChainIDString;


    //Creating Arraylists to hold the data from the API.
    ArrayList<String> productNameArrayList = new ArrayList<String>();
    ArrayList<String> productIDArrayList = new ArrayList<String>();
    ArrayList<String> supplierNameArrayList = new ArrayList<String>();
    ArrayList<String> orderIDArrayList = new ArrayList<String>();
    ArrayList<String> orderDateArrayList = new ArrayList<String>();


    //Local Data JSON, created for temp use.
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Redirect's activityView to the called activity
        setContentView(R.layout.activity_search_result_page);

        //Toolbar toolbar = findViewById(R.id.toolbar);

        //Changes the actionbar Title
        getSupportActionBar().setTitle("Search Results:");

        //Gets the search results from activity two
        Intent intent = getIntent();

        //This line should get the information from the variable in the second activity.
        productSKUString = intent.getStringExtra(SearchParameterPage.productSKUExtra);
        productNameString = intent.getStringExtra(SearchParameterPage.productNameExtra);
        supplierNameString = intent.getStringExtra(SearchParameterPage.supplierExtra);


        //TODO Lisa Test here

            //need to pass context (you can only retrieve during onCreate
            context = getApplicationContext();

            ArrayList<Component> queryData = TestDataGenerator.getQueryData(context);
            ArrayList<Component> componentData = TestDataGenerator.getComponentData(context);

            if (queryData != null) {
                for (Component item : queryData) {
                    MedProduct prod = (MedProduct) item;
                    Log.d("LisaUnitTest", "Name: " + prod.getName() + ", ProductID: " +
                            prod.getSKU() + ",Supplier: " + prod.getSupplier() + ", OrderID " +
                            prod.getOrderID() + ",Order Data: " + prod.getOrderDate());

                    //Grabs the data and puts it into the arrayList.
                    productNameArrayList.add(prod.getName());
                    productIDArrayList.add(prod.getSKU());
                    supplierNameArrayList.add(prod.getSupplier());
                    orderIDArrayList.add(prod.getOrderID());
                    orderDateArrayList.add(prod.getOrderDate());

                }
            }

            if (componentData != null) {
                for (Component item : componentData) {
                    Log.d("LisaUnitTest", "Name: " + item.getName() + ", ProductID: " +
                            item.getSKU() + ",Supplier: " + item.getSupplier());
                }

            }
        //TODO Lisa Test ENDS here

        //Gets the listview from searchresultpage
        listview = (ListView)findViewById(R.id.listView);


        //Creates a customAdapter (custom listview) for the listview
        CustomAdapter customAdapter = new CustomAdapter();

        //sets the listview to the custom adapter
        listview.setAdapter(customAdapter);
    }

    @Override
    protected void onResume() {

        super.onResume();
        queryTask = new SearchTask();


        //Parameter ("BlockChain Choose", ProductID, productName, supplier)
        //Need to implement a Spinner to get data from activity 2 (searchparameter)
        //0=Etherium, 1=Hyper Ledger, 2=Open Chain
        //return 0 if user enters etherium
        queryTask.execute("1", productSKUString, productNameString, supplierNameString);
    }

    //CustomAdapter for the custom ListView Display (these methods are generated automatically to handle the custom ListView)
    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            //dunno what this does. forgot about it :P -Ben
            return productNameArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        //The "View" method essentially displays all the data out, customized listview.
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            //Creating a view and creating variables to grab data and store them in
            convertView = getLayoutInflater().inflate(R.layout.listview_detail,null);

            //This assigns the listview_detail's TextViews into a variable we can display
            TextView textview_name = (TextView) convertView.findViewById((R.id.productNameTextView));
            TextView textview_sku =  (TextView) convertView.findViewById(R.id.productSKUTextView);
            TextView textview_supplier = (TextView) convertView.findViewById((R.id.supplierTextView));
            TextView textview_orderID = (TextView)convertView.findViewById(R.id.orderIDTextView);
            TextView textview_orderDate = (TextView)convertView.findViewById(R.id.orderDateTextView);


            //Dummy Data: Change the array names here to the WebAPI's Array.
            //This also prints in the UI (SearchResultsPage) to accurately display data.
            textview_name.setText("Product Name: " +productNameArrayList.get(position));
            textview_sku.setText("Product SKU/ID: " +productIDArrayList.get(position));
            textview_supplier.setText("Supplier Name: " +supplierNameArrayList.get(position));
            textview_orderID.setText("Order ID: " +orderIDArrayList.get(position));
            textview_orderDate.setText("Order Date: " +orderDateArrayList.get(position));

            //System.out.println("String Array contents: " +productNameStringArray[position] + " | position#: " +position);
            System.out.println("--ArayList stuff: "  + productNameArrayList.get(position)+" | arraylistContents: " +productNameArrayList.size());

            //textview_name.setText(productNameString[0]=productSKUSting);


            return convertView;
        }
    }

    /**
     * Class representing the tasks for query searches. Query searches are done asynchronously.
     */
    class SearchTask extends AsyncTask<String, Void, String> {
        ProgressDialog loadingBar;

        /** Setup of task prior to execution. */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            setupLoadingDialog();
        }
        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected String doInBackground(String... params) {

            try {
                BlockChainQueryAPIClientUsage clientUser = new BlockChainQueryAPIClientUsage();
                JSONArray results = clientUser.getJSONResults(params[0], params[1],params[2],
                        params[3]);
                QueryResultParser queryParser = new QueryResultParser(results);
                ArrayList<Component> productResults = queryParser.getParsedResults();

                //Ben: This code here should print out the JSON in console. Need to manipulate this data to display it in this page.
                //Need this block of code to run first before UI's onCreate method. so that I can grab the data and display it.
                for (Component c : productResults) {
                    MedProduct product = (MedProduct) c;
                    Log.d("LisaAPIConnectionTest", "Name: " + c.getName() + ", SKU: " +
                            c.getSKU() + ", Supplier: " + c.getSupplier() + ", OrderID: " +
                            product.getOrderID() + ", OrderDate: " + product.getOrderDate());

                    /*
                    String getNameString;
                    getNameString = c.getName();
                    productNameArrayList.add(getNameString);

                    System.out.println("(===) Arraylist: " +productNameArrayList.get(i));
                    i++;
                    */
                }
                if (isCancelled())
                    return null;
                String queryID = null;
                while (queryID == null) {
                    queryID = clientUser.getQueryID();
                }

                return queryID; //TODO do not pass queryID
            } catch (JSONException e) {
                return "Unable to retrieve data. Parameters may be invalid.";
            }

        }

        /**
         * Once task is complete, this method is called for completion.
         * @param result
         */
        @Override
        protected void onPostExecute(String result) {
            populateSearchResults(result);
            loadingBar.dismiss();

        }

        /**
         * Populates the screen with the search results.
         * @param result The result from the query search
         */
        private void populateSearchResults(String result) {
//            TODO change for JSON results
//            ArrayList<String> results = new ArrayList<>();
//            results.add(""); //TODO fix layout so this line is not needed
//            results.add("Query ID: " + result);
//            arrayAdapter = new ArrayAdapter(SearchResultPage.this,
//                    android.R.layout.simple_list_item_1, results);
//            listview.setAdapter(arrayAdapter);
        }

        /** To set up the loading dialog seen by the user. */
        private void setupLoadingDialog() {
            loadingBar = new ProgressDialog(SearchResultPage.this);
            loadingBar.setCancelable(true);
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.setMessage("\t" + LOADING_DIALOG_TEXT);
            loadingBar.show();
        }
    }




}
